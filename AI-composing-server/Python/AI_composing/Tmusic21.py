# -*- coding: UTF-8 -*-

from music21 import converter,instrument

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
        print(str(element))

if __name__ == "__main__":
    print_notes()