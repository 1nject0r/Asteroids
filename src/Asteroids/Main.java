package Asteroids;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 14.04.17.
 */
public class Main extends Application{
    //variables
    private static Pane root;
    private double prefWidth;
    private double prefHeight;

    private List<Enemy> enemies;
    private List<Bullet> bullets;

    private Player player;
    private Label status;

    //constructor
    public Main(){
        this.prefWidth = 900;
        this.prefHeight = 800;
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
    }

    private AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            gameLoop();
        }
    };

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(initialise()));
        keyEvents(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    //methods
    private Parent initialise(){
        root = new Pane();
        root.setPrefSize(prefWidth,prefHeight);

        player = new Player();
        status = new Label();

        player.getGameObject().setHealth(1000);
        status.setTranslateX(prefWidth - 100);
        status.setTranslateY(prefHeight -75);

        enemies.clear();
        bullets.clear();

        addPlayer(player,300,300);
        root.getChildren().add(status);

        timer.start();

        return root;
    }

    public void keyEvents(Stage stage){
        stage.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                player.rotateLeft();
                player.getRotate().setAngle(player.getRotate().getAngle() - 5);
            } else if (e.getCode() == KeyCode.RIGHT) {
                player.rotateRight();
                player.getRotate().setAngle(player.getRotate().getAngle() + 5);
            } else if (e.getCode() == KeyCode.SPACE) {
                Bullet bullet = new Bullet();
                bullet.getGameObject().setVelocity(player.getGameObject().getVelocity().normalize().multiply(5));
                addBullet(bullet, player.getGameObject().getView().getTranslateX()+10, player.getGameObject().getView().getTranslateY()+10);
            }else if (e.getCode() == KeyCode.R) {
                stage.setScene(new Scene(initialise()));
                keyEvents(stage);
            }
        });
    }

    private void gameLoop(){
        for (Enemy enemy : enemies) {
            if (player.getGameObject().isColliding(enemy.getGameObject())) player.getGameObject().setHealth(player.getGameObject().getHealth() - 10);
            if (enemy.isDead()) root.getChildren().remove(enemy.getGameObject().getView());
            for (Bullet bullet : bullets){
                if (bullet.getGameObject().isColliding(enemy.getGameObject())){
                    bullet.getGameObject().setHealth(0);
                    enemy.getGameObject().setHealth(0);

                    root.getChildren().removeAll(bullet.getGameObject().getView(), enemy.getGameObject().getView());
                }
                if (bullet.getGameObject().touchesBorder()){
                    bullet.getGameObject().setHealth(0);
                    root.getChildren().remove(bullet.getGameObject().getView());
                }
            }
        }

        bullets.removeIf(Bullet::isDead);
        enemies.removeIf(Enemy::isDead);

        bullets.forEach(Bullet::update);


        //sparwn enemies
        if (Math.random() < 0.015) {
            addEnemy(new Enemy(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
            if (player.getGameObject().isColliding(enemies.get(enemies.size()-1).getGameObject()) || enemies.get(enemies.size()-1).getGameObject().touchesBorder()) {
                enemies.get(enemies.size()-1).getGameObject().setHealth(0);
            }
        }

        //handle window border collision
        if (player.getGameObject().touchesBorder()){
            player.getGameObject().setVelocity(new Point2D(-player.getGameObject().getVelocity().getX(),-player.getGameObject().getVelocity().getY()));
            player.getGameObject().getView().setRotate(player.getGameObject().getView().getRotate()-180);
            player.getGameObject().setHealth(player.getGameObject().getHealth()-5);
        }
        player.update();

        //restart on death
        if (!player.getGameObject().isAlive()){
            try {
                Robot r = new Robot();
                r.keyPress(KeyEvent.VK_R);
                r.keyRelease(KeyEvent.VK_R);
            }catch (AWTException e){
                e.printStackTrace();
            }
        }

        //status
        status.setText("Health:" + player.getGameObject().getHealth() + "\nAsteroids: " + enemies.size());
    }

    private void addPlayer(Player player, double x, double y){
        addGameObject(player.getGameObject(),x,y);
        player.getSensor().setTranslateX(x);
        player.getSensor().setTranslateY(y);
        root.getChildren().add(player.getSensor());
    }

    private void addEnemy(Enemy enemy, double x, double y){
        enemies.add(enemy);
        addGameObject(enemy.getGameObject(),x,y);
    }

    private void addBullet(Bullet bullet, double x, double y){
        bullets.add(bullet);
        addGameObject(bullet.getGameObject(),x,y);
    }

    private void addGameObject(GameObject object, double x, double y) {
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView());
    }

    //getters
    public static Pane getRoot() {
        return root;
    }
}