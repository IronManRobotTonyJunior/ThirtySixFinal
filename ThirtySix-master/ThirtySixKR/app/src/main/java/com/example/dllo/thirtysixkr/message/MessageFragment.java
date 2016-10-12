package com.example.dllo.thirtysixkr.message;

import android.content.Intent;

import com.example.dllo.thirtysixkr.base.BaseFragment;
import com.example.dllo.thirtysixkr.R;
import com.example.dllo.thirtysixkr.login.LoginActivity;

public class MessageFragment extends BaseFragment {
    @Override
    protected int setLayout() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);

    }

    @Override
    protected void initData() {

    }
}
