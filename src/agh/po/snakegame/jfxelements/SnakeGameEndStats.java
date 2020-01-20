package agh.po.snakegame.jfxelements;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SnakeGameEndStats extends Stage {
    private SnakeGameStage snakeGameStage;
    private Scene scene;

    public SnakeGameEndStats(SnakeGameStage snakeGameStage) {
        this.snakeGameStage = snakeGameStage;

        VBox vbox = new VBox();

        this.setTitle("Game Over!");
        this.scene = new Scene(vbox);
        this.setScene(this.scene);
        this.setMinWidth(250);

        Label label = new Label("You got " + this.snakeGameStage.getGame().getMap().getSnake().getSnakeLength() + " points!");
        vbox.getChildren().add(label);
        vbox.setPadding(new Insets(30));
        vbox.setAlignment(Pos.CENTER);

        this.setOnCloseRequest(e -> this.snakeGameStage.close());
    }
}
