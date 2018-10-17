package com.github.king.musicPlayView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.king.musicplayview.R;
import com.github.king.player.MusicPalyManager;
import com.github.king.player.MusicProgressViewUpdateHelper;
import com.github.king.player.MusicUtil;

/**
 * @author Created by jinxl on 2018/10/17.
 */
public class MusicPlayView extends LinearLayout implements MusicProgressViewUpdateHelper.Callback {

    private MusicProgressViewUpdateHelper progressViewUpdateHelper;
    PlayPauseDrawable playPauseDrawable;
    boolean isPlaying = false;
    String mDataSource;

    ImageButton ib_playBtn;
    SeekBar progressSlider;
    TextView songTotalTime;
    TextView songCurrentProgress;

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
        View.inflate(getContext(), R.layout.mpm_layout_music_play_view, this);
        ib_playBtn = findViewById(R.id.ib_playBtn);
        progressSlider = findViewById(R.id.progressSlider);
        songCurrentProgress = findViewById(R.id.songCurrentProgress);
        songTotalTime = findViewById(R.id.songTotalTime);
        playPauseDrawable = new PlayPauseDrawable(getContext());
        ib_playBtn.setImageDrawable(playPauseDrawable);
        ib_playBtn.setColorFilter(Color.parseColor("#888E96"), PorterDuff.Mode.SRC_IN);
        playPauseDrawable.setPlay(false);
        progressViewUpdateHelper = new MusicProgressViewUpdateHelper(getContext(), this);
        progressSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    seekTo(progress);
                    MusicPalyManager palyManager = MusicPalyManager.getInstance(getContext());
                    onUpdateProgressViews(palyManager.getSongProgressMillis(), palyManager.getSongDurationMillis());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        ib_playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMusicPlayer();
            }
        });
    }

    private void toggleMusicPlayer() {
        if (!isPlaying) {
            if (setDataSource(mDataSource)) {
                playMusic();
                progressViewUpdateHelper.start();
            }
        } else {
            pauseMusic();
        }

        updatePlayPauseDrawableState(true);
        isPlaying = !isPlaying;
    }

    public boolean setDataSource(String path) {
        mDataSource = path;
        if (MusicPalyManager.getInstance(getContext()).isPlaying()) {
            MusicPalyManager.getInstance(getContext()).pause();
        }
        return MusicPalyManager.getInstance(getContext()).setDataSource(path);
    }

    public void playMusic() {
        MusicPalyManager.getInstance(getContext()).play();
    }

    public void pauseMusic() {
        MusicPalyManager.getInstance(getContext()).pause();
    }

    public int seekTo(int millis) {
        return MusicPalyManager.getInstance(getContext()).seek(millis);
    }

    public void pause() {
        if (progressViewUpdateHelper != null) {
            progressViewUpdateHelper.stop();
        }
    }

    public void resume() {
        if (progressViewUpdateHelper != null) {
            progressViewUpdateHelper.start();
        }
    }

    protected void updatePlayPauseDrawableState(boolean animate) {
        if (isPlaying) {
            playPauseDrawable.setPlay(animate);
        } else {
            playPauseDrawable.setPause(animate);
        }
    }

    @Override
    public void onUpdateProgressViews(int progress, int total) {
        progressSlider.setMax(total);
        progressSlider.setProgress(progress);
        songTotalTime.setText("/" + MusicUtil.getReadableDurationString(total));
        songCurrentProgress.setText(MusicUtil.getReadableDurationString(progress));
    }

}
