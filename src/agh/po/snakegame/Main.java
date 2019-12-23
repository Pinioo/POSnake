package agh.po.snakegame;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application{
    private SnakeMapScene scene;
    private SnakeGame game;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.game = new SnakeGame(60, 60);

        Group root = new Group();
        this.scene = new SnakeMapScene(root, this.game);
        this.game.setScene(this.scene);
        root.getChildren().add(this.scene.canvas);

        primaryStage.setTitle("Snake");
        primaryStage.setScene(this.scene);
        primaryStage.setOnCloseRequest((a) -> System.exit(0));

        primaryStage.show();

        this.game.start();


        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            this.game.update();
        }));

        Timer terminator = new Timer();
        terminator.schedule(new TimerTask(){
            @Override
            public void run() {
                if(game.isGameTerminated()){
                    System.out.println("Termination!");
                    timeline.stop();
                    terminator.cancel();
                }
            }
        }, 0, 10);

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
