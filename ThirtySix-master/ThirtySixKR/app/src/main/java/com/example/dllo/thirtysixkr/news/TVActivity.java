package com.example.dllo.thirtysixkr.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.dllo.thirtysixkr.R;
import com.example.dllo.thirtysixkr.base.BaseActivity;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

public class TVActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";
    private static final String VIDEO_URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

    private View mVideoLayout;
    private UniversalVideoView videoView;
    private UniversalMediaController mediaController;
    private View mBottom;


    private int mSeekposition;

    private int cacheHeight;

    private boolean isFullscreen;
    private Button mStart;

    private SharedPreferences mSetting;

    private boolean isWifi;


    @Override
    protected int setLayout() {
        return R.layout.activity_tv;
    }

    @Override
    protected void initView() {
        mVideoLayout = findViewById(R.id.video_layout);
        videoView = (UniversalVideoView) findViewById(R.id.video_view);
        mediaController = (UniversalMediaController) findViewById(R.id.media_controller);
        mBottom = findViewById(R.id.bottom_layout);
        mStart = (Button) findViewById(R.id.start);
        mSetting = getSharedPreferences("settingTimePush", MODE_PRIVATE);

    }

    @Override
    protected void initData() {
        videoView.setMediaController(mediaController);
        setVideoAreaSize();
        videoView.setVideoViewCallback(new UniversalVideoView.VideoViewCallback() {
            @Override
            public void onScaleChange(boolean isFullscreen) {
                TVActivity.this.isFullscreen = isFullscreen;
                if (isFullscreen) {
                    ViewGroup.LayoutParams layoutParams = mediaController.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    videoView.setLayoutParams(layoutParams);
                    mBottom.setVisibility(View.GONE);
                } else {
                    ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    layoutParams.height = TVActivity.this.cacheHeight;
                    videoView.setLayoutParams(layoutParams);
                    mBottom.setVisibility(View.VISIBLE);
                }
                switchTitleBar(!isFullscreen);
            }

            private void switchTitleBar(boolean b) {
                ActionBar actionBar = getSupportActionBar();

                if (actionBar != null) {
                    if (b) {
                        actionBar.show();
                    } else {
                        actionBar.hide();
                    }
                }
            }

            @Override
            public void onPause(MediaPlayer mediaPlayer) {

            }

            @Override
            public void onStart(MediaPlayer mediaPlayer) {

            }

            @Override
            public void onBufferingStart(MediaPlayer mediaPlayer) {

            }

            @Override
            public void onBufferingEnd(MediaPlayer mediaPlayer) {

            }
        });
        mStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mSeekposition > 0) {
                    videoView.seekTo(mSeekposition);
                }
                isWifi = mSetting.getBoolean("isWifi", true);
                if (isWifi) {
                    videoView.start();
                } else {
                    ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo.State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
                    if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
                        videoView.start();
                    } else {
                        Toast.makeText(TVActivity.this, "您的Wifi没有打开,会耗费你的大量流量,如果收看,请打开允许非Wifi下播放视频", Toast.LENGTH_SHORT).show();
                    }
                }
                mediaController.setTitle("氪TV");
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d(TAG, "onCompletion");

            }
        });


    }

    @Override
    public void onBackPressed() {
        if (isFullscreen) {
            videoView.setFullscreen(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SEEK_POSITION_KEY, mSeekposition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mSeekposition = savedInstanceState.getInt(SEEK_POSITION_KEY);
    }

    private void setVideoAreaSize() {
        mVideoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = mVideoLayout.getWidth();
                cacheHeight = (int) (width * 405f / 720f);
                ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = cacheHeight;
                mVideoLayout.setLayoutParams(videoLayoutParams);
                videoView.setVideoPath(VIDEO_URL);
                videoView.requestFocus();
            }
        });
    }

}
