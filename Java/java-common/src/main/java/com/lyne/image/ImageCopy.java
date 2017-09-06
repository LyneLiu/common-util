package com.lyne.image;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by nn_liu on 2017/4/1.
 */

public class ImageCopy {

    public void jpgTset() throws Exception {
        File _file = new File("F:\\temp\\image\\Desert.jpg"); //读入文件
        Image src = javax.imageio.ImageIO.read(_file); //构造Image对象
        int wideth=src.getWidth(null); //得到源图宽
        int height=src.getHeight(null); //得到源图长

        BufferedImage tag = new BufferedImage(wideth,height,BufferedImage.TYPE_INT_RGB);
        tag.getGraphics().drawImage(src,0,0,wideth,height,null);
        FileOutputStream out=new FileOutputStream("F:\\temp\\image\\DesertCopy.jpg"); //输出到文件流
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(tag); //JPEG编码
        out.close();
    }

    public static void main(String[] args) {
        try {
            new ImageCopy().jpgTset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
