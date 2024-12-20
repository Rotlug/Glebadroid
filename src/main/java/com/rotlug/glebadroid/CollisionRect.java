package com.rotlug.glebadroid;

import android.graphics.Canvas;

import java.util.ArrayList;

/*
CollisionRect is a Node that can check for collisions with other
CollisionRects
 */
public class CollisionRect extends Node {
    Node2D parent2D;
    CollisionPool colPool;

    // Number of collisions in this frame
    int numCollisions;

    @Override
    public void onReady() {
        super.onReady();
        if (!(getParent() instanceof Node2D)) {
            throw new RuntimeException("Error: Parent of collisionRect is not a Node2D");
        }

        parent2D = (Node2D) getParent();
        colPool = CollisionPool.getInstance();
        numCollisions = 0;

        colPool.getCollisionRects().add(this);
        connect("collision", parent2D);
    }

    @Override
    public void update(Canvas canvas) {
        super.update(canvas);
        // Check for collisions with every collision object in the pool
        numCollisions = 0;

        for (int i = 0; i < colPool.getCollisionRects().size(); i++) {
            CollisionRect collisionRect = colPool.getCollisionRects().get(i);
            if (collisionRect == this) continue;
            if (isCollidingWith(collisionRect)) {
                numCollisions++;
                emit("collision", collisionRect);
            }
        }
    }

    public boolean isCollidingWith(CollisionRect other) {
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
        return parent2D.position;
    }

    public Vector2 getSize() {
        return parent2D.getSize();
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