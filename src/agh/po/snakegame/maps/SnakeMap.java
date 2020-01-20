package agh.po.snakegame.maps;

import agh.po.snakegame.mapelements.Snake;
import agh.po.snakegame.game.SnakeGame;
import agh.po.snakegame.spatial.Vector2d;
import agh.po.snakegame.interfaces.SingleMapElement;
import agh.po.snakegame.mapelements.Food;
import agh.po.snakegame.mapelements.SnakeBodyPart;
import agh.po.snakegame.mapelements.Wall;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class SnakeMap extends RectangularMap {
    private Snake snake;
    private SnakeGame game;
    private boolean wallsAround;
    private boolean wallsInside;
    private HashSet<Vector2d> freePositions;
    private static Random rand = new Random();

    public SnakeMap(int width, int height, boolean wallsAround, boolean wallsInside, SnakeGame game) {
        super(width, height);
        this.freePositions = new HashSet<>(this.allPositions());
        this.wallsAround = wallsAround;
        this.wallsInside = wallsInside;
        this.game = game;
        this.game.addObserver(this);
    }

    public void initGame(){
        if(this.wallsAround) this.setWallsOnBorder();
        if(this.wallsInside) this.setWallsInside();
        this.initSnake();
        this.randomFood();
    }

    public void initSnake(){
        this.snake = new Snake(this);
        this.snake.initSnake(new Vector2d(this.getWidth()/2, this.getHeight()/2));
    }

    public Vector2d randFreePosition(){
        ArrayList<Vector2d> freePositions = new ArrayList<>(this.freePositions);
        return freePositions.get(rand.nextInt(freePositions.size()));
    }

    private void randomFood(){
        if(!this.freePositions.isEmpty())
            new Food(this.randFreePosition(), this).add();
    }

    public void setWallsOnBorder(){
        for(Vector2d position : this.border())
            new Wall(position, this).add();
    }

    // Adds obstacles in map's center
    private void setWallsInside() {
        int positionX1 = 2*this.getWidth()/7;
        int positionX2 = this.getWidth() - positionX1 - 1;
        int startY = 2*this.getHeight()/7;
        for(int y = startY; y <= this.getHeight() - startY - 1; y++) {
            new Wall(new Vector2d(positionX1, y), this).add();
            new Wall(new Vector2d(positionX2, y), this).add();
        }
    }

    public void update(){
        this.snake.move();
    }

    @Override
    protected void collisionEvent(SingleMapElement newElement, SingleMapElement occupyingElement) {
        if (!(newElement instanceof SnakeBodyPart))
            // Only SnakeBodyPart can collide with something else, so throw exception
            throw new RuntimeException("Element of type " + newElement.getClass().getName() + " is not allowed to move!");

        if (occupyingElement instanceof Food){
            // If collided with food -> eat and put a new food on map
            (((SnakeBodyPart)newElement).getOwner()).feed();
            this.randomFood();
        } else {
            // If collided with wall -> end game
            (((SnakeBodyPart)newElement).getOwner()).dieEvent();
        }
    }

    @Override
    public void objectAdded(SingleMapElement element){
        if(this.isOccupied(element.getPosition())){
            this.collisionEvent(element, this.elementAt(element.getPosition()));
        }
        else{
            this.freePositions.remove(element.getPosition());
            super.objectAdded(element);
        }
    }

    @Override
    public void objectRemoved(SingleMapElement element){
        this.freePositions.add(element.getPosition());
        super.objectRemoved(element);
    }

    @Override
    public void gameTerminated() {
        // no actions needed
    }

    public SnakeGame getGame() {
        return game;
    }

    public Snake getSnake() {
        return this.snake;
    }
}
