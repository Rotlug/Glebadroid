package com.rotlug.glebadroid;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import java.util.HashMap;
import java.util.Random;

public class SoundManager {
    private static HashMap<Integer, Integer> soundMap = new HashMap<>();
    private static final Random random = new Random();

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
                soundPool.play(soundMap.get(resId), volume, volume, 1, loop, pitch);
            });
        }
        else {
            soundPool.play(soundMap.get(resId), volume, volume, 1, loop, pitch);
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

    // Random Pitch
    public static void playSoundWithRandomPitch(Context context, int resId, float volume, int loop) {
        float pitch = 0.9f + random.nextFloat() * 0.2f; // Random pitch between 0.9 and 1.1
        playSound(context, resId, volume, loop, pitch);
    }

    public static void playSoundWithRandomPitch(Context context, int resId) {
        playSoundWithRandomPitch(context, resId, 1.0f, 0);
    }
}