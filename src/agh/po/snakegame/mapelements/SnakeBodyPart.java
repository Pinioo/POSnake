package agh.po.snakegame.mapelements;

import agh.po.snakegame.Snake;
import agh.po.snakegame.Vector2d;

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
