package com.mhxks;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

public class LoadImage {
    public static void main(String[] args) throws Exception{

        BufferedImage image = ImageIO.read(new File("H:\\development\\神经网络\\data\\minst_img\\train/5/8a80e849-f5c3-41b7-8301-426cbed1392a.png")); // 读取灰度图像为BufferedImage对象
        byte[] pixelData = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.out.println(pixelData);
    }
}
