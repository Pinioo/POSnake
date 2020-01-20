package agh.po.snakegame.jfxelements;

import agh.po.snakegame.game.SnakeGame;
import javafx.scene.Group;
import javafx.stage.Stage;

public class SnakeGameStage extends Stage {
    private final SnakeMapScene scene;
    private final SnakeGame game;

    SnakeGameStage(SnakeGame game){
        this.game = game;

        Group root = new Group();

        this.setTitle("Snake");
        this.scene = new SnakeMapScene(root, this.game);
        this.setScene(this.scene);
        this.setResizable(false);
        this.setOnCloseRequest(e -> game.stop());

        root.getChildren().add(this.scene.getCanvas());
    }

    public SnakeGame getGame(){
        return this.game;
    }
}
