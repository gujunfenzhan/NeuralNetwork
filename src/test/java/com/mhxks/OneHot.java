package com.mhxks;

import com.mhxks.math.Matrix;
import com.mhxks.math.NNMath;
import com.mhxks.minst.MINST;

public class OneHot {
    public static void main(String[] args) throws Exception{
        MINST minst = new MINST("minst","train-images.idx3-ubyte","train-labels.idx1-ubyte",1);
        int label = minst.getLabel(0);
        Matrix labelM = minst.getLabelByOneHot(0);
        System.out.println(label);
        labelM.print();

    }
}
