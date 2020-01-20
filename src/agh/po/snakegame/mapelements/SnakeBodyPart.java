package agh.po.snakegame.mapelements;

import agh.po.snakegame.spatial.Vector2d;

public class SnakeBodyPart extends SnakeMapElement {
    private final Snake owner;

    public SnakeBodyPart(Vector2d initialPosition, Snake snake) {
        super(initialPosition, snake.getMap());
        this.owner = snake;
    }

    public Snake getOwner(){
        return this.owner;
    }
}
