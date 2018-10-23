package com.github.king.player;

import android.content.Context;

import com.github.king.player.playback.Playback;

/**
 * @author Created by jinxl on 2018/10/17.
 */
public class MusicPalyManager implements Playback.PlaybackCallbacks {

    private static MusicPalyManager instance;
    private MultiPlayer playback;

    private MusicPalyManager(Context context) {
        playback = new MultiPlayer(context);
        playback.setCallbacks(this);
    }

    public static MusicPalyManager getInstance(Context context) {
        if (instance == null) {
            synchronized (MusicPalyManager.class) {
                if (instance == null) {
                    instance = new MusicPalyManager(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public boolean setDataSource(String path) {
        synchronized (this) {
            try {
                return playback.setDataSource(path);
            } catch (Exception e) {
                return false;
            }
        }
    }

    public void play() {
        synchronized (this) {
            if (!playback.isPlaying()) {
                playback.start();
            }
        }
    }

    public void pause() {
        if (playback.isPlaying()) {
            playback.pause();
        }
    }

    public int seek(int millis) {
        synchronized (this) {
            try {
                return playback.seek(millis);
            } catch (Exception e) {
                return -1;
            }
        }
    }

    public int getSongProgressMillis() {
        return playback.position();
    }

    public int getSongDurationMillis() {
        return playback.duration();
    }

    public boolean isPlaying() {
        return playback != null && playback.isPlaying();
    }

    private void releaseResources() {
        pause();
        playback.release();
        playback = null;
    }

    @Override
    public void onTrackWentToNext() {
    }

    @Override
    public void onTrackEnded() {
    }
}
