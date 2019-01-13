# -*- coding: UTF-8 -*-

import pickle
import numpy as np
import tensorflow as tf

from utils import *
from network import *

"""
使用传入歌曲的前一百个音符（和弦）来生成新的歌曲
"""

def generate(baseFile):
    with open('data/notes2', 'rb') as filepath:
        notes = pickle.load(filepath)

    pitch_names = sorted(set(item for item in notes))
    num_pitch = len(set(notes))
    network_input, normalized_input = prepare_sequences(notes, pitch_names, num_pitch)

    model = network_model(normalized_input, num_pitch, "best-weights.hdf5")
    print(model.summary())

    # 这里使用输入歌曲的前一百个音符作为输入预测后面的音符
    custom_input = get_inputNotes_byFile(baseFile, pitch_names)
    prediction = generate_notes(model, custom_input, pitch_names, num_pitch)
    # prediction = generate_notes(model, network_input, pitch_names, num_pitch)
    create_music(prediction)


def prepare_sequences(notes, pitch_names, num_pitch):
    sequence_length = 100  # 单个序列的长度

    # 创建一个音符到数字的映射
    pitch_to_int = dict((pitch, num) for num, pitch in enumerate(pitch_names))

    network_input = []
    network_output = []  # 这里定义的是期望输出，对应于network_input相应位置的实际序列下一位的音符

    for i in range(0, len(notes) - sequence_length, 1):
        sequence_in = notes[i: i + sequence_length]
        sequence_out = notes[i + sequence_length]

        network_input.append([pitch_to_int[char] for char in sequence_in])
        network_output.append([pitch_to_int[sequence_out]])

    n_patterns = len(network_input)
    normalized_input = np.reshape(network_input, (n_patterns, sequence_length, 1))
    normalized_input = normalized_input / float(num_pitch)

    return (network_input, normalized_input)


def generate_notes(model, network_input, pitch_names, num_pitch):
    """
    基于一序列音符用训练好的神经网络模型生成一首歌曲
    :param model: 训练好的模型
    :param network_input: 神经网络输入
    :param pitch_names: 所有不重复的音符的名字
    :param num_pitch: 所有不重复的音符的总数量
    :return:
    """
    # start = np.random.randint(0, len(network_input) - 1)

    int_to_pitch = dict((num, pitch) for num, pitch in enumerate(pitch_names))
    # pattern = network_input[start]
    pattern = network_input
    prediction_output = []

    # 生成700个音符
    for note_index in range(700):
        prediction_input = np.reshape(pattern, (1, len(pattern), 1))
        prediction_input = prediction_input / float(num_pitch)

        # 读取参数文件，即载入最佳参数的神经网络来进行预测
        prediction = model.predict(prediction_input, verbose=0)

        # 取最高的预测值，类似One-Hot
        index = np.argmax(prediction)
        result = int_to_pitch[index]
        prediction_output.append(result)

        pattern.append(index)
        pattern = pattern[1:len(pattern)]

    return prediction_output


if __name__ == "__main__":
    generate(r"D:\code\python\AI_composing\music_midi\7.mid")
