package com.rotlug.glebadroid;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;

/*
`Node` is the base class for all other nodes
 */
public class Node {
    private final ArrayList<Node> children;
    private GameView gameView;
    private Node parent;
    private String name;

    private HashSet<String> tags;

    Dictionary<String, ArrayList<Node>> signals;

    public Node() {
        this.children = new ArrayList<>();
        this.signals = new Hashtable<>();
        this.name = getClass().getSimpleName();

        this.tags = new HashSet<>();
    }

    public void onReady() {
        for (int i = 0; i < children.size(); i++) {
            Node child = children.get(i);
            child.onReady();
        }
    }

    public void update(Canvas canvas, MotionEvent motionEvent) {
        if (canvas == null) return;

        // Update all children when updated.
        for (int i = 0; i < children.size(); i++) {
            Node child = children.get(i);
            child.update(canvas, motionEvent);
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
        // Destroy all children
        for (int i = 0; i < children.size(); i++) {
            Node child = children.get(i);
            child.destroySelf();
        }
        getParent().getChildren().remove(this);
    }

    public void onDestroy() {}

    // Name
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Get Node by path (example: ~/UI/MoveRightButton or ../../Player)
    public Node find(String path) {
        String[] nodeNames = path.split("/");
        Node result = this;

        for (int i = 0; i < nodeNames.length; i++) {
            String name = nodeNames[i];
            switch (name) {
                case "~":
                    result = getGameView().getRootNode();
                    break;
                case "..":
                    result = result.getParent();
                    break;
                default:
                    boolean found = false;
                    for (int j = 0; j < result.getChildren().size(); j++) {
                        Node child = result.getChildren().get(j);
                        if (child.getName().equals(name)) {
                            result = child;
                            found = true;
                            break;
                        };
                    }
                    if (!found) {
                        return null;
                    }
                    break;
            }
        }
        return result;
    }

    // Debug
    private void printTree(String indent) {
        String newIndent = "---" + indent;
        // print self
        System.out.printf("%s%s%n", indent, name);
        for (int i = 0; i < children.size(); i++) {
            Node child = children.get(i);
            child.printTree(newIndent);
        }
    }

    public void printTree() { printTree(""); }

    // Tags
    public HashSet<String> getTags() {
        return tags;
    }

    // Context
    public Context getContext() {
        return getGameView().getContext();
    }
}
