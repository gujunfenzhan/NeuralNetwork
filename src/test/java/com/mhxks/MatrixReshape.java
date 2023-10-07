package com.mhxks;

import com.mhxks.math.Matrix;
import com.mhxks.math.NNMath;

public class MatrixReshape {
    public static void main(String[] args) {

        Matrix matrix = new Matrix(
                new double[]{1.3f,6.3f},
                new double[]{2.5f,0.3f},
                new double[]{2.0f,1.0f}
        );
        NNMath.reshape(matrix,3,2).print();
    }
}
