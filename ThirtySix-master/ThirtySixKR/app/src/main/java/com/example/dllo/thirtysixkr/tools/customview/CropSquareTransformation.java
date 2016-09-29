package com.example.dllo.thirtysixkr.tools.customview;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

public class CropSquareTransformation implements Transformation {
    int targetWidth = 80;
    int targetHeight = 80;
    @Override
    public Bitmap transform(Bitmap source) {
        if (source.getWidth() == 0 || source.getHeight() == 0) {
            return source;
        }

        if (source.getWidth() > source.getHeight()) {//横向长图
            if (source.getHeight() <  targetHeight && source.getWidth() <= 400) {
                return source;
            } else {
                //如果图片大小大于等于设置的高度，则按照设置的高度比例来缩放
                double aspectRatio = (double) source.getWidth() / (double) source.getHeight();
                int width = (int) (targetHeight * aspectRatio);
                if (width > 400) { //对横向长图的宽度 进行二次限制
                    width = 400;
                    targetHeight = (int) (width / aspectRatio);// 根据二次限制的宽度，计算最终高度
                }
                if (width != 0 && targetHeight != 0) {
                    Bitmap result = Bitmap.createScaledBitmap(source, width, targetHeight, false);
                    if (result != source) {
                        // Same bitmap is returned if sizes are the same
                        source.recycle();
                    }
                    return result;
                } else {
                    return source;
                }
            }
        } else {//竖向长图
            //如果图片小于设置的宽度，则返回原图
            if (source.getWidth() < targetWidth && source.getHeight() <= 600) {
                return source;
            } else {
                //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int height = (int) (targetWidth * aspectRatio);
                if (height > 600) {//对横向长图的高度进行二次限制
                    height = 600;
                    targetWidth = (int) (height / aspectRatio);//根据二次限制的高度，计算最终宽度
                }
                if (height != 0 && targetWidth != 0) {
                    Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, height, false);
                    if (result != source) {
                        // Same bitmap is returned if sizes are the same
                        source.recycle();
                    }
                    return result;
                } else {
                    return source;
                }
            }
        }
    }


    @Override
    public String key() {
        return "desiredWidth" + " desiredHeight";
    }

}
