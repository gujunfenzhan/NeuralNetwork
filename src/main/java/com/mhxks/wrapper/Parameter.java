package com.mhxks.wrapper;

import com.mhxks.math.Matrix;
import com.mhxks.math.NNMath;

import java.io.*;
import java.nio.ByteBuffer;
/*
对参数的包装
包含w,b,c三个参数
 */
public class Parameter {
    public Matrix c;
    public Matrix b;
    public Matrix w;
    public Parameter(Matrix b,Matrix c,Matrix w){
        this.w = w;
        this.c = c;
        this.b = b;

    }

    //对参数的每个元素增加，通常做梯度更新操作
    public void add(Parameter parameter){
        this.w = NNMath.add(parameter.w,w);
        this.b = NNMath.add(parameter.b,b);
        this.c = NNMath.add(parameter.c,c);
    }
    //对参数的每个元素除以一个数值
    public void divide(int rate){
        this.w = NNMath.divide(rate,w);
        this.b = NNMath.divide(rate,b);
        this.c = NNMath.divide(rate,c);
    }
    //保存参数到本地
    public void saveParameter(File file)throws Exception{
        if(!file.isFile()){
            file.createNewFile();
        }
        OutputStream outputStream = new FileOutputStream(file);
        byte[] bytes = serialize();

        ByteBuffer buffer = ByteBuffer.allocate(bytes.length+Integer.BYTES);
        buffer.putInt(bytes.length);
        buffer.put(bytes);

        outputStream.write(buffer.array());
        outputStream.close();
    }
    //加载本地参数
    public static Parameter loadParameter(File file)throws Exception{
        InputStream inputStream = new FileInputStream(file);
        byte[] len_b = new byte[Integer.BYTES];
        inputStream.read(len_b);
        ByteBuffer buffer = ByteBuffer.wrap(len_b);
        byte[] bytes = new byte[buffer.getInt()];
        inputStream.read(bytes);
        inputStream.close();
        return deserialize(bytes);
    }

    //序列化
    public byte[] serialize(){
        byte[] w_bytes = this.w.serialize();
        byte[] b_bytes = this.b.serialize();
        byte[] c_bytes = this.c.serialize();
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES*3+w_bytes.length+b_bytes.length+c_bytes.length);
        buffer.putInt(w_bytes.length);
        buffer.put(w_bytes);
        buffer.putInt(b_bytes.length);
        buffer.put(b_bytes);
        buffer.putInt(c_bytes.length);
        buffer.put(c_bytes);
        return buffer.array();
    }
    //反序列化
    public static Parameter deserialize(byte[] bytes){
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        byte[] w_bytes = new byte[buffer.getInt()];
        buffer.get(w_bytes);
        byte[] b_bytes = new byte[buffer.getInt()];
        buffer.get(b_bytes);
        byte[] c_bytes = new byte[buffer.getInt()];
        buffer.get(c_bytes);
        return new Parameter(Matrix.deserialize(b_bytes),Matrix.deserialize(c_bytes),Matrix.deserialize(w_bytes));
    }

}
