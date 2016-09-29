package com.example.dllo.thirtysixkr.tools.webrequest;

import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

public class GsonRequest<T> extends Request<T> {
    private final Response.Listener<T> mListener;
    private Class<T> clazz;
    public GsonRequest(int method, String url,Class<T> clazz,Response.Listener<T> mListener, Response.ErrorListener listener) {
        super(method, url, listener);
        this.mListener = mListener;
        this.clazz = clazz;
    }
    public GsonRequest(String url, Class<T> clazz, Response.Listener<T> mListener,
                       Response.ErrorListener listener){
        this(Method.GET,url,clazz,mListener,listener);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        Gson gson = new Gson();
        return Response.success(gson.fromJson(parsed,clazz), HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);

    }
    @Override
    public void deliverError(VolleyError error) {
        if (error instanceof NoConnectionError) {
            Cache.Entry entry = this.getCacheEntry();
            if (entry != null) {
                Log.d("数据", "这是缓存数据");
                Response<T> response = parseNetworkResponse(new NetworkResponse(entry.data, entry.responseHeaders));
                deliverResponse(response.result);
                return;
            }
        }
        super.deliverError(error);

    }
}
