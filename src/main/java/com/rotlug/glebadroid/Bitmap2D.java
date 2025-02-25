package com.rotlug.glebadroid;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/*
Bitmap2D can draw a bitmap on the screen
 */
public class Bitmap2D extends Node2D {
    private int alpha;
    private float rotation;

    private Vector2 size;
    private Bitmap bitmap;

    @Override
    public void onReady() {
        super.onReady();
        this.rotation = 0;
        this.alpha = 255;
    }

    // Draw the bitmap
    @Override
    public void update(Canvas canvas, MotionEvent motionEvent) {
        draw(canvas);
        super.update(canvas, motionEvent);
    }

    // Draw Function takes in a canvas and paints our bitmap on it
    public void draw(Canvas canvas) {
        Bitmap bmp = bitmap;

        /*
        Rotate the canvas, paint the picture, then restore the canvas to its original
        state. this is more efficient than rotating the actual bitmap.
         */
        canvas.save();
        canvas.rotate(rotation, getGlobalPosition().x, getGlobalPosition().y);
        if (alpha == 255) canvas.drawBitmap(bmp, getGlobalPosition().x, getGlobalPosition().y, null);
        else drawWithOpacity(canvas);
        canvas.restore();
    }

    // Opacity
    private void drawWithOpacity(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAlpha(alpha);
        canvas.drawBitmap(bitmap, getGlobalPosition().x, getGlobalPosition().y, paint);
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        if (alpha < 0) {
            alpha = 0;
        }
        this.alpha = (alpha % 256);
    }

    // Rotation
    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        if (rotation < 0) {
            rotation = 0;
        }
        this.rotation = (rotation % 360);
    }

    // Size
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

    // Bitmap
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        setSize(bitmap.getWidth(), bitmap.getHeight()); // Update the size property
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
