package agh.po.snakegame.mapelements;

import agh.po.snakegame.interfaces.IMapElementObserver;
import agh.po.snakegame.interfaces.SingleMapElement;
import agh.po.snakegame.maps.SnakeMap;
import agh.po.snakegame.Vector2d;

import java.util.LinkedList;

public abstract class SnakeMapElement implements SingleMapElement {
    private final Vector2d position;
    private final SnakeMap map;
    private LinkedList<IMapElementObserver> observers;


    public SnakeMapElement(Vector2d initialPosition, SnakeMap map){
        this.position = initialPosition;
        this.map = map;
        this.observers = new LinkedList<>();
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public void addObserver(IMapElementObserver obs){
        this.observers.add(obs);
    }

    public void removeObserver(IMapElementObserver obs){
        this.observers.remove(obs);
    }

    public void add() {
        for(IMapElementObserver obs : this.observers)
            obs.objectAdded(this);
    }
    public void remove(){
        for(IMapElementObserver obs : this.observers)
            obs.objectRemoved(this);
    }
}
