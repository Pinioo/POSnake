package agh.po.snakegame.interfaces;

import agh.po.snakegame.interfaces.SingleMapElement;

public interface IMapElementObserver {
    void objectRemoved(SingleMapElement element);
    void objectAdded(SingleMapElement element);
}
