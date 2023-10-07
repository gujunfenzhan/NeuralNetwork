package com.mhxks;

import com.mhxks.math.Matrix;
import com.mhxks.math.NNMath;
import com.mhxks.minst.MINST;
import com.mhxks.wrapper.Parameter;

import java.text.DecimalFormat;

public class NeuralNetwork784 {

    public static void main(String[] args)throws Exception {
        new NeuralNetwork784();
    }


    public Matrix predict(Parameter parameter,Matrix in){
        return predict(in,parameter.b,parameter.w,parameter.c);
    }

    public Matrix predict(Matrix in,Matrix b,Matrix w,Matrix c){
        //y = A2[W1A1(x+b1)+b2]
        Matrix l0_bias1  = NNMath.add(in,b);

        //求tanh
        Matrix l0_bias1_tanh = NNMath.tanh(l0_bias1);
        //乘以权重
        Matrix l1 = NNMath.Multiply(l0_bias1_tanh,w);

        //加上偏置
        Matrix l1_w = NNMath.add(l1,c);

        //softmax
        Matrix out = NNMath.softMax(l1_w);


        //return out;
        return out;
    }


    public Parameter backward(Matrix in,Matrix b,Matrix w,Matrix c,Matrix y){
        //y = A2[W1A1(x+b1)+b2]
        Matrix l0_bias1  = NNMath.add(in,b);

        //求tanh
        Matrix l0_bias1_tanh = NNMath.tanh(l0_bias1);
        //乘以权重
        Matrix l1 = NNMath.Multiply(l0_bias1_tanh,w);

        //加上偏置
        Matrix l1_w = NNMath.add(l1,c);

        //softmax
        Matrix softmaxOut = NNMath.softMax(l1_w);

        Matrix y_plus_softmaxOut = NNMath.plus(y,softmaxOut);

        Matrix c_1 = NNMath.Multiply(-2,y_plus_softmaxOut);

        Matrix softMaxDout = NNMath.softMax_grad(l1_w);

        Matrix c_grad = NNMath.MultiplyByElement(c_1,softMaxDout);


        /**
         * b的梯度
         */
        Matrix tanhD_x_b = NNMath.tanh_grad(l0_bias1);

        Matrix c_grad_w = NNMath.T(NNMath.Multiply(w,NNMath.T(c_grad)));


        Matrix b_grad = NNMath.MultiplyByElement(tanhD_x_b,c_grad_w);


        //w的梯度
        Matrix w_grad = NNMath.reshape(NNMath.T(NNMath.Multiply(NNMath.T(c_grad),l0_bias1_tanh)),w.getWidth(),w.getHeight());

        Parameter parameter = new Parameter(b_grad,c_grad,w_grad);


        return parameter;
    }


}
