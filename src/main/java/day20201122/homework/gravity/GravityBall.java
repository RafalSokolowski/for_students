package day20201122.homework.gravity;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import static day20201122.homework.Const.HEIGHT;
import static day20201122.homework.Const.WIDTH;

public class GravityBall extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Pane playground = new Pane();
        Scene scene = new Scene(playground, WIDTH, HEIGHT);

        stage.setTitle("Animation homework (gravity ball)");
        stage.setScene(scene);
        stage.show();

        Text text = new Text(30, 30, "Press digit from 1 to 5 to kick the ball ...");
        text.setFont(Font.font("Arial", 20));
        playground.getChildren().add(text);

        double startX = 50;
        double startY = 50;

        Circle ball = new Circle(20, Color.RED);
        ball.relocate(startX, startY);
        playground.getChildren().add(ball);

        Shape table = new Rectangle(0, startY + 2 * ball.getRadius(), ball.getLayoutY(), playground.getBoundsInLocal().getMaxY());
        playground.getChildren().add(table);

//        // add marker, for testing purposes
//        Shape marker = new Line(bottomX + ball.getLayoutX() + ball.getRadius(), 0, bottomX + ball.getLayoutX() + ball.getRadius(), HEIGHT);
//        marker.getStrokeDashArray().addAll(2d);
//        marker.setFill(Color.LIGHTGRAY.);
//        playground.getChildren().add(marker);

        scene.setOnKeyPressed(pressed -> {
            switch (pressed.getCode()) {
                case DIGIT1:
                    playground.getChildren().add(fly(0.4, ball, "very light"));
                    break;
                case DIGIT2:
                    playground.getChildren().add(fly(0.6, ball, "light"));
                    break;
                case DIGIT3:
                    playground.getChildren().add(fly(1, ball, "normal"));
                    break;
                case DIGIT4:
                    playground.getChildren().add(fly(2, ball, "heavy"));
                    break;
                case DIGIT5:
                    playground.getChildren().add(fly(9, ball, "very heavy"));
                    break;
                default:
                    playground.getChildren().add(fly(0.9, ball, "default"));
            }
        });

    }

    private static Text fly(double weight, Circle circle, String message) {
        Path path = new Path();

        double bottomX = WIDTH / 3 / weight;
        double bottomY = HEIGHT - circle.getLayoutY() - circle.getRadius();

        ArcTo arcTo = new ArcTo(
                HEIGHT - circle.getLayoutX() - circle.getRadius(),
                bottomX,
                90,
                bottomX,        // landing on the ground X
                bottomY,        // landing on the ground Y
                false,      // largeArcFlag == true means that the arc greater than 180 degrees will be drawn
                true        // sweepFlag == true means that the arc will be drawn in the positive angle direction
        );

        path.getElements().add(new MoveTo());
        path.getElements().add(arcTo);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(2000 / weight));      // time, weighted by the ball weight
        pathTransition.setNode(circle);
        pathTransition.setPath(path);
        pathTransition.play();

        Text text = new Text(390, 30, message + " ball was kicked, good job :)");
        text.setFont(Font.font("Arial", 20));

        return text;
    }

}