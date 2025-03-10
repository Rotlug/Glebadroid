package com.rotlug.glebadroid;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
CollisionRect is a Node that can check for collisions with other
CollisionRects
 */
public class CollisionRect extends Node {
    SizedNode2D parent2D;
    CollisionPool colPool;

    // Number of collisions in this frame
    int numCollisions;

    // List of all current colliding objects
    ArrayList<SizedNode2D> collidingObjects;

    @Override
    public void onReady() {
        super.onReady();
        if (!(getParent() instanceof SizedNode2D)) {
            throw new RuntimeException("Error: Parent of collisionRect is not a SizedNode2D");
        }

        parent2D = (SizedNode2D) getParent();
        colPool = CollisionPool.getInstance();
        numCollisions = 0;
        collidingObjects = new ArrayList<>();

        colPool.getCollisionRects().add(this);
        connect("collision", parent2D);
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
                collidingObjects.add((SizedNode2D) collisionRect.getParent());
                emit("collision", collisionRect.getParent());
            }
        }
    }

    public boolean isCollidingWith(CollisionRect other) {
        if (other.getPosition() == null) return false;
        if (this.getPosition().x + this.getSize().x <= other.getPosition().x ||
                other.getPosition().x + other.getSize().x <= this.getPosition().x) {
            return false;
        }

        // Check if one rectangle is above or below the other
        if (this.getPosition().y >= other.getPosition().y + other.getSize().y ||
                other.getPosition().y >= this.getPosition().y + this.getSize().y) {
            return false;
        }

        return true;
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

    // Getters for position & size of the parent
    public Vector2 getPosition() {
        return parent2D.getGlobalPosition();
    }

    public Vector2 getSize() {
        return parent2D.getSize();
    }

    public List<SizedNode2D> getCollidingObjects() {
        return Collections.unmodifiableList(collidingObjects);
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