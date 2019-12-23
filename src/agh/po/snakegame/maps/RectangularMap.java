package agh.po.snakegame.maps;

import agh.po.snakegame.Vector2d;
import agh.po.snakegame.interfaces.IMap;
import agh.po.snakegame.interfaces.SingleMapElement;

import java.util.HashMap;
import java.util.LinkedList;

public abstract class RectangularMap implements IMap {
    private HashMap<Vector2d, SingleMapElement> elements;
    private Vector2d lowerLeft;
    private Vector2d upperRight;

    public RectangularMap(int width, int height){
        this.lowerLeft = new Vector2d(0,0);
        this.upperRight = new Vector2d(width - 1, height - 1);

        this.elements = new HashMap<>();
    }

    public LinkedList<Vector2d> allPositions(){
        LinkedList<Vector2d> allPositions = new LinkedList<>();
        for(int x = this.lowerLeft.getX(); x <= this.upperRight.getX(); x++)
            for(int y = this.lowerLeft.getY(); y <= this.upperRight.getY(); y++) {
                allPositions.add(new Vector2d(x, y));
            }
        return allPositions;
    }

    public LinkedList<Vector2d> border(){
        LinkedList<Vector2d> border = new LinkedList<>();
        for (int x = this.lowerLeft.getX(); x < this.upperRight.getX(); x++)
            border.add(new Vector2d(x, this.lowerLeft.getY()));

        for (int y = this.lowerLeft.getY(); y < this.upperRight.getY(); y++)
            border.add(new Vector2d(this.upperRight.getX(), y));

        for (int x = this.upperRight.getX(); x > this.lowerLeft.getX(); x--)
            border.add(new Vector2d(x, upperRight.getY()));

        for (int y = this.upperRight.getY(); y > this.lowerLeft.getY(); y--)
            border.add(new Vector2d(this.lowerLeft.getX(), y));

        return border;
    }

    protected abstract void collisionEvent(SingleMapElement newElement, SingleMapElement occupyingElement);

    @Override
    public SingleMapElement elementAt(Vector2d position) {
        return this.elements.get(position);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return this.elements.containsKey(position);
    }

    @Override
    public void objectRemoved(SingleMapElement element) {
        this.elements.remove(element.getPosition());
    }

    @Override
    public void objectAdded(SingleMapElement element) {
        if (this.elements.containsKey(element.getPosition()))
            // What happens if HashSet contains element at this position yet
            this.collisionEvent(element, this.elementAt(element.getPosition()));
        else
            this.elements.put(element.getPosition(), element);
    }

    public int getWidth(){
        return this.upperRight.getX() - this.lowerLeft.getX() + 1;
    }

    public int getHeight(){
        return this.upperRight.getY() - this.lowerLeft.getY() + 1;
    }
}
