package com.mhxks;

import com.mhxks.img.NNImage;
import com.mhxks.math.Matrix;
import com.mhxks.math.NNMath;
import com.mhxks.minst.MINST;
import com.mhxks.wrapper.Parameter;

import java.io.File;

public class tEST9 {
    public static void main(String[] args) throws Exception{
        NeuralNetwork784 neuralNetwork784 = new NeuralNetwork784();
        Parameter parameter = Parameter.loadParameter(new File("data.nn"));
        File file = new File("H:\\development\\神经网络\\data\\minst_img\\train\\9");
        for (File listFile : file.listFiles()) {
            NNImage test = new NNImage(listFile);
            Matrix matrix = test.getMatrix();
            Matrix pre = neuralNetwork784.predict(NNMath.reshape(matrix,784,1),parameter.b,parameter.w,parameter.c);
            Matrix hot = new Matrix(10);
            Matrix label = new Matrix(hot.list.get(9));

            System.out.println("预测:"+NNMath.maxIndex(pre));



        }



    }
}
