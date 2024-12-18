package com.rotlug.glebadroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.Choreographer;
import android.view.View;

import androidx.annotation.NonNull;

@SuppressLint("ViewConstructor")
public class GameView extends View {
    private final Node rootNode;
    private final Vector2 screenSize;
    private final int delayMs;
    private final int frameRate;

    public GameView(Context context, int frameRate) {
        super(context);
        rootNode = new Node();
        rootNode.setGameView(this);
        screenSize = new Vector2(Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels);

        this.delayMs = 1000 / frameRate;
        this.frameRate = frameRate;

        // A Choreographer object is needed to make sure the game runs at the specified frame rate.
        Choreographer choreographer = Choreographer.getInstance();
        getFrameCallback(choreographer);
    }

    private void getFrameCallback(Choreographer choreographer) {
        Choreographer.FrameCallback frameCallback = new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                // Perform the update
                invalidate();

                // Schedule the next frame
                choreographer.postFrameCallbackDelayed(this, delayMs); // Adjust delay for desired FPS
            }
        };

        // Start the updates
        choreographer.postFrameCallback(frameCallback);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        rootNode.update(canvas);
    }

    public Node getRootNode() {
        return rootNode;
    }

    public int getFrameRate() {
        return frameRate;
    }

    public Vector2 getScreenSize() {
        return screenSize;
    }
}
