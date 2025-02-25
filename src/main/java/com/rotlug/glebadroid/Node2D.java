package com.rotlug.glebadroid;

import android.graphics.Canvas;
import android.view.MotionEvent;

public class Node2D extends Node {
    public Vector2 position;
    private Vector2 globalPosition;

    @Override
    public void onReady() {
        this.position = new Vector2(0, 0);
        this.globalPosition = findGlobalPosition();
        super.onReady();
    }

    // Draw the bitmap
    @Override
    public void update(Canvas canvas, MotionEvent motionEvent) {
        this.globalPosition = findGlobalPosition();
        super.update(canvas, motionEvent);
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