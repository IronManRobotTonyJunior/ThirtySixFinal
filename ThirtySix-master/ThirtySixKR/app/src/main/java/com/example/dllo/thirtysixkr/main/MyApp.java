package com.example.dllo.thirtysixkr.main;

import android.app.Application;
import android.content.Context;

import cn.sharesdk.framework.ShareSDK;

public class MyApp extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        ShareSDK.initSDK(this);
    }
    public static Context getContext(){
        return mContext;
    }
}
