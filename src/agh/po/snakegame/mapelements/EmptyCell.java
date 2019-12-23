package agh.po.snakegame.mapelements;

import agh.po.snakegame.maps.SnakeMap;
import agh.po.snakegame.Vector2d;

public class EmptyCell extends SnakeMapElement {
    public EmptyCell(Vector2d initialPosition, SnakeMap map) {
        super(initialPosition, map);
    }
}
