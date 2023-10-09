package com.mhxks.img;

import com.mhxks.math.Matrix;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NNImage {
        Matrix matrix;
        BufferedImage image;
        JFrame jFrame;
    public NNImage(File file)throws Exception{
            //加载图片
            loadImage(file);




    }
    public NNImage(BufferedImage bufferedImage)throws Exception{
        this.image = bufferedImage;
        loadImage(null);
    }


    public void loadImage(File file)throws Exception{

            // 读取灰度图像为BufferedImage对象
            if (file!=null){
                image = ImageIO.read(file);
            }

            //图片宽度
            int width = image.getWidth();
            //图片高度
            int height = image.getHeight();
            //图片byte原数据，共784个byte一一对应像素数据
            byte[] pixelData = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
            //需要把byte转成0-255，而不是-128到127，数据训练时使用的0-255
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(pixelData);
            List<double[]> list = new ArrayList<>();
            // 构造矩阵
            for (int y = 0; y < height; y++) {
                    double[] doubles = new double[width];
                    for (int x = 0; x < width; x++) {

                            //int grayValue = image.getRGB(x, y) & 0xFF; // 获取灰度值（范围：0-255）
                            //doubles[x] = grayValue;
                            //System.out.println("Pixel at (" + x + "," + y + "): Gray Value: " + grayValue);
                        //doubles[x] = (int)pixelData[y*width+x];
                        doubles[x] = byteArrayInputStream.read();
                    }
                    list.add(doubles);
            }
            byteArrayInputStream.close();
            this.matrix = new Matrix(list);
    }
    public void showImg(){
        // 创建一个 JFrame 窗口
        jFrame = new JFrame("Image Display");
        // 创建一个 JPanel 用于展示图片
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 在 JPanel 上绘制 BufferedImage
                g.drawImage(image, 0, 0,280 ,280,this);
            }

        };
        // 将 JPanel 添加到 JFrame 中
        jFrame.getContentPane().add(panel);

        // 设置 JFrame 的大小和关闭操作
        jFrame.setSize(280, 280);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 显示 JFrame

        jFrame.setVisible(true);

    }
    //关闭图片窗口
    public void closeImg(){
        jFrame.setVisible(false);
    }
    //获取BufferedImage
    public Image getImage(){
            return this.image;
    }
    //获取图像的矩阵形式
    public Matrix getMatrix(){
        return this.matrix;
    }
}
