package com.mhxks;

import com.mhxks.img.NNImage;
import com.mhxks.math.Matrix;
import com.mhxks.math.NNMath;
import com.mhxks.wrapper.Parameter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class AllImgTest {
    public static void main(String[] args) throws Exception{
        Parameter parameter = Parameter.loadParameter(new File("my_data.nn"));
        //加载NN
        NeuralNetwork784 nn = new NeuralNetwork784();

        File file = new File("H:\\development\\神经网络\\自己手写数据集\\4");
        for (File listFile : file.listFiles()) {

            NNImage nnImage = new NNImage(listFile);
            Matrix matrix = nnImage.getMatrix();
            Matrix out = nn.predict(NNMath.reshape(matrix,784,1),parameter.b,parameter.w,parameter.c);
            System.out.println("预测数字:"+NNMath.maxIndex(out));

        }

    }
}
