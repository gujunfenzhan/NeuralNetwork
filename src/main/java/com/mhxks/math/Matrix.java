package com.mhxks.math;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Matrix {
    /*
    矩阵形式
    double[]{x1,x2,x3...,xn}
    double[]{b1,b2,b3...,bn}
    double[]{c1,c2,c3...,cn}
    ......
    double[]{d1,d2,d3...,dn}
     */
    public List<double[]> list = new ArrayList<double[]>();
    //如上所示
    public Matrix(double[]...a){
        for (double[] doubles : a) {
            list.add(doubles);
        }
    }
    //也可以构建好list传入
    public Matrix(List<double[]> list){
        this.list = list;
    }
    /*
    生成一个宽高为size的单位矩阵
     */
    public Matrix(int size){
        for (int i = 0; i < size; i++) {
            double[] doubles = new double[size];
            for (int i1 = 0; i1 < size; i1++) {
                if(i1==i){
                    doubles[i1] = 1;
                }
                else{
                    doubles[i1] = 0;
                }
            }
            list.add(doubles);
        }
    }
    //获取矩阵宽度
    public int getWidth(){
        return this.list.get(0).length;
    }
    //获取矩阵高度
    public int getHeight(){
        return this.list.size();
    }
    /*
    获取对应位置的元素
    index1:行
    index2:列
     */
    public double getElement(int index1,int index2){
        return this.list.get(index1)[index2];
    }
    //输出矩阵内容
    public void print(){
        for (double[] doubles : list) {
            System.out.print("[");
            for (double adouble : doubles) {
                System.out.print(adouble);

                System.out.print(" , ");
            }
            System.out.println("]");
        }
    }
    //矩阵序列化
    public byte[] serialize(){
        int width = this.getWidth();
        int height = this.getHeight();
        int size = width*height;
        Matrix matrix = NNMath.reshape(this,size,1);
        ByteBuffer buffer = ByteBuffer.allocate(width*height*8+8);
        buffer.putInt(width);
        buffer.putInt(height);
        for (int i = 0; i < size; i++) {
            buffer.putDouble(matrix.getElement(0,i));
        }
        return buffer.array();
    }
    //矩阵反序列化
    public static Matrix deserialize(byte[] bytes){
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        int width = buffer.getInt();
        int height = buffer.getInt();
        int size = width*height;
        double[] doubles = new double[size];
        for (int i = 0; i < size; i++) {
            doubles[i] = buffer.getDouble();
        }
        Matrix matrix = new Matrix(doubles);

        matrix = NNMath.reshape(matrix,width,height);
        return matrix;
    }
}
