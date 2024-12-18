package com.rotlug.glebadroid;

import android.graphics.Canvas;

import java.util.ArrayList;

public class Node {
    ArrayList<Node> children;
    GameView gameView;

    public Node() {
        this.children = new ArrayList<>();
    }

    public void onReady() {
        for (int i = 0; i < children.size(); i++) {
            Node child = children.get(i);
            child.onReady();
        }
    }

    public void update(Canvas canvas) {
        if (canvas == null) return;

        // Update all children when updated.
        for (int i = 0; i < children.size(); i++) {
            Node child = children.get(i);
            child.update(canvas);
        }
    }

    public void addChild(Node child) {
        children.add(child);
        child.setGameView(gameView);
        child.onReady();
    }

    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }
}
