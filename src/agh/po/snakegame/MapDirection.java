package agh.po.snakegame;

public enum MapDirection {
    NORTH(new Vector2d(0,-1)),
    EAST(new Vector2d(1,0)),
    SOUTH(new Vector2d(0,1)),
    WEST(new Vector2d(-1,0));

    private final Vector2d unitVector;

    MapDirection(Vector2d unitVector){
        this.unitVector = unitVector;
    }

    public MapDirection opposite(){
        return MapDirection.values()[(this.ordinal() + 2) % 4];
    }

    public Vector2d toUnitVector(){
        return this.unitVector;
    }
}
