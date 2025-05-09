package com.rotlug.glebadroid;

import android.graphics.Canvas;
import android.view.MotionEvent;

public class Timer extends Node {
    private int timeLeft;
    private boolean isActive = false;

    public void startTimer(double seconds) {
        timeLeft = (int) (seconds * getGameView().getFrameRate());
        isActive = true;
    }

    @Override
    public void update(Canvas canvas, MotionEvent motionEvent) {
        if (isActive) {
            if (timeLeft == 0) {
                isActive = false;
                emit("timeout", null);
            }
            else {
                timeLeft -= 1;
            }
        }
        super.update(canvas, motionEvent);
    }

    public float getSecondsLeft() {
        return (float) timeLeft / 60;
    }

    public boolean isActive() {
        return isActive;
    }
}
