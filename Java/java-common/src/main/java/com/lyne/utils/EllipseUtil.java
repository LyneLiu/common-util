package com.lyne.utils;

/**
 *  判断当前点是否在椭圆上
 * Created by nn_liu on 2017/6/9.
 */
public class EllipseUtil {

    // 椭圆中心坐标
    private float xCenter;
    private float yCenter;

    // 椭圆横轴半径
    private float xRadius = 50;
    // 椭圆纵轴半径
    private float yRadius = 20;

    /**
     * true表示点在椭圆外；false表示点在椭圆内或者椭圆上
     * @param x
     * @param y
     * @return
     */
    public boolean isInEllipse(float x,float y){
        xCenter = 0;
        yCenter = 0;
        return ((x-xCenter)*(x-xCenter))/(xRadius*xRadius) + ((y-yCenter)*(y-yCenter))/(yRadius*yRadius) > 1;
    }

    public static void main(String[] args) {
        EllipseUtil ellipseUtil = new EllipseUtil();
        System.out.println(ellipseUtil.isInEllipse(20,20));

        System.out.println("F".equalsIgnoreCase(null));
    }

}
