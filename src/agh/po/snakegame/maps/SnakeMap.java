package agh.po.snakegame.maps;

import agh.po.snakegame.Snake;
import agh.po.snakegame.SnakeGame;
import agh.po.snakegame.Vector2d;
import agh.po.snakegame.interfaces.SingleMapElement;
import agh.po.snakegame.mapelements.EmptyCell;
import agh.po.snakegame.mapelements.Food;
import agh.po.snakegame.mapelements.SnakeBodyPart;
import agh.po.snakegame.mapelements.Wall;
import agh.po.snakegame.maps.RectangularMap;

import java.util.Random;

public class SnakeMap extends RectangularMap {
    private Snake snake;
    private SnakeGame game;

    public SnakeMap(int width, int height, SnakeGame game) {
        super(width, height);
        this.game = game;
    }

    public void initGame(){
        this.initSnake();
        this.randomFood();
    }

    public void initSnake(){
        this.snake = new Snake(this);
        this.snake.addObserver(this);
        this.snake.addObserver(this.game.getScene());
        this.snake.initSnake(new Vector2d(this.getWidth()/2, this.getHeight()/2));
    }

    public Vector2d randPosition(){
        Random rand = new Random();
        return new Vector2d(rand.nextInt(this.getWidth()), rand.nextInt(this.getHeight()));
    }

    private void randomFood(){
        Vector2d position;
        do{
            position = this.randPosition();
        }while(this.isOccupied(position));
        Food newFood = new Food(position, this);
        newFood.addObserver(this);
        newFood.addObserver(this.game.getScene());
        newFood.add();
    }

    public void setWallOnBorder(){
        Wall addedWall;
        for(Vector2d position : this.border()) {
            addedWall = new Wall(position, this);
            addedWall.addObserver(this);
            addedWall.addObserver(this.game.getScene());
            addedWall.add();
        }
    }

    public void update(){
        this.snake.move();
    }

    @Override
    protected void collisionEvent(SingleMapElement newElement, SingleMapElement occupyingElement) {
        if (!(newElement instanceof SnakeBodyPart))
            throw new RuntimeException("Element of type " + newElement.getClass().getName() + " is not allowed to move!");
        if (occupyingElement instanceof Food){
            (((SnakeBodyPart)newElement).getOwner()).feed();
            this.randomFood();
        } else {
            (((SnakeBodyPart)newElement).getOwner()).dieEvent();
        }
    }

    @Override
    public void objectAdded(SingleMapElement element){
        if(this.isOccupied(element.getPosition())){
            collisionEvent(element, this.elementAt(element.getPosition()));
        }
        else{
            super.objectAdded(element);
        }
    }

    @Override
    public SingleMapElement elementAt(Vector2d position){
        SingleMapElement element = super.elementAt(position);
        return element == null ? new EmptyCell(position, this) : element;
    }

    public SnakeGame getGame() {
        return game;
    }

    public Snake getSnake() {
        return this.snake;
    }
}
