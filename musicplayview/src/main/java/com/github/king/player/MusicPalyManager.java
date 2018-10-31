package com.github.king.player;

import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;
import com.github.king.player.playback.Playback;

import java.io.File;

/**
 * @author Created by jinxl on 2018/10/17.
 */
public class MusicPalyManager implements Playback.PlaybackCallbacks {

    private static MusicPalyManager instance;

    private HttpProxyCacheServer mProxyCacheServer;

    private MultiPlayer playback;

    String VOICE_PATH = "/sdcard/MyVoiceForder/Record/workoder/voice";

    private MusicPalyManager(Context context) {
        playback = new MultiPlayer(context);
        playback.setCallbacks(this);
        mProxyCacheServer = new HttpProxyCacheServer.Builder(context.getApplicationContext())
                .cacheDirectory(new File(VOICE_PATH))
                .maxCacheSize(1024 * 1024 * 1024)       // 1 Gb for cache
                .build();
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

    public HttpProxyCacheServer getProxyCacheServer() {
        return mProxyCacheServer;
    }
}
