package com.example.dllo.thirtysixkr.main;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.dllo.thirtysixkr.R;
import com.example.dllo.thirtysixkr.base.BaseActivity;
import com.example.dllo.thirtysixkr.find.FindFragment;
import com.example.dllo.thirtysixkr.investment.InvestmentFragment;
import com.example.dllo.thirtysixkr.message.MessageFragment;
import com.example.dllo.thirtysixkr.mine.MineFragment;
import com.example.dllo.thirtysixkr.news.NewsFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends BaseActivity {
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> titles = new ArrayList<>();
    private ViewPager vp;
    private TabLayout tb;
    private long timeSeconds;
    private int lastFragment = 0;


    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        vp = bindView(R.id.main_vp);
        tb = bindView(R.id.main_tb);
        fragments.add(new NewsFragment());
        fragments.add(new InvestmentFragment());
        fragments.add(new FindFragment());
        fragments.add(new MessageFragment());
        fragments.add(new MineFragment());
        titles.add("新闻");
        titles.add("股权投资");
        titles.add("发现");
        titles.add("消息");
        titles.add("我的");
        timeSeconds = Calendar.getInstance().getTimeInMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String str = format.format(timeSeconds);
    }

    @Override
    protected void initData() {
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager());
        adapter.setFragments(fragments);
        adapter.setTitles(titles);
        vp.setAdapter(adapter);
        tb.setupWithViewPager(vp);
        tb.getTabAt(0).setIcon(R.drawable.selector_news);
        tb.getTabAt(1).setIcon(R.drawable.selector_investment);
        tb.getTabAt(2).setIcon(R.drawable.selector_find);
        tb.getTabAt(3).setIcon(R.drawable.selector_message);
        tb.getTabAt(4).setIcon(R.drawable.selector_mine);
        tb.setSelectedTabIndicatorColor(Color.parseColor("#4285f4"));
        tb.setTabTextColors(Color.GRAY, Color.parseColor("#4285f4"));


        tb.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() != 3) {
                    lastFragment = tab.getPosition();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        vp.setCurrentItem(lastFragment);
    }
}
