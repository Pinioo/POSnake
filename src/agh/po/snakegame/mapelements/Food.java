package agh.po.snakegame.mapelements;

import agh.po.snakegame.maps.SnakeMap;
import agh.po.snakegame.spatial.Vector2d;

public class Food extends SnakeMapElement {
    public Food(Vector2d initialPosition, SnakeMap map) {
        super(initialPosition, map);
    }
}
