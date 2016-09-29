package com.example.dllo.thirtysixkr.web.richtext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;


/**
 * Created by ChenFengYao on 16/7/29.
 */
public class UrlImageGetter implements Html.ImageGetter {
    Context mContext;
    TextView container;
    int width;

    public UrlImageGetter(Context context, TextView container) {
        mContext = context;
        this.container = container;
        width = mContext.getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public Drawable getDrawable(String s) {
        final UrlDrawable urlDrawable = new UrlDrawable();
        Log.d("UrlImageGetter", s);

        Glide.with(mContext).load(s).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if(resource!=null){
                    urlDrawable.mBitmap = resource;
                    urlDrawable.setBounds(0,0,resource.getWidth(),resource.getHeight());
                    container.invalidate();
                    container.setText(container.getText());
                }
            }
        });
        return urlDrawable;
    }
}
