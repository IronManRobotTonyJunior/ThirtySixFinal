package com.example.dllo.onehalf;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FragmentB extends Fragment {

    private TextView tv;

    // Fragment的构造方法,是千万不能写参数的!
    // 导致横屏异常
    // 而且 Android 不建议写Fragment的构造方法
    public FragmentB() {
        // 注册EventBus
        //  super();
        EventBus.getDefault().register(this);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_down, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv = (TextView) view.findViewById(R.id.tv_bundle);

    }

    /**
     * 注册对象的方法
     * @param //一定要用@sendTextEvent 标识
     * 参数类型 是事件类, 而且 只能有一个参数
     *
     * EventBus 可以指定, 回调的方法, 在哪个线程中
     * 当使用EventBus 的时候, 只要刷新UI 即一定要声明
     * threadMode 是在主线程中的~!!!!!!!!!!!!!!!!!!!!!
     *
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sendText(SendTextEvent sendTextEvent){
        String text = sendTextEvent.getText();
        tv.setText(text);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注册的对象 , 在没用了之后, 一应一定要取消注册
        EventBus.getDefault().unregister(this);
    }
}
