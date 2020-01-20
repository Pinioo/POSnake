package agh.po.snakegame.main;

import agh.po.snakegame.jfxelements.SnakeMainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) {
        new SnakeMainMenu().show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
