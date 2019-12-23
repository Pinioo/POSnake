package agh.po.snakegame.mapelements;

import agh.po.snakegame.maps.SnakeMap;
import agh.po.snakegame.Vector2d;

public class Wall extends SnakeMapElement {
    public Wall(Vector2d initialPosition, SnakeMap map) {
        super(initialPosition, map);
    }

    @Override
    public void remove() {
        // Do nothing, wall cannot be removed from map
    }
}
