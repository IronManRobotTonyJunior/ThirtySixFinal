package com.example.dllo.noonsharemediaplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPause;
    private Button btnStart;
    private Button btnStop;
    private SeekBar seekBar;
    private TextView tvPlay;
    private TextView tvPlaying;
    private MediaPlayer mMediaPlayer;
    private int position;
    private int duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMediaPlayer = MediaPlayer.create(this, R.raw.journeytothewest);
        btnPause = (Button) findViewById(R.id.btn_pause);
        btnStart = (Button) findViewById(R.id.btn_start);
        btnStop = (Button) findViewById(R.id.btn_stop);
        tvPlay = (TextView) findViewById(R.id.play_time);
        tvPlaying = (TextView) findViewById(R.id.playing_time);
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        btnStop.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        duration = mMediaPlayer.getDuration();
        tvPlay.setText(changeTime(duration));

        if (mMediaPlayer.isPlaying()) {
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    mMediaPlayer.seekTo((int) (progress / 100f * duration));

                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                mMediaPlayer.start();
                break;

            case R.id.btn_pause:
                mMediaPlayer.pause();
                break;

            case R.id.btn_stop:
                mMediaPlayer.stop();
                seekBar.setProgress(0);
                try {
                    mMediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public String changeTime(int duration) {
        int minute = duration / 1000 / 60;
        int second = duration / 1000 % 60;
        String m = String.valueOf(minute);
        String s = second >= 10 ? String.valueOf(second) : "0" + String.valueOf(second);
        return m + ":" + s;
    }
}
