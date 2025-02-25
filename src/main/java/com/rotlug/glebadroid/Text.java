package com.rotlug.glebadroid;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/*
Text is a node that draws text on the canvas
 */
public class Text extends Bitmap2D {
    private String text;
    private int color;
    private int textSize;

    public Text(String text, int color, int textSize) {
        this.color = color;
        this.text = text;
        this.textSize = textSize;
    }

    @Override
    public void onReady() {
        setBitmap(createBitmapFromText());
        super.onReady();
    }

    private Bitmap createBitmapFromText() {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(textSize);

        // Measure text size
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        int textHeight = (int) (Math.abs(fontMetrics.top) + Math.abs(fontMetrics.bottom));
        int textWidth = (int) paint.measureText(text);

        // Create a bitmap with sufficient size
        Bitmap bitmap = Bitmap.createBitmap(textWidth, textHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // Draw the text onto the canvas
        canvas.drawText(text, 0, Math.abs(fontMetrics.top), paint);

        return bitmap;
    }

    // Setters
    public void setColor(int color) {
        this.color = color;
        setBitmap(createBitmapFromText());
    }

    public void setText(String text) {
        this.text = text;
        setBitmap(createBitmapFromText());
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        setBitmap(createBitmapFromText());
    }

    // Getters
    public int getColor() {
        return color;
    }

    public String getText() {
        return text;
    }

    public int getTextSize() {
        return textSize;
    }
}
