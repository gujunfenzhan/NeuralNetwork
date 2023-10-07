package com.mhxks;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

public class ToImg {

    public static void main(String[] args) throws Exception{


        File img_File = new File("H:\\development\\神经网络\\data\\minst\\train\\train-images.idx3-ubyte");
        File label_File = new File("H:\\development\\神经网络\\data\\minst\\train\\train-labels.idx1-ubyte");
        File savePath = new File("H:\\development\\神经网络\\data\\minst_img\\train");


        //初始化目录
        for (int i1 = 0; i1 < 10; i1++) {
            File dir = new File(savePath,""+i1);
            if(!dir.exists()){
                dir.mkdirs();
            }
        }


        InputStream img_input = new FileInputStream(img_File);
        InputStream label_input = new FileInputStream(label_File);
        readInt(img_input);
        readInt(label_input);
        int size = readInt(img_input);
        readInt(label_input);
        int height = readInt(img_input);
        int width = readInt(img_input);
        System.out.println(height);
        System.out.println(width);
        for (int i = 0; i < size; i++) {
            byte[] img_bytes = new byte[height*width];
            img_input.read(img_bytes);
            int label = label_input.read();

            BufferedImage image = ImageIO.read(new File("H:\\development\\神经网络\\data\\minst_img\\train/5/8a80e849-f5c3-41b7-8301-426cbed1392a.png")); // 读取灰度图像为BufferedImage对象
            byte[] pixelData = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
            for (int i1 = 0; i1 < pixelData.length; i1++) {
                System.out.println(img_bytes[i1]==pixelData[i1]);
            }
            System.out.println("1");

            //saveImg(img_bytes,savePath.getAbsolutePath()+"/"+label+"/"+UUID.randomUUID().toString()+".png",width,height);


        }
    }
    public static void saveImg(byte[] bytes,String path,int width,int height)throws Exception{
        // 创建一个BufferedImage对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        // 将字节数组写入图像
        image.getRaster().setDataElements(0, 0, width, height, bytes);


        File outputFile = new File(path);

        ImageIO.write(image,"png",outputFile);

    }


    public static int readInt(InputStream inputStream)throws Exception{
        byte[] bytes = new byte[4];
        inputStream.read(bytes);

        int result = 0;
        for(int i = 0;i<4;i++){
            int byteValue = bytes[i] &0XFF;
            result = (result<<8) | byteValue;
        }
        return result;
    }
    public static float readFloatByOneByte(InputStream inputStream)throws Exception{
        float floatValue = inputStream.read();


        return floatValue;
    }
}
