package com.rotlug.glebadroid;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicManager {
    private static MediaPlayer mediaPlayer;

    public static void play(Context context, int resId, float volume, boolean loop) {
        stop(); // Stop any existing playback
        mediaPlayer = MediaPlayer.create(context, resId);
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(loop); // Enable seamless looping
            mediaPlayer.setVolume(volume, volume);
            mediaPlayer.start();
        }
    }

    public static void play(Context context, int resId, float volume) {
        play(context, resId, volume, true);
    }

    public static void play(Context context, int resId) {
        play(context, resId, 1);
    }

    public static void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
