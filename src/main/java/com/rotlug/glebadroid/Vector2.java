package com.rotlug.glebadroid;

import androidx.annotation.NonNull;

public class Vector2 {
    public float x, y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Returns the length (magnitude) of the vector
    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    // Returns a new vector pointing in the same direction but with length 1
    public Vector2 normalized() {
        float len = length();
        if (len == 0) return new Vector2(0, 0);
        return new Vector2(x / len, y / len);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("(%s, %s)", this.x, this.y);
    }
}

