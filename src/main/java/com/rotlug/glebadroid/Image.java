package com.rotlug.glebadroid;

import android.content.Context;
import android.graphics.BitmapFactory;

/*
Image is a node that draws an image on the canvas, given a resourceId and context.
 */
public class Image extends Bitmap2D {
    Context context;
    int resourceId;

    public Image(Context context, int resourceId) {
        this.context = context;
        this.resourceId = resourceId;
    }

    @Override
    public void onReady() {
        setBitmap(BitmapFactory.decodeResource(context.getResources(), resourceId));
        super.onReady();
    }
}
