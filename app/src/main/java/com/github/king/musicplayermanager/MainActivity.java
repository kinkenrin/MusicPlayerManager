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
