# -*- coding: UTF-8 -*-

import tensorflow as tf


def network_model(inputs, num_pitch, weights_file=None):
    model = tf.keras.models.Sequential()
    model.add(tf.keras.layers.LSTM(
        512, input_shape=(inputs.shape[1], inputs.shape[2]),
        return_sequences=True
    ))  # 神经元数目512，返回所有的输出序列
    model.add(tf.keras.layers.Dropout(0.3))  # 丢弃层，丢弃30%神经元避免过拟合
    model.add(tf.keras.layers.LSTM(512, return_sequences=True))
    model.add(tf.keras.layers.Dropout(0.3))
    model.add(tf.keras.layers.LSTM(512))
    model.add(tf.keras.layers.Dense(256))  # 全连接层，256个神经元
    model.add(tf.keras.layers.Dropout(0.3))
    model.add(tf.keras.layers.Dense(num_pitch))  # 最后一层全连接，生成的音符数
    model.add(tf.keras.layers.Activation("softmax"))  # 激活函数

    # 确定损失函数的计算方法，确定优化器
    model.compile(loss="categorical_crossentropy", optimizer="rmsprop")

    # 生成音乐时使用，选择最好的参数文件
    if weights_file is not None:
        model.load_weights(weights_file)

    return model
