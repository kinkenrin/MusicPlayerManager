package com.github.king.player;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

/**
 * @author Karim Abou Zeid (kabouzeid)
 */
public class MusicProgressViewUpdateHelper extends Handler {
    private static final int CMD_REFRESH_PROGRESS_VIEWS = 1;

    private static final int MIN_INTERVAL = 20;
    private static final int UPDATE_INTERVAL_PLAYING = 1000;
    private static final int UPDATE_INTERVAL_PAUSED = 500;

    private Context mContext;
    private Callback callback;
    private int intervalPlaying;
    private int intervalPaused;

    public void start() {
        queueNextRefresh(1);
    }

    public void stop() {
        removeMessages(CMD_REFRESH_PROGRESS_VIEWS);
    }

    public MusicProgressViewUpdateHelper(Context context, Callback callback) {
        this.mContext = context;
        this.callback = callback;
        this.intervalPlaying = UPDATE_INTERVAL_PLAYING;
        this.intervalPaused = UPDATE_INTERVAL_PAUSED;
    }

    public MusicProgressViewUpdateHelper(Callback callback, int intervalPlaying, int intervalPaused) {
        this.callback = callback;
        this.intervalPlaying = intervalPlaying;
        this.intervalPaused = intervalPaused;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        if (msg.what == CMD_REFRESH_PROGRESS_VIEWS) {
            int interval = refreshProgressViews();
            if (interval != -1) {
                queueNextRefresh(interval);
            }
        }
    }

    private int refreshProgressViews() {
        final int progressMillis = MusicPalyManager.getInstance(mContext).getSongProgressMillis();
        final int totalMillis = MusicPalyManager.getInstance(mContext).getSongDurationMillis();

        callback.onUpdateProgressViews(progressMillis, totalMillis);

        if (!MusicPalyManager.getInstance(mContext).isPlaying()) {
//            return intervalPaused;
            return -1;
        }

        final int remainingMillis = intervalPlaying - progressMillis % intervalPlaying;

        return Math.max(MIN_INTERVAL, remainingMillis);
    }

    private void queueNextRefresh(final long delay) {
        final Message message = obtainMessage(CMD_REFRESH_PROGRESS_VIEWS);
        removeMessages(CMD_REFRESH_PROGRESS_VIEWS);
        sendMessageDelayed(message, delay);
    }

    public interface Callback {
        void onUpdateProgressViews(int progress, int total);
    }
}
