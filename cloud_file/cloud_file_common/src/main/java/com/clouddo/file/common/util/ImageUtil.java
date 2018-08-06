package com.clouddo.file.common.util;

import net.coobird.thumbnailator.Thumbnails;

import java.io.*;

/**
 * 图片压缩个工具
 * @author zhongming
 * @since 3.0
 * 2018/7/26上午8:51
 */
public class ImageUtil {

    /**
     * 压缩图片
     * @param inputStream 图片输入流
     * @param outputStream 图片输出流
     * @param scale 图片压缩的大小 0.5代表一半
     * @throws IOException
     */
    public static void compress(InputStream inputStream, OutputStream outputStream, Double scale) throws IOException {
        compress(inputStream, outputStream, scale, 1.0);
    }

    /**
     * 压缩图片
     * @param inputStream 图片输入流
     * @param outputStream 图片输出流
     * @param scale 图片压缩的大小 0.5代表一半
     * @param quality 图片压缩质量 1最好  0最差
     * @throws IOException
     */
    public static void compress(InputStream inputStream, OutputStream outputStream, Double scale, Double quality) throws IOException {
        Thumbnails.of(inputStream)
                .scale(scale)
                .outputQuality(quality)
                .toOutputStream(outputStream);
    }

    /**
     * 压缩图片
     * @param inputStream 图片输入流
     * @param outputStream 图片输出流
     * @param width 图片压缩后宽度
     * @param height 图片压缩后高度
     * @param quality 图片质量
     * @throws IOException
     */
    public static void compress(InputStream inputStream, OutputStream outputStream, Integer width, Integer height, Double quality) throws IOException {
        Thumbnails.of(inputStream)
                .size(width, height)
                .outputQuality(quality)
                .toOutputStream(outputStream);
    }

    /**
     * 压缩图片
     * @param inputStream 图片输入流
     * @param outputStream 图片输出流
     * @param width 图片压缩后宽度
     * @param height 图片压缩后高度
     * @throws IOException
     */
    public static void compress(InputStream inputStream, OutputStream outputStream, Integer width, Integer height) throws IOException {
        compress(inputStream, outputStream, width, height, 1.0);
    }

    /**
     * 按照宽度等比压缩
     * @param inputStream 图片输入流
     * @param outputStream 图片输出流
     * @param width 图片压缩后宽度
     */
    public static void compress(InputStream inputStream, OutputStream outputStream, Integer width) throws IOException {
        //获取图片原宽度
//        Integer widthOld = Thumbnails.of(inputStream)
//                .width(width)
//                .asBufferedImage()
//                .getWidth();
//        double scale = width/widthOld;
//        compress(inputStream, outputStream, scale);
        Thumbnails.of(inputStream)
                .width(width)
                .outputQuality(1.0)
                .toOutputStream(outputStream);
    }





}
