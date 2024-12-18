package com.rotlug.glebadroid;

import androidx.annotation.NonNull;

public class Vector2 {
    public float x, y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("(%s, %s)", this.x, this.y);
    }
}
