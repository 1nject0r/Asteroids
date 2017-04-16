package Asteroids;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by user on 14.04.17.
 */
public class Bullet {
    //variables
    private GameObject bullet;

    //constructor
    public Bullet(){
        bullet = new GameObject(new Circle(5,5,5, Color.GREEN));
    }

    //methods
    public void update(){
        this.bullet.getView().setTranslateX(this.bullet.getView().getTranslateX() + this.bullet.getVelocity().getX());
        this.bullet.getView().setTranslateY(this.bullet.getView().getTranslateY() + this.bullet.getVelocity().getY());
    }

    public boolean isDead(){
        return !getGameObject().isAlive();
    }

    //getters
    public GameObject getGameObject() {
        return this.bullet;
    }
}
