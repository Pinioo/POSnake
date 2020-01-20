package agh.po.snakegame.jfxelements;

import agh.po.snakegame.spatial.MapDirection;
import agh.po.snakegame.game.SnakeGame;
import agh.po.snakegame.spatial.Vector2d;
import agh.po.snakegame.interfaces.IGameObserver;
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

public class SnakeMapScene extends Scene implements IGameObserver {
    private SnakeGame game;
    private Canvas canvas;
    private static final Color BACKGROUND_COLOR = Color.OLIVEDRAB;
    private static final Color WALL_COLOR = Color.DARKGRAY;
    private static final Color FOOD_COLOR = Color.DARKRED;
    private static final Color SNAKE_COLOR = Color.BROWN;
    private static final Color SNAKE_HEAD_COLOR = Color.WHITE;

    public SnakeMapScene(Parent parent, SnakeGame game){
        super(parent, game.getMap().getWidth() * (SnakeGame.rectWidth + SnakeGame.gap) - SnakeGame.gap, game.getMap().getHeight() * (SnakeGame.rectWidth + SnakeGame.gap) - SnakeGame.gap);

        this.game = game;
        this.game.addObserver(this);
        this.setOnKeyPressed(this::changeSnakeDirection);

        this.canvas = new Canvas(
                game.getMap().getWidth() * (SnakeGame.rectWidth + SnakeGame.gap) - SnakeGame.gap,
                game.getMap().getHeight() * (SnakeGame.rectWidth + SnakeGame.gap) - SnakeGame.gap
        );
        this.canvas.getGraphicsContext2D().setFill(BACKGROUND_COLOR);
        this.canvas.getGraphicsContext2D().fillRect(0,0,1000,1000);
    }

    @Override
    public void objectRemoved(SingleMapElement element) {
        this.fillRectAtPosition(element.getPosition(), BACKGROUND_COLOR);
    }

    @Override
    public void objectAdded(SingleMapElement element) {
        if (element instanceof SnakeBodyPart) {
            SnakeBodyPart previousHead = ((SnakeBodyPart) element).getOwner().getPreviousHead();
            if(previousHead != null){
                this.fillCircleAtPosition(previousHead.getPosition(), SNAKE_COLOR);
            }
            this.fillCircleAtPosition(element.getPosition(), SNAKE_HEAD_COLOR);
        } else if (element instanceof Wall) {
            this.fillRectAtPosition(element.getPosition(), WALL_COLOR);
        } else if (element instanceof Food) {
            this.fillRectAtPosition(element.getPosition(), FOOD_COLOR);
        } else {
            this.fillRectAtPosition(element.getPosition(), BACKGROUND_COLOR);
        }
    }

    @Override
    public void gameTerminated() {
        new SnakeGameEndStats((SnakeGameStage) this.getWindow()).show();
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
        MapDirection directToSet;
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
            default:
                return;
        }
        this.game.getMap().getSnake().changeDirection(directToSet);
    }

    public Canvas getCanvas(){
        return this.canvas;
    }

    private int getXonMap(Vector2d position){
        return position.getX() * (SnakeGame.rectWidth + SnakeGame.gap);
    }

    private int getYonMap(Vector2d position){
        return position.getY() * (SnakeGame.rectWidth + SnakeGame.gap);
    }
}
