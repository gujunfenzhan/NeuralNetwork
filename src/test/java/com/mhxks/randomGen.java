package com.mhxks;

import com.mhxks.math.Matrix;
import com.mhxks.math.NNMath;

public class randomGen {
    public static void main(String[] args) {

        Matrix matrix = NNMath.genRandomMatrix(8,8);
        matrix.print();
    }
}
