package com.example.dllo.thirtysixkr.tools.webrequest;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class SendGetRequest {

    public static <T> void sendGetRequest(String url, Class<T> clazz, final OnResponseListener<T> listener){
        GsonRequest<T> gsonRequest = new GsonRequest<>(url, clazz, new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError();
            }
        });

        VolleySingleton.getInstance().addRequest(gsonRequest);
    }

    public interface OnResponseListener<T>{
        void onResponse(T response);
        void onError();
    }
}
