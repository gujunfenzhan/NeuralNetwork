package com.mhxks;

import com.mhxks.math.Matrix;
import com.mhxks.wrapper.Parameter;

import java.io.File;

public class ParameterSaveTest {
    public static void main(String[] args) throws Exception{
        Matrix a = new Matrix(
                new double[]{1.0d,2.0d}
        );
        Matrix b = new Matrix(
                new double[]{1.0d,2.0d},
                new double[]{1.0d,4.d}
        );
        Matrix c = new Matrix(
                new double[]{1.0d,2.0d},
        new double[]{1.0d,5.0d},
        new double[]{1.0d,1.0d}
        );

        //Parameter parameter = new Parameter(a,b,c);
        //parameter.saveParameter(new File("data.nn"));
        Parameter p = Parameter.loadParameter(new File("data.nn"));
        System.out.println(p);

    }
}
