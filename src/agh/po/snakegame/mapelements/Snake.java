package agh.po.snakegame.mapelements;

import agh.po.snakegame.spatial.MapDirection;
import agh.po.snakegame.spatial.Vector2d;
import agh.po.snakegame.maps.SnakeMap;

import java.util.LinkedList;

public class Snake {
    private LinkedList<SnakeBodyPart> bodyParts;
    private SnakeMap map;
    private MapDirection direction = MapDirection.NORTH;
    private MapDirection nextDirection = MapDirection.NORTH;
    private MapDirection queriedDirection = MapDirection.NORTH;
    private boolean foodEaten = false;

    public Snake(SnakeMap map){
        this.map = map;
        this.bodyParts = new LinkedList<>();
    }

    public void initSnake(Vector2d initialPosition){
        this.newBodyPart(initialPosition);
    }

    private void newBodyPart(Vector2d initialPosition) {
        SnakeBodyPart newPart = new SnakeBodyPart(initialPosition, this);
        this.bodyParts.addFirst(newPart);
        newPart.add();
    }

    public SnakeBodyPart getHead(){
        return this.bodyParts.getFirst();
    }

    public SnakeBodyPart getPreviousHead() {
        return this.bodyParts.size() > 1 ? this.bodyParts.get(1) : null;
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
        this.newBodyPart(this.map.correctPosition(nextPosition.add(this.direction.toUnitVector())));
    }

    public void shortenSnake() {
        SnakeBodyPart removedPart = this.bodyParts.removeLast();
        removedPart.remove();
    }

    // Growing costs a single foodEaten by snake
    public void grow(){
        this.foodEaten = false;
    }

    public void dieEvent() {
        this.map.getGame().stop();
    }

    public SnakeMap getMap(){
        return this.map;
    }

    public void changeDirection(MapDirection qDirection){
        if(this.direction == qDirection || this.direction.opposite() == qDirection) {
            // To make quick u-turns
            // If any perpendicular direction (to current snake's direction) was chosen
            // Parallel direction is queued
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

    public int getSnakeLength(){
        return this.bodyParts.size();
    }
}
