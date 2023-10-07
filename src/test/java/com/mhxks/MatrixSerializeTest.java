package com.mhxks;

import com.mhxks.math.Matrix;

public class MatrixSerializeTest {
    public static void main(String[] args) {
        Matrix matrix = new Matrix(
                new double[]{1.0d,5.0d,2.0d},
                new double[]{2.0d,1.0d,6.0d}
        );
        byte[] bytes = matrix.serialize();

        Matrix m = Matrix.deserialize(bytes);
        m.print();
    }
}
