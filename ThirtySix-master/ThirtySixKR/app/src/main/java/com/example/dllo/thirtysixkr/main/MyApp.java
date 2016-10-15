package com.example.dllo.thirtysixkr.main;

import android.app.Application;
import android.content.Context;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;
import cn.smssdk.SMSSDK;

public class MyApp extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        ShareSDK.initSDK(this);
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        SMSSDK.initSDK(this, "17f10618e35e2", "b9dfbc73511d9265b88f096656dec121");
    }

    public static Context getContext() {
        return mContext;
    }
}
