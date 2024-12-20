package com.rotlug.glebadroid;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/*
ColorRect is a node that can draw a colored rectangle on the canvas (Mainly used for debugging)
 */
public class ColorRect extends Node2D {
    private int color;

    public ColorRect(int color) {
        this.color = color;
    }

    @Override
    public void onReady() {
        super.onReady();
        setBitmap(createBitmap());
    }

    private Bitmap createBitmap() {
        // Here you create the bound of your shape
        Rect rect = new Rect(0, 0, 1, 1);

        // You then create a Bitmap and get a canvas to draw into it
        Bitmap image = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);

        //Paint holds information about how to draw shapes
        Paint paint = new Paint();
        paint.setColor(color);

        // Then draw your shape
        canvas.drawRect(rect, paint);

        return image;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        setBitmap(createBitmap());
    }
}
