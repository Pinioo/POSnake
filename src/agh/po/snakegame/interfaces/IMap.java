package agh.po.snakegame.interfaces;

import agh.po.snakegame.spatial.Vector2d;

public interface IMap extends IGameObserver {
    SingleMapElement elementAt(Vector2d position);
    boolean isOccupied(Vector2d position);
}
