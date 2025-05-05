package com.rotlug.glebadroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

@SuppressLint("ViewConstructor")
public class GameView extends View {
    private final Node rootNode;
    private final float density;
    private final Vector2 screenSize;
    private final int delayMs;
    private final int frameRate;

    private MotionEvent motionEvent;
    private final BackgroundColor bgRect;

    public GameView(Context context, int frameRate, int backgroundColor) {
        super(context);
        rootNode = new Node();
        rootNode.setGameView(this);
        screenSize = new Vector2(Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels);

        this.delayMs = 1000 / frameRate;
        this.frameRate = frameRate;

        this.density = context.getResources().getDisplayMetrics().density;

        // A Choreographer object is needed to make sure the game runs at the specified frame rate.
        Choreographer choreographer = Choreographer.getInstance();
        getFrameCallback(choreographer);

        // Add Background Color
        bgRect = (BackgroundColor) rootNode.addChild(new BackgroundColor(backgroundColor));
    }

    private void getFrameCallback(Choreographer choreographer) {
        Choreographer.FrameCallback frameCallback = new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                // Perform the update
                invalidate();

                // Schedule the next frame
                choreographer.postFrameCallbackDelayed(this, delayMs);
            }
        };

        // Start the updates
        choreographer.postFrameCallback(frameCallback);
    }

    // Draw Cycle (Update)
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        rootNode.update(canvas, motionEvent);
    }

    // Touch events
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.motionEvent = event;
        return super.onTouchEvent(event);
    }

    public MotionEvent getMotionEvent() {
        return motionEvent;
    }

    // Other Getters
    public Node getRootNode() {
        return rootNode;
    }

    public int getFrameRate() {
        return frameRate;
    }

    public Vector2 getScreenSize() {
        return screenSize;
    }

    public float getDensity() {
        return density;
    }

    // Background Color

    public void setBackgroundColor(int color) {
        bgRect.setColor(color);
    }

    public int getBackgroundColor() {
        return bgRect.getColor();
    }
}

class BackgroundColor extends ColorRect {
    public BackgroundColor(int color) {
        super(color);
    }

    @Override
    public void onReady() {
        super.onReady();
        setSize(getGameView().getScreenSize());
    }
}