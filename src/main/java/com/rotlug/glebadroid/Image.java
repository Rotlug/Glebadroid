package com.rotlug.glebadroid;

import android.content.Context;
import android.graphics.BitmapFactory;

/*
Image is a node that draws an image on the canvas, given a resourceId and context.
 */
public class Image extends Bitmap2D {
    Context context;
    int resourceId;

    public Image(int resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public void onReady() {
        this.context = getContext();
        setImage(resourceId);
        super.onReady();
    }

    public void setImage(int id) {
        resourceId = id;
        setBitmap(BitmapFactory.decodeResource(context.getResources(), resourceId));
    }
}
