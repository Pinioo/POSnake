package agh.po.snakegame.interfaces;

import agh.po.snakegame.Vector2d;

public interface IMap extends IMapElementObserver {
    SingleMapElement elementAt(Vector2d position);
    boolean isOccupied(Vector2d position);
}
