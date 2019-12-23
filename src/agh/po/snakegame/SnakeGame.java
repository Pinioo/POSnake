package agh.po.snakegame;

import agh.po.snakegame.interfaces.Game;
import agh.po.snakegame.interfaces.IMapElementObserver;
import agh.po.snakegame.maps.SnakeMap;

public class SnakeGame implements Game {
    SnakeMap map;
    SnakeMapScene scene;
    private boolean gameTerminated = false;
    public static final int gap = 0;
    public static final int rectWidth = 10;

    public SnakeGame(int width, int height){
        this.map = new SnakeMap(width, height, this);
    }

    @Override
    public void start() {
        this.map.setWallOnBorder();
        this.map.initGame();
    }

    @Override
    public void update() {
        this.map.update();
    }

    @Override
    public void stop() {
        gameTerminated = true;
    }

    public void setScene(SnakeMapScene scene) {
        this.scene = scene;
    }

    public boolean isGameTerminated() {
        return gameTerminated;
    }

    public SnakeMap getMap() {
        return this.map;
    }

    public SnakeMapScene getScene() {
        return this.scene;
    }
}
