package com.github.king.musicPlayView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.github.king.musicplayview.R;

/**
 * @author Created by jinxl on 2018/10/17.
 */
public class MusicPlayView extends LinearLayout {
    public MusicPlayView(Context context) {
        this(context, null);
    }

    public MusicPlayView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicPlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.layout_music_play_view, this);

    }
}
