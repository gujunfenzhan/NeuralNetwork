package com.mhxks.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NNMath {
    //矩阵向量相乘(矩阵行乘列)
    //输入 nxm,mx1
    //输出 nx1
    @Deprecated
    public static Vector Multiply(Matrix matrix,Vector vector){
        int matrixHeight = matrix.getHeight();
        int matrixWeight = matrix.getWidth();
        double[] doubles = new double[matrixHeight];
        for (int i = 0; i < matrixHeight; i++) {
            double addTotal = 0;
            for (int i1 = 0; i1 < matrixWeight; i1++) {
                addTotal += matrix.getElement(i,i1)*vector.getElement(i1);
            }
            doubles[i] = addTotal;


        }



        return new Vector(doubles);
    }
    //矩阵相乘
    //输入 nxm mxc
    //输出 nxc
    public static Matrix Multiply(Matrix matrix1,Matrix matrix2){
        int height = matrix1.getHeight();
        int width = matrix2.getWidth();
        int k = matrix1.getWidth();
        List<double[]> list = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            double[] doubles = new double[width];
            for (int i1 = 0; i1 < width; i1++) {


                double all = 0.0f;
                for (int i2 = 0; i2 < k; i2++) {
                    all +=matrix1.getElement(i,i2)*matrix2.getElement(i2,i1);
                }
                doubles[i1] = all;
            }
            list.add(doubles);
        }
        return new Matrix(list);
    }
    //矩阵元素相加
    //输入 nxm,nxm
    //输出 nxm
    public static Matrix add(Matrix matrix1,Matrix matrix2){
        int matrixWidth = matrix1.getWidth();
        int matrixHeight = matrix1.getHeight();
        List<double[]> list = new ArrayList<double[]>();
        for (int i = 0; i < matrixHeight; i++) {
            double[] line = new double[matrixWidth];
            for (int i1 = 0; i1 < matrixWidth; i1++) {
                line[i1] = matrix1.getElement(i,i1)+matrix2.getElement(i,i1);
            }
            list.add(line);
        }
        Matrix matrix = new Matrix(list);
        return matrix;
    }
    //矩阵元素相减
    //输入 nxm,nxm
    //输出 nxm
    public static Matrix plus(Matrix matrix1,Matrix matrix2){
        int matrixWidth = matrix1.getWidth();
        int matrixHeight = matrix1.getHeight();
        List<double[]> list = new ArrayList<double[]>();
        for (int i = 0; i < matrixHeight; i++) {
            double[] line = new double[matrixWidth];
            for (int i1 = 0; i1 < matrixWidth; i1++) {
                line[i1] = matrix1.getElement(i,i1)-matrix2.getElement(i,i1);
            }
            list.add(line);
        }
        Matrix matrix = new Matrix(list);
        return matrix;
    }



    //矩阵每个元素求tanh
    //输入 nxm
    //输出 nxm
    public static Matrix tanh(Matrix matrix){
        int matrixWidth = matrix.getWidth();
        int matrixHeight = matrix.getHeight();
        List<double[]> list = new ArrayList<double[]>();
        for (int i = 0; i < matrixHeight; i++) {
            double[] line = new double[matrixWidth];
            for (int i1 = 0; i1 < matrixWidth; i1++) {
                double element = matrix.getElement(i,i1);

                line[i1] = tanh(element);

            }
            list.add(line);
        }
        return new Matrix(list);
    }
    //向量求tanh
    @Deprecated
    public static Vector tanh(Vector vector){
        int width = vector.getLen();
        double[] doubles = new double[width];
        for (int i = 0; i < width; i++) {
            doubles[i] = tanh(vector.getElement(i));
        }
        return new Vector(doubles);
    }
    public static double tanh(double element){
        return (double) ((Math.exp(element)-Math.exp(-element))/(Math.exp(element)+Math.exp(-element)));
    }
    @Deprecated
    public static Vector add(Vector vector1,Vector vector2){
        int len = vector1.getLen();
        double[] elements = new double[len];
        for (int i = 0; i < len; i++) {
            elements[i] = vector1.getElement(i)+vector2.getElement(i);
        }
        return new Vector(elements);
    }
    @Deprecated
    public static Vector softMax(Vector vector){
        int len = vector.getLen();
        double expAll = 0.0f;

        for (int i = 0; i < len; i++) {
            expAll+=Math.exp(vector.getElement(i));
        }

        double[] doubles = new double[len];
        for (int i = 0; i < doubles.length; i++) {
            doubles[i] = (double) Math.exp(vector.getElement(i))/expAll;
        }
        return new Vector(doubles);
    }
    //对矩阵每个元素求softmax
    //输入 nxm
    //输出 nxm
    public static Matrix softMax(Matrix matrix){
        double max = maxIndex(matrix);

        Matrix newMatrix = NNMath.plus(
                matrix
                ,
                NNMath.fullMatrix(new Matrix(new double[matrix.getWidth()]),max)

        );


        int len = newMatrix.getWidth();
        double expAll = 0.0f;

        for (int i = 0; i < len; i++) {
            expAll+=Math.exp(newMatrix.getElement(0,i));
        }

        double[] doubles = new double[len];
        for (int i = 0; i < doubles.length; i++) {
            doubles[i] = (double) Math.exp(newMatrix.getElement(0,i))/expAll;
        }

        return new Matrix(doubles);
    }
    //softmax的导函数
    //输入 nxm
    //输出 nxm
    public static Matrix softMax_grad(Matrix matrix){
        //Vector vector1 = softMax_grad(new Vector(matrix.list.get(0)));
        Matrix soft = softMax(matrix);

        Matrix matrix1 = new Matrix(new double[matrix.getWidth()]);
        matrix1 = NNMath.fullMatrix(matrix1,1);

        Matrix out = NNMath.MultiplyByElement(soft,NNMath.plus(matrix1,soft));



        return out;

    }

    @Deprecated
    public static Vector softMax_grad(Vector vector){

        int len = vector.getLen();
        double expAll = 0.0f;
        for (int i = 0; i < len; i++) {
            expAll+=Math.exp(vector.getElement(i));
        }
        double[] doubles = new double[len];
        for (int i = 0; i < doubles.length; i++) {
            double exp = (double) Math.exp(vector.getElement(i));
            doubles[i] = ((exp*expAll)-(exp*exp))/(expAll*expAll);
        }
        return new Vector(doubles);

    }
    //对矩阵填满指定数值(double)
    // 输入 nxm,i
    // 输出 nxm
    public static Matrix fullMatrix(Matrix matrix,double nu){
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        List<double[]> list = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            double[] doubles = new double[width];
            for (int i1 = 0; i1 < width; i1++) {
                doubles[i1] = nu;
            }
            list.add(doubles);
        }
        return new Matrix(list);
    }

    //对矩阵填满指定数值(int)
    // 输入 nxm,i
    // 输出 nxm
    public static Matrix fullMatrix(Matrix matrix,int nu){
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        List<double[]> list = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            double[] doubles = new double[width];
            for (int i1 = 0; i1 < width; i1++) {
                doubles[i1] = nu;
            }
            list.add(doubles);
        }
        return new Matrix(list);
    }
    @Deprecated
    public static Vector fullVector(Vector vector,int element){
        int width = vector.getLen();
        double[] doubles = new double[width];
        for (int i = 0; i < width; i++) {
            doubles[i] = element;
        }
        return new Vector(doubles);
    }
    //对矩阵使用tanh的导数
    // 输入 nxm
    // 输出 nxm
    public static Matrix tanh_grad(Matrix matrix){
        int matrixWidth = matrix.getWidth();
        int matrixHeight = matrix.getHeight();
        List<double[]> list = new ArrayList<double[]>();
        for (int i = 0; i < matrixHeight; i++) {
            double[] line = new double[matrixWidth];
            for (int i1 = 0; i1 < matrixWidth; i1++) {
                double element = matrix.getElement(i,i1);
                double tanh = tanh(element);
                line[i1] = 1 -(tanh*tanh);

            }
            list.add(line);
        }
        return new Matrix(list);
    }
    //矩阵转置
    //输入 nxm
    //输出 mxn
    public static Matrix T(Matrix matrix){
        int width = matrix.getHeight();
        int height = matrix.getWidth();
        List<double[]> list = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            double[] doubles = new double[width];
            for (int i1 = 0; i1 < width; i1++) {
                doubles[i1] = matrix.getElement(i1,i);
            }
            list.add(doubles);

        }
        return new Matrix(list);
    }

    //外积
    @Deprecated
    public static Matrix outer(Vector vector1,Vector vector2){
        List<double[]> list = new ArrayList<>();
        int width = vector2.getLen();
        int height = vector1.getLen();
        for (int i = 0; i < height; i++) {
            double[] doubles = new double[width];
            for (int i1 = 0; i1 < width; i1++) {
                doubles[i1] = vector1.getElement(i)*vector2.getElement(i1);

            }
            list.add(doubles);

        }
        return new Matrix(list);

    }
    //均方损失函数
    //输入 nx1(1xn)
    //输出 nx1(1xn)
    public static double loss(Vector vector1,Vector vector2){
        double loss = 0.0f;
        int width = vector1.getLen();
        for (int i = 0; i < width; i++) {
            double cha = vector1.getElement(i)-vector2.getElement(i);
            loss+= cha*cha;
        }
        return loss;
    }
    //矩阵版的均方损失函数
    //只会计算第一行
    public static double loss(Matrix matrix1,Matrix matrix2){

        return loss(new Vector(matrix1.list.get(0)),new Vector(matrix2.list.get(0)));
    }
    //数值乘矩阵
    //输入 i,nxm
    //输出 nxm
    public static Matrix  Multiply(int nu,Matrix matrix){
        List<double[]> list = new ArrayList<>();
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        for (int i = 0; i < height; i++) {
            double[] doubles = new double[width];
            for (int i1 = 0; i1 < width; i1++) {
                doubles[i1] = nu*matrix.getElement(i,i1);
            }
            list.add(doubles);
        }
        return new Matrix(list);
    }
    //数值乘矩阵
    //输入 i,nxm
    //输出 nxm
    public static Matrix  Multiply(double nu,Matrix matrix){
        List<double[]> list = new ArrayList<>();
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        for (int i = 0; i < height; i++) {
            double[] doubles = new double[width];
            for (int i1 = 0; i1 < width; i1++) {
                doubles[i1] = nu*matrix.getElement(i,i1);
            }
            list.add(doubles);
        }
        return new Matrix(list);
    }

    //矩阵每个元素除以一个值
    // 输入 i,nxm
    // 输出 nxm
    public static Matrix divide(double nu,Matrix matrix){
        List<double[]> list = new ArrayList<>();
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        for (int i = 0; i < height; i++) {
            double[] doubles = new double[width];
            for (int i1 = 0; i1 < width; i1++) {
                doubles[i1] = matrix.getElement(i,i1)/nu;
            }
            list.add(doubles);
        }
        return new Matrix(list);
    }


    //取矩阵对角元素
    public static Matrix diag(Matrix matrix){
        int width = matrix.getWidth();
        double[] doubles = new double[width];
        for (int i = 0; i < width; i++) {
            doubles[i] = matrix.getElement(i,i);
        }
        return new Matrix(doubles);
    }
    //使用高斯分布生成一个随机矩阵
    public static Matrix genRandomMatrix(int width,int height){
        double len = Math.sqrt(6.0d/(784d+10d));
        List<double[]> list = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            double[] doubles = new double[width];
            for (int i1 = 0; i1 < width; i1++) {
                doubles[i1] = generateGaussian(0,len);
            }
            list.add(doubles);
        }
        return new Matrix(list);
    }
    static Random random = new Random();
    // 生成符合高斯分布的随机数
    public static double generateGaussian(double mean, double stdDeviation) {
        return mean + stdDeviation * random.nextGaussian();
    }
    //生成一个填满某个值的矩阵
    //输入 i,i1,i2
    //输出 nxm
    public static Matrix genMatrix(int width,int height,int fullNum){
        List<double[]> list = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            double[] doubles = new double[width];
            for (int i1 = 0; i1 < width; i1++) {
                doubles[i1] = fullNum;
            }
            list.add(doubles);
        }
        return new Matrix(list);
    }
    //重塑矩阵
    //输入nxm,i,i1
    //输出 ixi1
    public static Matrix reshape(Matrix matrix,int width,int height){
        if(matrix.getWidth()*matrix.getHeight()!=width*height){
            System.out.println("数据不匹配");
            return null;
        }
        List<Double> allData = new ArrayList<>();
        for (int i = 0; i < matrix.getHeight(); i++) {
            for (int i1 = 0; i1 < matrix.getWidth(); i1++) {
                allData.add(matrix.getElement(i,i1));
            }
        }
        //重新分配
        List<double[]> list = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            double[] doubles = new double[width];
            for (int i1 = 0; i1 < width; i1++) {
                doubles[i1] = allData.get(0);
                allData.remove(0);
            }
            list.add(doubles);
        }
        return new Matrix(list);

    }
    //依据两矩阵的每个同位置元素相乘得到新的矩阵
    //输入 nxm,nxm
    //输出 nxm
    public static Matrix MultiplyByElement(Matrix matrix1,Matrix matrix2){
        List<double[]> list = new ArrayList<>();
        int width = matrix1.getWidth();
        int height = matrix1.getHeight();

        for (int i = 0; i < height; i++) {
            double[] doubles = new double[width];

            for (int i1 = 0; i1 < width; i1++) {
                doubles[i1] = matrix1.getElement(i,i1)*matrix2.getElement(i,i1);
            }
            list.add(doubles);
        }
        return new Matrix(list);
    }
    //获取矩阵里最大的数，仅第一行生效
    public static int maxIndex(Matrix matrix){
        double[] doubles = matrix.list.get(0);
        double max = Double.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < doubles.length; i++) {
            if(doubles[i]>max){
                max = doubles[i];
                index = i;
            }
        }
        return index;
    }
}
