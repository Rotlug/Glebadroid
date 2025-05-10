package com.rotlug.glebadroid;

import android.graphics.Canvas;
import android.view.MotionEvent;

/*
Node2D is a node that draws a bitmap on the canvas.
it also supports rotating the bitmap.
 */
public class Node2D extends Node {
    public Vector2 position;
    public Vector2 offset = new Vector2(0, 0);
    public Vector2 globalOffset = new Vector2(0, 0);

    Vector2 globalPosition;

    @Override
    public void onReady() {
        this.position = new Vector2(0, 0);
        super.onReady();
    }

    @Override
    public void update(Canvas canvas, MotionEvent motionEvent) {
        this.globalPosition = findGlobalPosition();
        this.globalOffset = findGlobalOffset();
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

    private Vector2 findGlobalOffset() {
        Vector2 pos = new Vector2(offset.x, offset.y);
        Node2D node = this;

        while (node.getParent() instanceof Node2D) {
            node = (Node2D) node.getParent();
            pos.x += node.offset.x;
            pos.y += node.offset.y;
        }

        return pos;
    }

    public Vector2 getGlobalPosition() {
        return globalPosition;
    }

    public Vector2 getGlobalPositionWithDensity() {
        if (globalPosition == null) return null;
        return new Vector2(globalPosition.x * getGameView().getDensity(), globalPosition.y * getGameView().getDensity());
    }

    public Vector2 getGlobalOffsetWithDensity() {
        if (offset == null) return null;
        return new Vector2(globalOffset.x * getGameView().getDensity(), globalOffset.y * getGameView().getDensity());
    }
}