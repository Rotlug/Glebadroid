package com.rotlug.glebadroid;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

/*
`Node` is the base class for all other nodes
 */
public class Node {
    private final ArrayList<Node> children;
    private GameView gameView;
    private Node parent;

    Dictionary<String, ArrayList<Node>> signals;

    public Node() {
        this.children = new ArrayList<>();
        this.signals = new Hashtable<>();
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

    public Node addChild(Node child) {
        child.setParent(this);
        children.add(child);
        child.setGameView(gameView);
        child.onReady();

        return child;
    }

    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    // Parent Methods
    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    private ArrayList<Node> getChildren() {
        return children;
    }

    // Signals
    public void connect(String signalName, Node dest) {
        if (signals.get(signalName) == null) {
            signals.put(signalName, new ArrayList<>());
        }

        signals.get(signalName).add(dest);
    }

    public void emit(String signalName, Object params) {
        if (signals.get(signalName) == null) return;

        ArrayList<Node> nodeList = signals.get(signalName);
        for (int i = 0; i < nodeList.size(); i++) {
            Node node = nodeList.get(i);
            node.onSignal(signalName, this, params);
        }
    }

    public void onSignal(String signalName, Node signalSource, Object params) {}

    // Destroy node
    public void destroySelf() {
        this.onDestroy();
        getParent().getChildren().remove(this);
    }

    public void onDestroy() {}
}
