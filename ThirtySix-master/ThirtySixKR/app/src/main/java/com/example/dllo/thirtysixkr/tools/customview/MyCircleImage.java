package com.example.dllo.thirtysixkr.tools.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.dllo.thirtysixkr.R;


public class MyCircleImage extends ImageView{
    private boolean isCircle = false;

    public MyCircleImage(Context context) {
        super(context);
    }

    // 如果自定义属性的话, 会执行这个方法
    public MyCircleImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 通过obtainStyledAttributes方法
        // 取出attrs.xml文件中对应的 MyCircleImage的属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyCircleImage);
        isCircle = array.getBoolean(R.styleable.MyCircleImage_is_circle,false);
    }


    public MyCircleImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isCircle)
        {
            // 在这段代码里画一个圆
            // 获取到src设置的图片
            BitmapDrawable drawable = (BitmapDrawable) getDrawable();
            if (drawable != null){
                // 这个bitmap就是我们的图片转换出来的
                Bitmap bitmap = drawable.getBitmap();
                Bitmap circleBitmap = getOutBitmap(bitmap);
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                Rect rect = new Rect(0 , 0 , circleBitmap.getWidth(),circleBitmap.getHeight());
                canvas.drawBitmap(circleBitmap,rect,rect,paint);

            }



        }
        else {
            super.onDraw(canvas);
        }
    }
    // 自定义的方法 用来获取圆形的bitmap 图片
    private Bitmap getOutBitmap(Bitmap bitmap) {
        // 这里就可以返回以个圆形的bitmap
        // 创建一个空白的画布
        // 创建一个跟原图一样大小的bitmap
        // bitmap的初始化方式
        Bitmap outBitmap = Bitmap.createBitmap(
                bitmap.getWidth(),
                bitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawCircle(bitmap.getWidth()/2,
                          bitmap.getHeight()/2,
                          bitmap.getHeight()/2,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Rect rect = new Rect(0, 0 ,bitmap.getWidth(),getHeight());
        canvas.drawBitmap(bitmap , rect , rect ,paint);
        return outBitmap;
        //
    }
}
