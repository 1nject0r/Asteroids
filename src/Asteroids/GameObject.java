package Asteroids;

import javafx.geometry.Point2D;
import javafx.scene.Node;

/**
 * Created by user on 14.04.17.
 */
public class GameObject {
    //variables
    private Node view;
    private Point2D velocity;
    private int health = 1;

    //constructor
    public GameObject(Node view){
        this.view = view;
    }

    //methods
    public boolean isAlive(){
        if (this.health > 0) return true;
        return false;
    }

    public boolean isColliding(GameObject other) {
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }

    public boolean touchesBorder() {
        boolean colision = false;
        if (!getView().getBoundsInParent().intersects(getView().getBoundsInParent().getWidth(),
                getView().getBoundsInParent().getHeight(),
                Main.getRoot().getPrefWidth()-getView().getBoundsInParent().getWidth()*2,
                Main.getRoot().getPrefHeight()-getView().getBoundsInParent().getHeight()*2)) {
            colision = true;
        }
        return colision;
    }

    //getters
    public Node getView() {
        return view;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public int getHealth() {
        return health;
    }

    //setters
    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
