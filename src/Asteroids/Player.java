package Asteroids;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

/**
 * Created by user on 14.04.17.
 */
public class Player {
    //variables
    private GameObject player;
    private Node sensor;
    private Rotate rotate;

    //constructor
    public Player(){
        player = new GameObject(new Circle(15,15,15, Color.BLUE));
        sensor = new Rectangle(1,Main.getRoot().getPrefWidth(),Color.BLACK);
        player.setHealth(1000);
        player.setVelocity(new Point2D(1,0));
        this.rotate = new Rotate(-90);
        sensor.getTransforms().add(rotate);
    }

    //methods
    public void update(){
        player.getView().setTranslateX(player.getView().getTranslateX() + player.getVelocity().getX());
        player.getView().setTranslateY(player.getView().getTranslateY() + player.getVelocity().getY());
        sensor.setTranslateX(player.getView().getTranslateX()+15);
        sensor.setTranslateY(player.getView().getTranslateY()+15);
        rotate.setAngle(player.getView().getRotate()-90);
    }

    public void rotateRight(){
        player.getView().setRotate(player.getView().getRotate() + 5);
        player.setVelocity(new Point2D(Math.cos(Math.toRadians(player.getView().getRotate())),
                Math.sin(Math.toRadians(player.getView().getRotate()))));
    }

    public void rotateLeft(){
        player.getView().setRotate(player.getView().getRotate() - 5);
        player.setVelocity(new Point2D(Math.cos(Math.toRadians(player.getView().getRotate())),
                Math.sin(Math.toRadians(player.getView().getRotate()))));
    }

    public boolean isDead(){
        return !getGameObject().isAlive();
    }

    //getters
    public GameObject getGameObject() {
        return player;
    }

    public Node getSensor() {
        return sensor;
    }

    public Rotate getRotate() {
        return rotate;
    }

    //setters
    public void setRotate(Rotate rotate) {
        this.rotate = rotate;
    }
}
