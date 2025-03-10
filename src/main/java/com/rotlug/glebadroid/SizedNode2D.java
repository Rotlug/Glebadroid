package com.rotlug.glebadroid;

public class SizedNode2D extends Node2D {
    private Vector2 size;

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public void setSize(float x, float y) {
        setSize(new Vector2(x, y));
    }

    public Vector2 getSize() {
        return size;
    }
}
