package com.mhxks;

import java.nio.ByteBuffer;

public class ByteBufferTest {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.putDouble(8.0f);
        buffer.putInt(4);
        byte[] bytes = buffer.array();


        ByteBuffer buffer1 = ByteBuffer.wrap(bytes);

        System.out.println("double:"+buffer1.getDouble());
        System.out.println("int:"+buffer1.getInt());
    }
}
