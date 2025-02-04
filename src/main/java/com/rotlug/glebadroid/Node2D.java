package com.rotlug.glebadroid;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/*
Node2D is a node that draws a bitmap on the canvas.
it also supports rotating the bitmap.
 */
public class Node2D extends Node {
    public Vector2 position;
    private Vector2 globalPosition;

    private int alpha;
    private float rotation;

    private Vector2 size;
    private Bitmap bitmap;

    @Override
    public void onReady() {
        this.position = new Vector2(0, 0);
        this.rotation = 0;
        this.alpha = 255;

        super.onReady();
    }

    // Handle Replacing `bitmap`
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        setSize(bitmap.getWidth(), bitmap.getHeight()); // Update the size property
    }

    // Size getters and setters
    public void setSize(Vector2 size) {
        this.size = size;
        bitmap = Bitmap.createScaledBitmap(bitmap, (int)size.x, (int)size.y, true);
    }

    public void setSize(float x, float y) {
        setSize(new Vector2(x, y));
    }

    public Vector2 getSize() {
        return size;
    }

    // Rotation setters and getters
    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        if (rotation < 0) {
            throw new RuntimeException("Error: rotation can't be set below 0");
        }
        this.rotation = (rotation % 360);
    }

    // Draw the bitmap
    @Override
    public void update(Canvas canvas, MotionEvent motionEvent) {
        this.globalPosition = findGlobalPosition();
        if (bitmap != null) draw(canvas);
        super.update(canvas, motionEvent);
    }

    public void draw(Canvas canvas) {
        Bitmap bmp = bitmap;

        /*
        Rotate the canvas, paint the picture, then restore the canvas to its original
        state. this is more efficient than rotating the actual bitmap.
         */
        canvas.save();
        canvas.rotate(rotation, globalPosition.x, globalPosition.y);
        if (alpha == 255) canvas.drawBitmap(bmp, globalPosition.x, globalPosition.y, null);
        else drawWithOpacity(canvas);
        canvas.restore();
    }

    // Opacity
    private void drawWithOpacity(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAlpha(alpha);
        canvas.drawBitmap(bitmap, globalPosition.x, globalPosition.y, paint);
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        if (alpha < 0) {
            alpha = 0;
//            throw new RuntimeException("Error: Alpha can't be set below 0");
        }
        this.alpha = (alpha % 256);
    }

    private Vector2 findGlobalPosition() {
        Vector2 pos = new Vector2(position.x, position.y);
        Node2D node = this;

        while (node.getParent() instanceof Node2D) {
            node = (Node2D) node.getParent();
            pos.x += node.position.x;
            pos.y += node.position.y;
        }

        return pos;
    }

    public Vector2 getGlobalPosition() {
        return globalPosition;
    }
}