package com.github.king.musicplayermanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.king.musicPlayView.MusicPlayView;

public class MainActivity extends AppCompatActivity {
    MusicPlayView mpv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mpv_test = findViewById(R.id.mpv_test);
        mpv_test.setEnable(true);
        mpv_test.setDataSource("http://sobot-test.oss-cn-beijing.aliyuncs.com/pass/record/36a7403308f74eaca63c32184cc31b09/d3576afb24904b44a6e74a6cb6ea9ea1/20181030/a73c2524-dc25-11e8-bfae-6bd08da289a3.wav");
    }

    @Override
    protected void onPause() {
        mpv_test.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mpv_test.resume();
    }

}
