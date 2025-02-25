package com.rotlug.glebadroid;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/*
CollisionRect is a Node that can check for collisions with other
CollisionRects
 */
public class CollisionRect extends Node2D {
    private CollisionPool colPool;
    private Vector2 size;

    // Number of collisions in this frame
    int numCollisions;

    // List of all current colliding objects
    ArrayList<CollisionRect> collidingObjects;

    @Override
    public void onReady() {
        super.onReady();

        colPool = CollisionPool.getInstance();
        numCollisions = 0;
        collidingObjects = new ArrayList<>();

        setSize(0, 0);
        colPool.getCollisionRects().add(this);
    }

    @Override
    public void update(Canvas canvas, MotionEvent motionEvent) {
        super.update(canvas, motionEvent);
        checkCollisions();
    }

    public void checkCollisions() {
        // Check for collisions with every collision object in the pool
        numCollisions = 0;
        collidingObjects.clear();

        for (int i = 0; i < colPool.getCollisionRects().size(); i++) {
            CollisionRect collisionRect = colPool.getCollisionRects().get(i);
            if (collisionRect == this) continue;
            if (isCollidingWith(collisionRect)) {
                numCollisions++;
                collidingObjects.add(collisionRect);
                emit("collision", collisionRect.getParent());
            }
        }
    }

    public boolean isCollidingWith(CollisionRect other) {
        if (this.getSize() == null) return false;
        if (other.getGlobalPosition() == null) return false;
        
        if (this.getGlobalPosition().x + this.getSize().x <= other.getGlobalPosition().x ||
                other.getGlobalPosition().x + other.getSize().x <= this.getGlobalPosition().x) {
            return false;
        }

        // Check if one rectangle is above or below the other
        return !(this.getGlobalPosition().y >= other.getGlobalPosition().y + other.getSize().y) &&
                !(other.getGlobalPosition().y >= this.getGlobalPosition().y + this.getSize().y);
    }

    public int getNumCollisions() {
        return numCollisions;
    }

    @Override
    public void onDestroy() {
        // Remove myself from the collision pool when destroyed.
        super.onDestroy();
        colPool.getCollisionRects().remove(this);
    }

    public List<CollisionRect> getCollidingObjects() {
        return Collections.unmodifiableList(collidingObjects);
    }

    // Size
    public void setSize(Vector2 size) {
        this.size = size;
    }

    public void setSize(float x, float y) {
        this.size = new Vector2(x, y);
    }

    public Vector2 getSize() {
        return size;
    }
}

/*
CollisionPool is a Singleton (only one instance of this class can exist)
that keeps track of all the collision objects in the tree.
 */
class CollisionPool {
    private static CollisionPool singleInstance = null;

    // Declaring a variable of type String
    private final ArrayList<CollisionRect> collisionRects;

    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private CollisionPool()
    {
        this.collisionRects = new ArrayList<>();
    }

    // Static method
    // Static method to create instance of CollisionPool class
    public static synchronized CollisionPool getInstance()
    {
        if (singleInstance == null)
            singleInstance = new CollisionPool();

        return singleInstance;
    }

    public ArrayList<CollisionRect> getCollisionRects() {
        return collisionRects;
    }
}