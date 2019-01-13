# coding:UTF-8
import os
import subprocess
from music21 import converter, instrument, note, chord, stream
import pickle
import glob
import time


# 将Midi文件转换为MP3文件
def convertMidi2Mp3():
    input_file = "output.mid"
    output_file = "output.mp3"
    assert os.path.exists(input_file)

    print("Converting %s to MP3" % input_file)

    # 使用timidity命令生成mp3文件
    command = 'timidity {} -Ow -o - | ffmpeg -i - ' \
              '-acodec libmp3lame -ab 64k {}'.format(input_file, output_file)
    subprocess.call(command, shell=True)

    print("Converted. Generated file is %s" % output_file)


# 使用music21读取音符、和弦
def get_notes():
    """
    样例： chord：和弦， Note：音符
    <music21.chord.Chord F4 G#4 C5>
    <music21.note.Note F>
    :return:
    """
    notes = []

    for file in glob.glob("music_midi/*.mid"):
        stream = converter.parse(file)

        # 获取所有的乐器部分
        parts = instrument.partitionByInstrument(stream)
        if parts:
            notes_to_parse = parts.parts[0].recurse()
        else:
            notes_to_parse = stream.flat.notes

        for element in notes_to_parse:
            # print(element)
            if isinstance(element, note.Note):
                notes.append(str(element.pitch))
                # print(element.pitch)
            elif isinstance(element, chord.Chord):
                # 将和弦转换为以.分割的音符，方便处理
                notes.append('.'.join(str(n) for n in element.normalOrder))
                # print(element.normalOrder)

    with open('data/notes', "wb") as filepath:
        pickle.dump(notes, filepath)

    return notes


def get_inputNotes_byFile(filepath, pitch_names):
    notes = []
    pitch_to_int = dict((pitch, num) for num, pitch in enumerate(pitch_names))

    stream = converter.parse(filepath)
    parts = instrument.partitionByInstrument(stream)
    if parts:
        notes_to_parse = parts.parts[0].recurse()
    else:
        notes_to_parse = stream.flat.notes

    for element in notes_to_parse:
        if isinstance(element, note.Note):
            notes.append(pitch_to_int[str(element.pitch)])
        elif isinstance(element, chord.Chord):
            notes.append(pitch_to_int['.'.join(str(n) for n in element.normalOrder)])

        if len(notes) >= 100:
            break
    return notes


# 根据神经网络预测的数据生成midi文件
def create_music(prediction):
    offset = 0
    output_notes = []

    for data in prediction:
        # 处理为和弦的情况
        if ('.' in data) or data.isdigit():
            notes_in_chord = data.split('.')
            notes = []
            for current_note in notes_in_chord:
                new_note = note.Note(int(current_note))
                new_note.storedInstrument = instrument.Piano()
                notes.append(new_note)
            new_chord = chord.Chord(notes)
            new_chord.offset = offset  # 和弦需要一个offset偏移值
            output_notes.append(new_chord)
        else:
            new_note = note.Note(data)
            new_note.offset = offset
            new_note.storeInstrument = instrument.Piano()
            output_notes.append(new_note)

        offset += 0.5  # 每次迭代偏移值加0.5

    # 生成文件
    midi_stream = stream.Stream(output_notes)
    output = 'E:/output/' + time.strftime('%Y-%m-%d-%H-%M-%S', time.localtime(time.time())) + '.mid'
    midi_stream.write('midi', fp=output)
    print(output)

if __name__ == "__main__":
    get_notes()
