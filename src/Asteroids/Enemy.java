package Asteroids;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by user on 14.04.17.
 */
public class Enemy {
    //variables
    private GameObject enemy;

    //constructor
    public Enemy(){
        double x = Math.random()*15+5;
        this.enemy = new GameObject(new Circle(x,x,x, Color.RED));
    }

    //methods
    public boolean isDead(){
        return !getGameObject().isAlive();
    }

    /*public void update(){
        this.enemy.getView().setTranslateX(this.enemy.getView().getTranslateX() + this.enemy.getVelocity().getX());
        this.enemy.getView().setTranslateY(this.enemy.getView().getTranslateY() + this.enemy.getVelocity().getY());
    }*/

    //getters
    public GameObject getGameObject() {
        return this.enemy;
    }
}
