package com.example.dllo.thirtysixkr.web;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

public class MyDrawable extends BitmapDrawable {
    Bitmap bitmap;

    @Override
    public void draw(Canvas canvas) {
        if(bitmap!=null){
            Paint paint = new Paint();
            canvas.drawBitmap(bitmap,0,0,paint);
        }
        super.draw(canvas);

    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
