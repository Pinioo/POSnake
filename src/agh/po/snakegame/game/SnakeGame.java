package agh.po.snakegame.game;

import agh.po.snakegame.interfaces.Game;
import agh.po.snakegame.interfaces.IGameObserver;
import agh.po.snakegame.maps.SnakeMap;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class SnakeGame implements Game {
    private SnakeMap map;
    private List<IGameObserver> observers = new ArrayList<>();
    private boolean gameTerminated = false;
    public static final int gap = 0;
    public static final int rectWidth = 10;

    private Timeline timeline;

    public SnakeGame(int width, int height, int speed, boolean wallsAround, boolean wallsInside){
        this.map = new SnakeMap(width, height, wallsAround, wallsInside, this);

        this.timeline = new Timeline(new KeyFrame(Duration.millis(speed), event -> {
            this.update();
        }
        ));
    }

    @Override
    public void start() {
        // Adding map elements
        this.map.initGame();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @Override
    public void update() {
        this.map.update();
    }

    @Override
    public void stop() {
        // Only if game was not stopped before
        if (!this.isGameTerminated()){
            this.gameTerminated = true;
            // Timer stop!
            this.timeline.stop();
            for(IGameObserver obs: this.observers)
                obs.gameTerminated();
        }
    }

    public boolean isGameTerminated() {
        return this.gameTerminated;
    }

    public SnakeMap getMap() {
        return this.map;
    }

    public void addObserver(IGameObserver obs) {
        this.observers.add(obs);
    }

    public List<IGameObserver> getObservers(){
        return this.observers;
    }
}
