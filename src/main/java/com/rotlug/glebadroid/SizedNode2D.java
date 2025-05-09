package com.rotlug.glebadroid;

public class SizedNode2D extends Node2D {
    Vector2 size;

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public void setSize(float x, float y) {
        setSize(new Vector2(x, y));
    }

    public Vector2 getSize() {
        return size;
    }

    public Vector2 getSizeWithDensity() {
        return new Vector2(size.x / getGameView().getDensity(), size.y / getGameView().getDensity());
    }

    public void debugPrint() {
        System.out.println("MY NAME IS: " + getName());
        System.out.println("MY SIZE IS: " + getSize());
        System.out.println("MY POSITION IS: " + position);
    }
}
