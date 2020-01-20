package agh.po.snakegame.interfaces;

import agh.po.snakegame.spatial.Vector2d;

public interface SingleMapElement {
    Vector2d getPosition();
    void add();
    void remove();
}
