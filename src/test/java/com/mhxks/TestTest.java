package com.mhxks;

import com.mhxks.img.NNImage;
import com.mhxks.math.Matrix;
import com.mhxks.math.NNMath;
import com.mhxks.wrapper.Parameter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

public class TestTest {
    public static void main(String[] args) throws Exception{
        while (true){
            // 创建文件选择器
            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setCurrentDirectory(new File("data/minst_img/train"));

            // 显示文件选择窗口
            int returnValue = fileChooser.showOpenDialog(null);

            // 处理文件选择结果
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                // 用户选择了文件
                System.out.println("你选择的文件: " + fileChooser.getSelectedFile().getAbsolutePath());


                //获取选中图片
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                //使用自制的图片加载器
                NNImage test = new NNImage(file);

                test.showImg();

                //获取图片的矩阵形式
                Matrix matrix = test.getMatrix();
                //加载模型
                Parameter parameter = Parameter.loadParameter(new File("data.nn"));

                //加载NN
                NeuralNetwork784 nn = new NeuralNetwork784();
                Matrix out = nn.predict(NNMath.reshape(matrix,784,1),parameter.b,parameter.w,parameter.c);
                System.out.println("预测数字:"+NNMath.maxIndex(out));
                Scanner scanner = new Scanner(System.in);
                System.out.println("输入q退出,输入其他继续");
                if(scanner.next()=="q"){
                    break;
                }
                else{
                    test.closeImg();
                }
            } else {
                // 用户取消了文件选择
                System.out.println("没有选择文件");
                break;
            }


        }

    }
}
