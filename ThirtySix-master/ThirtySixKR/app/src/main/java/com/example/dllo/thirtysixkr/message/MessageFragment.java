package com.example.dllo.thirtysixkr.message;

import android.app.Activity;
import android.content.Intent;

import com.example.dllo.thirtysixkr.R;
import com.example.dllo.thirtysixkr.base.BaseFragment;
import com.example.dllo.thirtysixkr.login.LoginActivity;

public class MessageFragment extends BaseFragment {

    private boolean isLayoutLoad;



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isLayoutLoad) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            ((Activity) mContext).startActivityForResult(intent, 100);
        }
    }

    @Override
    protected int setLayout() {
        isLayoutLoad = true;
        return R.layout.fragment_message;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        if (getUserVisibleHint()) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            ((Activity) mContext).startActivityForResult(intent, 100);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isLayoutLoad = false;
    }
}
