package agh.po.snakegame;

import agh.po.snakegame.interfaces.IMapElementObserver;
import agh.po.snakegame.mapelements.SnakeBodyPart;
import agh.po.snakegame.maps.SnakeMap;

import java.util.LinkedList;

public class Snake {
    private LinkedList<SnakeBodyPart> bodyParts;
    private LinkedList<IMapElementObserver> observers;
    private SnakeMap map;
    private MapDirection direction = MapDirection.NORTH;
    private MapDirection nextDirection = MapDirection.NORTH;
    private MapDirection queriedDirection = MapDirection.NORTH;
    private boolean foodEaten = false;

    public Snake(SnakeMap map){
        this.map = map;
        this.bodyParts = new LinkedList<>();
        this.observers = new LinkedList<>();
    }

    public void initSnake(Vector2d initialPosition){
        this.newBodyPart(initialPosition);
    }

    private void newBodyPart(Vector2d initialPosition) {
        SnakeBodyPart newPart = new SnakeBodyPart(initialPosition, this);
        for(IMapElementObserver obs: this.observers)
            newPart.addObserver(obs);

        this.bodyParts.addFirst(newPart);
        newPart.add();
    }

    private SnakeBodyPart getHead(){
        return this.bodyParts.getFirst();
    }

    public void move(){
        this.direction = this.nextDirection;
        this.nextDirection = this.queriedDirection;
        Vector2d nextPosition = this.getHead().getPosition();
        if(this.isFed()) {
            this.grow();
        } else {
            this.shortenSnake();
        }
        this.newBodyPart(nextPosition.add(this.direction.toUnitVector()));
    }

    public void shortenSnake() {
        SnakeBodyPart removedPart = this.bodyParts.removeLast();
        removedPart.remove();
    }

    public void grow(){
        this.foodEaten = false;
    }

    public void dieEvent() {
        this.map.getGame().stop();
    }

    public SnakeMap getMap(){
        return this.map;
    };

    public void addObserver(IMapElementObserver obs) {
        this.observers.add(obs);
    }

    public void changeDirection(MapDirection qDirection){
        if(this.direction == qDirection || this.direction.opposite() == qDirection) {
            System.out.println("YIKES");
            if (this.nextDirection != this.direction) {
                this.queriedDirection = qDirection;
            }
        }
        else{
            this.nextDirection = qDirection;
            this.queriedDirection = qDirection;
        }
    }

    public void feed(){
        this.foodEaten = true;
    }

    public boolean isFed(){
        return this.foodEaten;
    }

    public MapDirection getDirection() {
        return this.direction;
    }
}
