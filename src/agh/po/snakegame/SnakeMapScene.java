package agh.po.snakegame;

import agh.po.snakegame.interfaces.IMapElementObserver;
import agh.po.snakegame.interfaces.SingleMapElement;
import agh.po.snakegame.mapelements.Food;
import agh.po.snakegame.mapelements.SnakeBodyPart;
import agh.po.snakegame.mapelements.Wall;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class SnakeMapScene extends Scene implements IMapElementObserver {
    SnakeGame game;
    Canvas canvas;
    private static Color BACKGROUND_COLOR = Color.BLACK;
    private static Color WALL_COLOR = Color.GREY;
    private static Color FOOD_COLOR = Color.RED;
    private static Color SNAKE_COLOR = Color.WHITE;

    public SnakeMapScene(Parent parent, SnakeGame game){
        super(parent, game.map.getWidth() * (SnakeGame.rectWidth + SnakeGame.gap) - SnakeGame.gap, game.map.getHeight() * (SnakeGame.rectWidth + SnakeGame.gap) - SnakeGame.gap);

        this.game = game;
        this.setOnKeyPressed(this::changeSnakeDirection);


        this.canvas = new Canvas(
                game.map.getWidth() * (SnakeGame.rectWidth + SnakeGame.gap) - SnakeGame.gap,
                game.map.getHeight() * (SnakeGame.rectWidth + SnakeGame.gap) - SnakeGame.gap
        );
        this.canvas.getGraphicsContext2D().setFill(BACKGROUND_COLOR);
        this.canvas.getGraphicsContext2D().fillRect(0,0,1000,1000);
    }

    public void update(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for(Vector2d position : this.game.map.allPositions())
            if(this.game.map.isOccupied(position))
                gc.fillRect(this.getXonMap(position), this.getYonMap(position), SnakeGame.rectWidth, SnakeGame.rectWidth);
    }

    @Override
    public void objectRemoved(SingleMapElement element) {
        this.fillRectAtPosition(element.getPosition(), BACKGROUND_COLOR);
    }

    @Override
    public void objectAdded(SingleMapElement element) {
        if (element instanceof SnakeBodyPart) {
            this.fillCircleAtPosition(element.getPosition(), SNAKE_COLOR);
        } else if (element instanceof Wall) {
            this.fillRectAtPosition(element.getPosition(), WALL_COLOR);
        } else if (element instanceof Food) {
            this.fillRectAtPosition(element.getPosition(), FOOD_COLOR);
        } else {
            this.fillRectAtPosition(element.getPosition(), BACKGROUND_COLOR);
        }
    }

    private void fillRectAtPosition(Vector2d position, Color color){
        this.canvas.getGraphicsContext2D().setFill(color);
        this.canvas.getGraphicsContext2D().fillRect(
                this.getXonMap(position),
                this.getYonMap(position),
                SnakeGame.rectWidth,
                SnakeGame.rectWidth
        );
    }

    private void fillCircleAtPosition(Vector2d position, Color color){
        this.fillRectAtPosition(position, BACKGROUND_COLOR);
        this.canvas.getGraphicsContext2D().setFill(color);
        this.canvas.getGraphicsContext2D().fillOval(
                this.getXonMap(position),
                this.getYonMap(position),
                SnakeGame.rectWidth,
                SnakeGame.rectWidth
        );
    }

    private void changeSnakeDirection(KeyEvent keyEvent){
        MapDirection directToSet = this.game.getMap().getSnake().getDirection();
        switch (keyEvent.getCode()){
            case UP:
                directToSet = MapDirection.NORTH;
                break;
            case RIGHT:
                directToSet = MapDirection.EAST;
                break;
            case DOWN:
                directToSet = MapDirection.SOUTH;
                break;
            case LEFT:
                directToSet = MapDirection.WEST;
                break;
        }
        this.game.getMap().getSnake().changeDirection(directToSet);
    }

    private int getXonMap(Vector2d position){
        return position.getX() * (SnakeGame.rectWidth + SnakeGame.gap);
    }

    private int getYonMap(Vector2d position){
        return position.getY() * (SnakeGame.rectWidth + SnakeGame.gap);
    }
}
