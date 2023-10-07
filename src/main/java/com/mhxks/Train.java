package com.mhxks;

import com.mhxks.math.Matrix;
import com.mhxks.math.NNMath;
import com.mhxks.minst.MINST;
import com.mhxks.wrapper.Parameter;

import java.io.File;
import java.util.Random;

public class Train {
    public static void main(String[] args)throws Exception {
        Random random = new Random();
        NeuralNetwork784 nn = new NeuralNetwork784();
        //训练次数
        int train_nu = 20;
        //训练图片数量,总计6万张
        int train_size = 30000;
        //每批次训练多少张
        int batch_size = 50;
        //学习率
        double w_learn_rate = 0.8;
        double b_learn_rate = 0.01;
        double c_learn_rate = 0.1;




        MINST minst = new MINST("minst","train-images.idx3-ubyte","train-labels.idx1-ubyte",train_size);
        Matrix input = minst.getImg(0);
        input = NNMath.reshape(input,784,1);
        Matrix y = minst.getLabelByOneHot(0);

        //生成随机权重
        Matrix weight = NNMath.genRandomMatrix(10,784);
        //生成偏置b
        Matrix b = NNMath.genMatrix(784,1,0);
        //生成偏置c
        Matrix c = NNMath.genMatrix(10,1,0);


        for (int i = 0; i < train_nu; i++) {
            int epoch = train_size/batch_size;
            for (int i1 = 0; i1 < epoch; i1++) {
                //每个epoch需要更新一下参数，总共10个epoch
                Parameter parameter = null;
                for (int i2 = 0; i2 < batch_size; i2++) {

                    int index = batch_size*i1+i2;
                    //System.out.println("次数:"+i+"/10 "+"epoch:"+i1+"/"+epoch+" "+"index:"+i2+"/"+batch_size);
                    Matrix matrix = minst.getImgAndReshape(index);
                    Matrix label = minst.getLabelByOneHot(index);
                    //求梯度
                    if(parameter==null) {
                        parameter = nn.backward(matrix, b, weight, c, label);
                    }else{
                        parameter.add(nn.backward(matrix,b,weight,c,label));
                    }
                }
                System.out.println("次数:"+i+"/"+train_nu+" "+"epoch:"+i1+"/"+epoch);
                //平均化梯度
                parameter.divide(batch_size);


                Matrix w_grad = parameter.w;
                Matrix b_grad = parameter.b;
                Matrix c_grad = parameter.c;
                //更新梯度
                weight = NNMath.add(weight,NNMath.Multiply(-w_learn_rate,w_grad));
                b = NNMath.add(b,NNMath.Multiply(-b_learn_rate,b_grad));
                c = NNMath.add(c,NNMath.Multiply(-c_learn_rate,c_grad));



                double loss = 0;
                for (int i2 = 0; i2 < 64; i2++) {

                    int randIndex = random.nextInt(train_size);
                    Matrix randomImg = minst.getImgAndReshape(randIndex);
                    Matrix randomLabel = minst.getLabelByOneHot(randIndex);
                    Matrix p = nn.predict(randomImg,b,weight,c);
                    loss+=NNMath.loss(randomLabel,p);
                    //System.out.println("正确结果:"+NNMath.maxIndex(randomLabel)+" 预测结果"+NNMath.maxIndex(p));
                }


                System.out.printf("Loss:%.15f\n",loss/64);
                //随机一个图片查看Loss

                //缩小学习率
                //w_learn_rate = w_learn_rate *0.9d;
                ///b_learn_rate = b_learn_rate *0.9d;
                //c_learn_rate = c_learn_rate *0.9d;
            }









        }
        Parameter parameter = new Parameter(b,c,weight);
        parameter.saveParameter(new File("data.nn"));
    }
}
