package com.rotlug.glebadroid;

import java.util.ArrayList;

/*
CollisionPool is a Singleton (only one instance of this class can exist)
that keeps track of all the collision objects in the tree.
 */
public class CollisionPool {
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
