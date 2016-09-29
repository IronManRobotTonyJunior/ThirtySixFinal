package com.example.dllo.thirtysixkr.tools.webrequest;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.dllo.thirtysixkr.MyApp;

public class VolleySingleton {

    private static VolleySingleton mVolleySingleton;

    private RequestQueue mRequestQueue;

    private VolleySingleton() {
       mRequestQueue = Volley.newRequestQueue(MyApp.getContext());
    }

    public static VolleySingleton getInstance() {
        if (mVolleySingleton == null) {
            synchronized (VolleySingleton.class) {
                if (mVolleySingleton == null) {
                    mVolleySingleton = new VolleySingleton();
                }
            }
        }
        return mVolleySingleton;
    }

    public RequestQueue getmRequestQueue(){
        return mRequestQueue;
    }

    public void addRequest(Request request){
        mRequestQueue.add(request);
    }


}
