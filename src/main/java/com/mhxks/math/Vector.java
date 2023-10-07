package com.mhxks.math;
/*

 */
@Deprecated
public class Vector {
    double[] doubles;
    public Vector(double[] doubles){
        this.doubles = doubles;
    }
    public double getElement(int index){
        return this.doubles[index];
    }
    public int getLen(){
        return this.doubles.length;
    }
    public void print(){
        System.out.print("[");
        for (double adouble : this.doubles) {
            System.out.print(adouble);
            System.out.print(" , ");
        }
        System.out.println("]");
    }
}
