package com.rotlug.glebadroid;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import java.util.HashMap;

public class SoundManager {
    private static HashMap<Integer, Integer> soundMap = new HashMap<>();

    private static SoundPool soundPool;

    public static void init() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(10)
                .setAudioAttributes(audioAttributes)
                .build();
    }

    public static void playSound(Context context, int resId, float volume, int loop, float pitch) {
        if (soundMap.get(resId) == null) {
            int id = soundPool.load(context, resId, 1);
            soundMap.put(resId, id);
            soundPool.setOnLoadCompleteListener((soundPool1, sampleId, status) -> {
                soundPool.play(soundMap.get(resId), volume, volume, 1, loop, 1f);
            });
        }
        else {
            soundPool.play(soundMap.get(resId), volume, volume, 1, loop, 1f);
        }
    }

    public static void playSound(Context context, int resId) {
        playSound(context, resId, 1, 0, 1);
    }

    public static void playSound(Context context, int resId, float volume) {
        playSound(context, resId, volume, 0, 1);
    }

    public static void playSound(Context context, int resId, float volume, int loop) {
        playSound(context, resId, volume, loop, 1);
    }



    public static void release() {
        if (soundPool != null) {
            soundPool.release();
            soundMap.clear();
            soundPool = null;
        }
    }
}