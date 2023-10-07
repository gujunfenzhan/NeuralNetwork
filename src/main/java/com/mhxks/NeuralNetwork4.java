package com.mhxks;

import com.mhxks.math.Matrix;
import com.mhxks.math.NNMath;
/*
2x2输入
1x2输出
一个小型的神经网络模型，仅支持2x2输入
w:2x2
b1:1x2
b2:1x2
 */
public class NeuralNetwork4 {

    public static void main(String[] args) {
        NeuralNetwork4 neuralNetwork = new NeuralNetwork4();



    }


    public NeuralNetwork4(){
        //前向传播
        Matrix input = new Matrix(new double[]{1.0f,2.0f});


                //第一层偏置
        Matrix bias1 = new Matrix(
                new double[]{0.0f,0.0f}
        );
        Matrix bias2 = new Matrix(new double[]{0.0f,0.0f});
        Matrix weight = new Matrix(
                new double[]{0.3f,0.1f},
                new double[]{0.6f,0.4f}
        );
        Matrix y = new Matrix(new double[]{1.0f,0.0f});


        Matrix y_pre = forward(input,bias1,weight,bias2);

        System.out.println("Loss:"+NNMath.loss(y,y_pre));


        for (int i = 0; i < 100; i++) {
            Matrix bias1_grad = NNMath.Multiply(-1,bias1_grad(input,bias1,weight,bias2,y));

            Matrix bias2_grad = NNMath.Multiply(-1,bias2_grad(input,bias1,weight,bias2,y));

            Matrix weight_grad = NNMath.Multiply(-1,weight_grad(input,bias1,weight,bias2,y));

            //没用，应该是算错了
            //bias1 = NNMath.add(bias1,bias1_grad);
            bias2 = NNMath.add(bias2,bias2_grad);
            weight = NNMath.add(weight,weight_grad);

            y_pre = forward(input,bias1,weight,bias2);
            System.out.println("Loss:"+NNMath.loss(y,y_pre));

        }







        //Vector out = forward(input,bias1,weight,bias2);

        //out.print();

    }
    public Matrix forward(Matrix in, Matrix b, Matrix w,Matrix c){
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

    public Matrix bias2_grad(Matrix in, Matrix b, Matrix w,Matrix c,Matrix y){

        //y = A2[W1A1(x+b1)+b2]
        Matrix l0_bias1  = NNMath.add(in,b);

        //求tanh
        Matrix l0_bias1_tanh = NNMath.tanh(l0_bias1);
        //乘以权重
        Matrix l1 = NNMath.Multiply(l0_bias1_tanh,w);

        //加上偏置
        Matrix l1_w = NNMath.add(l1,c);

        //softmax
        Matrix y_pre = NNMath.softMax(l1_w);

        //y-y_pre
        Matrix y_plus_y_pre = NNMath.add(y,NNMath.Multiply(-1,y_pre));
        // *-2
        y_plus_y_pre = NNMath.Multiply(-2,y_plus_y_pre);
        //softMax_d
        Matrix softMaxD = NNMath.softMax_grad(l1_w);

        Matrix out = NNMath.Multiply(NNMath.T(y_plus_y_pre),softMaxD);

        Matrix diag = NNMath.diag(out);

        return diag;
    }


    public Matrix bias1_grad(Matrix in, Matrix b, Matrix w,Matrix c,Matrix y){
        //y = A2[W1A1(x+b1)+b2]
        Matrix l0_bias1  = NNMath.add(in,b);

        //求tanh
        Matrix l0_bias1_tanh = NNMath.tanh(l0_bias1);
        //乘以权重
        Matrix l1 = NNMath.Multiply(l0_bias1_tanh,w);

        //加上偏置
        Matrix l1_w = NNMath.add(l1,c);

        //softmax
        Matrix y_pre = NNMath.softMax(l1_w);

        //y-y_pre
        Matrix y_plus_y_pre = NNMath.add(y,NNMath.Multiply(-1,y_pre));
        // *-2
        y_plus_y_pre = NNMath.Multiply(-2,y_plus_y_pre);
        //softMax_d
        Matrix softMaxD = NNMath.softMax_grad(l1_w);

        Matrix out = NNMath.Multiply(NNMath.T(y_plus_y_pre),softMaxD);

        Matrix diag = NNMath.diag(out);

        Matrix x_add_b_tanh_d = NNMath.tanh_grad(l0_bias1);

        Matrix x_add_b_tanh_d_weight = NNMath.Multiply(x_add_b_tanh_d,w);

        Matrix out2 = NNMath.Multiply(NNMath.T(diag),x_add_b_tanh_d_weight);

        return NNMath.diag(out2);

    }




    public Matrix weight_grad(Matrix in, Matrix b, Matrix w,Matrix c,Matrix y){
        //y = A2[W1A1(x+b1)+b2]
        Matrix l0_bias1  = NNMath.add(in,b);

        //求tanh
        Matrix l0_bias1_tanh = NNMath.tanh(l0_bias1);
        //乘以权重
        Matrix l1 = NNMath.Multiply(l0_bias1_tanh,w);

        //加上偏置
        Matrix l1_w = NNMath.add(l1,c);

        //softmax
        Matrix y_pre = NNMath.softMax(l1_w);


        //y-y_pre
        Matrix y_plus_y_pre = NNMath.add(y,NNMath.Multiply(-1,y_pre));
        // *-2
        y_plus_y_pre = NNMath.Multiply(-2,y_plus_y_pre);
        //softMax_d
        Matrix softMaxD = NNMath.softMax_grad(l1_w);

        Matrix out = NNMath.Multiply(NNMath.T(y_plus_y_pre),softMaxD);

        Matrix diag = NNMath.diag(out);

        Matrix x_add_b_tanh = NNMath.tanh(l0_bias1);

        Matrix diag_mu_x_add_b_tanh = NNMath.Multiply(NNMath.T(diag),x_add_b_tanh);

        Matrix out2 = NNMath.diag(diag_mu_x_add_b_tanh);

        Matrix matrix = new Matrix(new double[out2.getWidth()]);


        matrix = NNMath.fullMatrix(matrix,1);

        Matrix a1 = NNMath.Multiply(NNMath.T(matrix),out2);


        return a1;
    }


}
