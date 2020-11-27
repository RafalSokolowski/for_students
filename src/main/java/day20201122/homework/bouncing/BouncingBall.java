package day20201122.homework.bouncing;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import static day20201122.homework.Const.*;

public class BouncingBall extends Application {

    @Override
    public void start(Stage stage) {
        Pane playground = new Pane();
        Scene scene = new Scene(playground, WIDTH, HEIGHT);

        stage.setTitle("Animation homework (bouncing ball)");
        stage.setScene(scene);
        stage.show();

        Circle ball = new Circle(BALL_RADIUS, Color.DARKGREY);    // ball starts in (0,0) position (for center of the circle / ball)
        ball.relocate(0, 0);                               // ball is moved to (radius,radius) but bounds stay the same...
        playground.getChildren().add(ball);

        Shape[] obstacles = new Obstacles(4).generate();
        playground.getChildren().addAll(obstacles);

        Timeline timeline = new Conditions(playground, ball, obstacles).check();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }

}