package com.mhxks.minst;

import com.mhxks.math.Matrix;
import com.mhxks.math.NNMath;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
/*
数据集读取
读取.idx3-ubyte和idx-ubyte
 */
public class MINST {
    public String data_path = "data";
    public String ImgFile_path;
    public String LabelFile_path;
    public List<Matrix> images = new ArrayList<>();
    public List<Integer> labels = new ArrayList<>();
    public int item_Size;
    /*
    data_name: 数据文件夹名
    ImgFile: 图片数据文件名
    LabelFile: 标签数据文件名
    readSize: 读取图片数量
     */
    public MINST(String data_name,String ImgFile,String LabelFile,int readSize)throws Exception{
        item_Size = readSize;


        this.ImgFile_path = data_path+"/"+data_name+"/train/"+ImgFile;
        this.LabelFile_path = data_path+"/"+data_name+"/train/"+LabelFile;

        InputStream inputStream = new FileInputStream(new File(ImgFile_path));
        InputStream labelInput = new FileInputStream(new File(LabelFile_path));

        //读取flag
        int magicNumber = readInt(inputStream);
        readInt(labelInput);

        //图片数量
        int item_Size = readInt(inputStream);
        readInt(labelInput);


        int height = readInt(inputStream);
        int width = readInt(inputStream);
        for (int i = 0; i < item_Size; i++) {
            if(i>=readSize){
                break;
            }

            System.out.println(i+1);
            List<double[]> list = new ArrayList<>();
            for (int i1 = 0; i1 < height; i1++) {
                double[] doubles = new double[width];
                for (int i2 = 0; i2 < width; i2++) {
                    doubles[i2] = readdoubleByOneByte(inputStream);
                }
                list.add(doubles);

            }
            this.images.add(new Matrix(list));
            this.labels.add(labelInput.read());




        }
        inputStream.close();
        labelInput.close();



    }
    //从数据流中读取一个4 byte的int数据
    public int readInt(InputStream inputStream)throws Exception{
        byte[] bytes = new byte[4];
        inputStream.read(bytes);

        int result = 0;
        for(int i = 0;i<4;i++){
            int byteValue = bytes[i] &0XFF;
            result = (result<<8) | byteValue;
        }
        return result;
    }
    //读取一个1 byte的double数据
    public double readdoubleByOneByte(InputStream inputStream)throws Exception{
        double doubleValue = inputStream.read();


        return doubleValue;
    }
    //获取指定索引的图片矩阵数据
    //输入 i
    //输出 nxm
    public Matrix getImg(int index){
        return this.images.get(index);
    }
    //在获取图片矩阵前对图片形状变换成1行
    public Matrix getImgAndReshape(int index){
        Matrix img = this.images.get(index);
        Matrix reshapeImg = NNMath.reshape(img,img.getWidth()*img.getHeight(),1);
        return reshapeImg;
    }
    //返回指定索引的标签
    public int getLabel(int index){
        return this.labels.get(index);
    }
    //返回指定索引的标签并转成onehot
    public Matrix getLabelByOneHot(int index){
        int label = this.getLabel(index);
        //创建单位矩阵
        Matrix e = new Matrix(10);
        return new Matrix(e.list.get(label));
    }

}
