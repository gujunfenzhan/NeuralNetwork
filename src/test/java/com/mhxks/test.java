package com.mhxks;

import com.mhxks.math.Matrix;
import com.mhxks.math.NNMath;

public class test {
    public static void main(String[] args) {

        Matrix matrix1 = new Matrix(
                new double[]{1.0f,2.0f},
                new double[]{6.0f,1.0f},
                new double[]{3.0f,1.0f}
        );
        Matrix matrix2 = new Matrix(
                new double[]{2.0f,3.0f},
                new double[]{1.0f,9.0f}
        );

        Matrix matrix3 = NNMath.Multiply(matrix1,matrix2);
        //matrix3.print();




        Matrix matrix = new Matrix(new double[]{5.0f,3.0f});



        NNMath.Multiply(5,matrix).print();



    }
}
