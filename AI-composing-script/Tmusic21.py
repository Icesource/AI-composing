# -*- coding: UTF-8 -*-

from music21 import converter, instrument, note, chord, stream

def print_notes():
    # 读取MIDI文件， 输出Stream流
    stream = converter.parse("1.mid")

    # 获取所有的乐器部分
    parts = instrument.partitionByInstrument(stream)

    if parts:
        notes = parts.parts[0].recurse()
    else:
        notes = stream.flat.notes

    for element in notes:
        print(str(element.offset))
        if isinstance(element, note.Note):
            print(str(element.pitch))
        elif isinstance(element, chord.Chord):
            print('.'.join(str(n) for n in element.normalOrder))

if __name__ == "__main__":
    print_notes()
