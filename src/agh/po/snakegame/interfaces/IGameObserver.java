package agh.po.snakegame.interfaces;

import agh.po.snakegame.interfaces.SingleMapElement;

public interface IGameObserver {
    void objectRemoved(SingleMapElement element);
    void objectAdded(SingleMapElement element);
    void gameTerminated();
}
