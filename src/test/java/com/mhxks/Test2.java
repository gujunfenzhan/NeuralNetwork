package com.mhxks;

import com.mhxks.math.Matrix;
import com.mhxks.math.NNMath;
import com.mhxks.minst.MINST;
import com.mhxks.wrapper.Parameter;

import java.io.File;

public class Test2 {
    public static void main(String[] args) throws Exception{
        NeuralNetwork784 neuralNetwork784 = new NeuralNetwork784();
        Parameter parameter = Parameter.loadParameter(new File("data(6万张1轮).nn"));
        MINST minst = new MINST("minst","train-images.idx3-ubyte","train-labels.idx1-ubyte",1000);
        for (int i = 0; i < minst.images.size(); i++) {
            Matrix img = minst.images.get(i);
            Matrix label = minst.getLabelByOneHot(i);
            Matrix pre = neuralNetwork784.predict(NNMath.reshape(img,784,1),parameter.b,parameter.w,parameter.c);
            int y = NNMath.maxIndex(label);
            int y_pre = NNMath.maxIndex(pre);
            if(y==1)
            System.out.println("正确:"+y+" 预测:"+y_pre);

        }
    }
}
