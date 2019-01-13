import numpy as np
import tensorflow as tf

from utils import *
from network import *


def train():
    notes = get_notes()
    num_pitch = len(set(notes))  # 获得所有不重复的音调数目，用于归一化

    network_input, network_output = prepare_sequences(notes, num_pitch)
    model = network_model(network_input, num_pitch)
    filepath = "weights-{epoch:02d}-{loss:.4f}.hdf5"

    # 在每一个epoch结束时保存模型参数
    checkpoint = tf.keras.callbacks.ModelCheckpoint(filepath, monitor="loss",
                                                    verbose=0, save_best_only=True,
                                                    mode="min")
    callbacks_list = [checkpoint]

    model.fit(network_input, network_output, epochs=100, batch_size=64, callbacks=callbacks_list)


# 准备训练用的序列（音符和和弦）
def prepare_sequences(notes, num_pitch):
    """
    :param notes: 训练用的音符的数组
    :param num_pitch: “不同（Set）”音符的总数
    :return:
    """
    sequence_length = 100  # 单个序列的长度

    # 得到所有不同的音调的名字
    pitch_names = sorted(set(item for item in notes))

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
    network_input = np.reshape(network_input, (n_patterns, sequence_length, 1))
    network_input = network_input / float(num_pitch)
    network_output = tf.keras.utils.to_categorical(network_output)

    return (network_input, network_output)


if __name__ == "__main__":
    train()
