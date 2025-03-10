package com.rotlug.glebadroid;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Bitmap2D extends SizedNode2D {
    private int alpha;
    private float rotation;

    private Bitmap bitmap;

    @Override
    public void onReady() {
        super.onReady();
        this.rotation = 0;
        this.alpha = 255;
    }

    // Bitmap
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        setSize(bitmap.getWidth(), bitmap.getHeight()); // Update the size property
    }

    // Bitmap Size
    @Override
    public void setSize(Vector2 size) {
        super.setSize(size);
        bitmap = Bitmap.createScaledBitmap(bitmap, (int)size.x, (int)size.y, true);
    }

    @Override
    public void update(Canvas canvas, MotionEvent motionEvent) {
        super.update(canvas, motionEvent);
        if (bitmap == null) System.out.printf("Warning: %s doesn't have bitmap%n", getName());
        else draw(canvas);
    }

    // Rotation
    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        if (rotation < 0) {
            throw new RuntimeException("Error: rotation can't be set below 0");
        }
        this.rotation = (rotation % 360);
    }

    // Draw
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

    private void drawWithOpacity(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAlpha(alpha);
        canvas.drawBitmap(bitmap, globalPosition.x, globalPosition.y, paint);
    }

    // Alpha
    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        if (alpha < 0) {
            alpha = 0;
        }
        this.alpha = (alpha % 256);
    }
}
