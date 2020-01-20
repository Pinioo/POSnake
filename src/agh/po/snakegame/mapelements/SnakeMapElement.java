package agh.po.snakegame.mapelements;

import agh.po.snakegame.interfaces.IGameObserver;
import agh.po.snakegame.interfaces.SingleMapElement;
import agh.po.snakegame.maps.SnakeMap;
import agh.po.snakegame.spatial.Vector2d;

public abstract class SnakeMapElement implements SingleMapElement {
    private final Vector2d position;
    private final SnakeMap map;

    public SnakeMapElement(Vector2d initialPosition, SnakeMap map){
        this.position = initialPosition;
        this.map = map;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    // Inform all game observers that object wants to be added
    public void add() {
        for(IGameObserver obs : this.map.getGame().getObservers())
            obs.objectAdded(this);
    }

    // Inform all game observers that object wants to be removed
    public void remove(){
        for(IGameObserver obs : this.map.getGame().getObservers())
            obs.objectRemoved(this);
    }
}
