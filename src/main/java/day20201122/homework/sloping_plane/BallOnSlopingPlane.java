package day20201122.homework.sloping_plane;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BallOnSlopingPlane extends Application {

    @Override
    public void start(Stage stage) {

        Group root = new Group();
        stage.setTitle("Ball dropped on sloping plane");
        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.show();

        double x = 50, y = 50;
        Circle ball = new Circle(x, y, 40);
        ball.setFill(Color.RED);
        root.getChildren().add(ball);

//        //Test color animation (blinking)
//        FadeTransition ft = new FadeTransition(Duration.millis(3000), ball);
//        ft.setFromValue(1.0);
//        ft.setToValue(0.1);
//        ft.setCycleCount(Timeline.INDEFINITE);
//        ft.setAutoReverse(true);
//        ft.play();

        Polygon triangle = new Polygon(
                0.0, 400.0,
                scene.getWidth() - 300.0, scene.getHeight(),
                0.0, scene.getHeight()
        );
        triangle.setFill(Color.DARKBLUE);
        root.getChildren().add(triangle);

        Line slopingPlane = new Line(0.0, 400.0, scene.getWidth() - 300.0, scene.getHeight());
        slopingPlane.setFill(Color.BLUE);
        slopingPlane.setStrokeWidth(3);
        root.getChildren().add(slopingPlane);

        Path path = new Path();
        path.getElements().add(new MoveTo(x, y));

        path.getElements().add(new LineTo(
                slopingPlane.getStartX() + x, slopingPlane.getStartY() - y / 2)
        );
        path.getElements().add(new LineTo(
                slopingPlane.getEndX() - ball.getRadius() + x, slopingPlane.getEndY() - ball.getRadius())
        );
        path.getElements().add(new LineTo(
                scene.getWidth() - ball.getRadius(), scene.getHeight() - ball.getRadius())
        );

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(3000));

        pathTransition.setPath(path);
        pathTransition.setNode(ball);

        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        // for back and forth animation in infinite loop
//        pathTransition.setCycleCount(Timeline.INDEFINITE);
//        pathTransition.setAutoReverse(true);

        pathTransition.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
