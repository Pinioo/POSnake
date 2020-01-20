package agh.po.snakegame.jfxelements;

import agh.po.snakegame.game.SnakeGame;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class SnakeMainMenu extends Stage {
    private Scene scene;
    private Label widthLabel = new Label("Width: ");
    private Slider widthSlider;
    private Label widthValueLabel;

    private Label heightLabel = new Label("Height: ");
    private Slider heightSlider;
    private Label heightValueLabel;

    private Label wallsLabel = new Label("Walls: ");
    private Label wallsAroundLabel = new Label("Around the map: ");
    private Label wallsInsideLabel = new Label("Obstacles inside: ");
    private CheckBox wallsAroundCheckBox;
    private CheckBox wallsInsideCheckBox;

    private Label speedLabel = new Label("Speed: ");
    private Slider speedSlider;

    private Button startButton;

    public SnakeMainMenu(){
        GridPane mainGrid = new GridPane();
        mainGrid.setPadding(new Insets(20));
        mainGrid.setHgap(10);
        mainGrid.setVgap(10);

        this.setTitle("Snake Main Menu");
        this.setResizable(false);
        this.scene = new Scene(mainGrid);
        this.setScene(this.scene);

        this.widthSlider = new Slider();
        this.widthSlider.setSnapToTicks(true);
        this.widthSlider.setShowTickMarks(true);
        this.widthSlider.setMin(10);
        this.widthSlider.setMax(100);
        this.widthSlider.setMinorTickCount(0);
        this.widthSlider.setShowTickLabels(true);
        this.widthSlider.setMajorTickUnit(5);
        this.widthValueLabel = new Label();
        this.widthValueLabel.setMinWidth(15);

        this.widthSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            updateLabels();
        });

        this.heightSlider = new Slider();
        this.heightSlider.setShowTickMarks(true);
        this.heightSlider.setSnapToTicks(true);
        this.heightSlider.setMin(10);
        this.heightSlider.setMax(80);
        this.heightSlider.setMajorTickUnit(5);
        this.heightSlider.setShowTickLabels(true);
        this.heightSlider.setMinorTickCount(0);
        this.heightValueLabel = new Label();
        this.heightValueLabel.setMinWidth(15);
        this.heightSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            updateLabels();
        });

        this.wallsAroundCheckBox = new CheckBox();

        this.wallsInsideCheckBox = new CheckBox();

        this.speedSlider = new Slider();
        this.speedSlider.setMin(0);
        this.speedSlider.setMax(3);
        this.speedSlider.setMajorTickUnit(1);
        this.speedSlider.setMinorTickCount(0);
        this.speedSlider.setSnapToTicks(true);
        this.speedSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if (n < 0.5) return "Snail";
                if (n < 1.5) return "Slow";
                if (n < 2.5) return "Normal";
                return "Fast";
            }

            @Override
            public Double fromString(String s) {
                switch (s){
                    case "Snail": return 0d;
                    case "Slow": return 1d;
                    case "Normal": return 2d;
                    case "Fast": return 3d;
                    default: return null;
                }
            }
        });
        this.speedSlider.setShowTickLabels(true);

        this.startButton = new Button("Start game");
        this.startButton.setOnAction(e -> this.startNewGame());

        mainGrid.add(this.widthLabel, 0, 0);
        mainGrid.add(this.heightLabel, 0, 1);
        mainGrid.add(this.wallsLabel, 0, 2);
        mainGrid.add(this.speedLabel, 0, 4);

        mainGrid.add(this.widthSlider, 1, 0);
        mainGrid.add(this.heightSlider, 1, 1);
        mainGrid.add(this.wallsAroundLabel, 1, 2);
        mainGrid.add(this.wallsInsideLabel, 1, 3);
        mainGrid.add(this.speedSlider, 1, 4);

        mainGrid.add(this.widthValueLabel, 2, 0);
        mainGrid.add(this.heightValueLabel, 2, 1);
        mainGrid.add(this.wallsAroundCheckBox, 2, 2);
        mainGrid.add(this.wallsInsideCheckBox, 2, 3);
        mainGrid.add(this.startButton, 2, 4);

        this.heightSlider.setValue(25);
        this.widthSlider.setValue(25);
        this.wallsAroundCheckBox.setSelected(true);
        this.wallsInsideCheckBox.setSelected(false);
        this.speedSlider.setValue(2);

        this.updateLabels();
    }

    private void updateLabels(){
        widthValueLabel.setText(String.valueOf((int)(this.widthSlider.getValue() + 2.5) / 5 * 5));
        heightValueLabel.setText(String.valueOf((int)(this.heightSlider.getValue() + 2.5) / 5 * 5));
    }

    public int getFrameDelay(){
        return (int)(350 - this.speedSlider.getValue() * 100);
    }

    private void startNewGame(){
        SnakeGame game = new SnakeGame((int)this.widthSlider.getValue(), (int)this.heightSlider.getValue(), this.getFrameDelay(), this.wallsAroundCheckBox.isSelected(), this.wallsInsideCheckBox.isSelected());
        SnakeGameStage stage = new SnakeGameStage(game);
        stage.show();
        game.start();
    }
}
