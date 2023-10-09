package com.mhxks;
import com.mhxks.img.NNImage;
import com.mhxks.math.Matrix;
import com.mhxks.math.NNMath;
import com.mhxks.wrapper.Parameter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.*;
import java.io.File;
import java.util.UUID;

public class PaintBoard
extends JFrame{
    private int prevX, prevY;
    private boolean drawing = false;
    private DrawArea drawArea;
    public static int pensize = 20;
    public static int gussion = 8;
    public static BufferedImage applyGaussianBlur(BufferedImage inputImage, int radius) {
        int size = radius * 2 + 1;
        float[] data = new float[size * size];

        float sigma = radius / 3.0f;
        float twoSigmaSquare = 2.0f * sigma * sigma;
        float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
        float total = 0.0f;

        for (int i = -radius; i <= radius; ++i) {
            for (int j = -radius; j <= radius; ++j) {
                float distance = i * i + j * j;
                int index = (i + radius) * size + (j + radius);
                data[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
                total += data[index];
            }
        }

        for (int i = 0; i < data.length; ++i) {
            data[i] /= total;
        }

        Kernel kernel = new Kernel(size, size, data);
        BufferedImageOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = outputImage.getRaster();
        ColorModel cm = outputImage.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] pixels = dataBuffer.getData();

        op.filter(inputImage, outputImage);
        return outputImage;
    }


    private PaintBoard(){
        setTitle("自定义画板");
        setSize(280, 360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        // 创建画布
        drawArea = new DrawArea();


        JButton clearButton = new JButton("清空");

        // 添加识别按钮
        JButton recognizeButton = new JButton("识别");
        recognizeButton.addActionListener(e -> {
            BufferedImage gussionImg = applyGaussianBlur(drawArea.getImage(), gussion);
            BufferedImage scaledImage = scaleImage(gussionImg, 28, 28);

            try {
                NNImage nnImage = new NNImage(scaledImage);
                Matrix matrix = nnImage.getMatrix();
                //matrix.print();
                Parameter parameter = Parameter.loadParameter(new File("my_data.nn"));
                //加载NN
                NeuralNetwork784 nn = new NeuralNetwork784();
                Matrix out = nn.predict(NNMath.reshape(matrix,784,1),parameter.b,parameter.w,parameter.c);
                System.out.println("预测数字:"+NNMath.maxIndex(out));

                ImageIO.write(scaledImage,"png",new File("test/"+ UUID.randomUUID().toString()+".png"));
            }catch (Exception a){
                a.printStackTrace();
            }

            //printPixelValues(scaledImage);
        });

        clearButton.addActionListener(e ->{
            drawArea.clear();
        });


        recognizeButton.setBounds(0,280,140,40);

        clearButton.setBounds(140,280,140,40);

        drawArea.setBounds(0,0,280,280);

        drawArea.setBackground(Color.BLACK);

        panel.add(drawArea);

        panel.add(recognizeButton);

        panel.add(clearButton);

        // 添加鼠标监听器
        drawArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                prevX = e.getX();
                prevY = e.getY();
                drawing = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                drawing = false;
            }
        });

        drawArea.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (drawing) {
                    int x = e.getX();
                    int y = e.getY();
                    drawArea.drawLine(prevX, prevY, x, y);
                    prevX = x;
                    prevY = y;
                }
            }
        });

        getContentPane().add(panel);
    }

    private BufferedImage scaleImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2 = resizedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2.dispose();
        return resizedImage;
    }

    private void printPixelValues(BufferedImage image) {
        for (int y = 0; y < image.getHeight(); y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;
                int gray = (red + green + blue) / 3; // 简单灰度化处理
                sb.append(gray).append("\t");
            }
            System.out.println(sb.toString());
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            PaintBoard paintBoard = new PaintBoard();
            paintBoard.setVisible(true);
        });
    }

    private static class DrawArea extends JPanel {
        private BufferedImage image;
        private Graphics2D g2;

        private DrawArea() {
            setDoubleBuffered(false);
        }

        public BufferedImage getImage() {
            return image;
        }

        private void initImage() {
            if (image == null) {
                image = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_BYTE_GRAY);
                g2 = (Graphics2D) image.getGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                clear();
            }
        }

        public void clear() {
            initImage();
            g2.setPaint(Color.BLACK);
            g2.fillRect(0, 0, getSize().width, getSize().height);
            repaint();
        }

        public void drawLine(int x1, int y1, int x2, int y2) {
            initImage();
            g2.setPaint(Color.WHITE);
            g2.setStroke(new BasicStroke(pensize)); // 设置笔粗细
            g2.drawLine(x1, y1, x2, y2);
            repaint();
        }
        // 添加一个新方法来应用高斯模糊效果

        @Override
        protected void paintComponent(Graphics g) {
            if (image != null) {
                g.drawImage(image, 0, 0, null);
            }
        }
    }
}
