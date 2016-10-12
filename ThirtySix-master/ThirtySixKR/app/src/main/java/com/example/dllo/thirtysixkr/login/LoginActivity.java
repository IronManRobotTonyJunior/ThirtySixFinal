package com.example.dllo.thirtysixkr.login;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.dllo.thirtysixkr.R;
import com.example.dllo.thirtysixkr.base.BaseActivity;

import java.util.ArrayList;

public class LoginActivity extends BaseActivity {

    private LoginAdapter adapter;
    private ViewPager vp;
    private TabLayout tb;

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        tb = bindView(R.id.login_tb);
        vp = bindView(R.id.login_vp);



    }

    @Override
    protected void initData() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new LoginFragment());
        fragments.add(new RegisterFragment());
        ArrayList<String> titles = new ArrayList<>();
        titles.add("登录");
        titles.add("注册");
        adapter = new LoginAdapter(getSupportFragmentManager(), fragments, titles);
        vp.setAdapter(adapter);
        tb.setupWithViewPager(vp);
    }
}
