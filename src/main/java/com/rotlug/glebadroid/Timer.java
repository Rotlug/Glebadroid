package com.rotlug.glebadroid;

import android.graphics.Canvas;

public class Timer extends Node {
    private int timeLeft;
    private boolean isActive;

    public void startTimer(double seconds) {
        timeLeft = (int) (seconds * getGameView().getFrameRate());
        isActive = true;
    }

    @Override
    public void update(Canvas canvas) {
        if (isActive) {
            if (timeLeft == 0) {
                isActive = false;
                emit("timeout");
            }
            else {
                timeLeft -= 1;
            }
        }
        super.update(canvas);
    }

    public float getSecondsLeft() {
        return (float) timeLeft / 60;
    }

    public boolean isActive() {
        return isActive;
    }
}
