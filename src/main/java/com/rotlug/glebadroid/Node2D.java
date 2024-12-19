package com.rotlug.glebadroid;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

/*
Node2D is a node that draws a bitmap on the canvas.
it also supports rotating the bitmap.
 */
public class Node2D extends Node {
    public Vector2 location;

    private float rotation;
    private Vector2 size;
    private Bitmap bitmap;

    @Override
    public void onReady() {
        this.location = new Vector2(0, 0);
        this.rotation = 0;
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
        this.rotation = (rotation % 360);
    }

    // Draw the bitmap
    @Override
    public void update(Canvas canvas) {
        if (bitmap == null) {
            throw new IllegalArgumentException("Bitmap is null");
        }
        draw(canvas);
        super.update(canvas);
    }

    public void draw(Canvas canvas) {
        Bitmap bmp = bitmap;

        /*
        Rotate the canvas, paint the picture, then restore the canvas to its original
        state. this is more efficient than rotating the actual bitmap.
         */
        canvas.save();
        canvas.rotate(rotation, location.x, location.y);
        canvas.drawBitmap(bmp, location.x, location.y, null);
        canvas.restore();
    }
}