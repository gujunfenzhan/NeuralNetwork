package com.mhxks;

import com.mhxks.minst.MINST;

import java.util.ArrayList;
import java.util.List;

public class MyDataTest {
    public static void main(String[] args) throws Exception{
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(""+i);
        }
        MINST minst = new MINST("my_data",list);
        minst.shuffle();

    }
}
