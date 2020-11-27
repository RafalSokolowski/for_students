package day20201122.homework.bouncing;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static day20201122.homework.Const.*;

@AllArgsConstructor
public class Conditions {

    private final Node root;
    private final Circle circle;
    @Getter
    private final Shape[] obstacles;

    public Timeline check() {

        Bounds[] bounds = new Bounds[obstacles.length + 1];

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(MILIS_PER_PIXEL_BOUNCING),
                new EventHandler<>() {

                    int deltaX = 1, deltaY = 1;

                    @Override
                    public void handle(ActionEvent actionEvent) {
                        circle.setLayoutX(circle.getLayoutX() + deltaX);
                        circle.setLayoutY(circle.getLayoutY() + deltaY);

                        bounds[0] = root.getBoundsInLocal();

                        for (int i = 1; i <= obstacles.length; i++)
                            bounds[i] = obstacles[i - 1].getBoundsInParent();

                        if (circle.getLayoutY() >= bounds[0].getMaxY() - circle.getRadius())
                            deltaY = -deltaY;
                        if (circle.getLayoutX() >= bounds[0].getMaxX() - circle.getRadius())
                            deltaX = -deltaX;
                        if (circle.getLayoutY() < circle.getRadius())
                            deltaY = -deltaY;
                        if (circle.getLayoutX() < circle.getRadius())
                            deltaX = -deltaX;

                        if (obstacles.length!=0) {
                            for (int i = 0; i < obstacles.length; i++) {
                                if (checkConditionsUpAdnDown(circle, bounds[i + 1])) {
//                                System.out.println(BLUE + "(" + circle.getLayoutX() + ", " + circle.getLayoutY() + ") <------------------BOOM UpAndDown_" + i + 1 + RESET);
//                                System.out.println(RED +
//                                        "(" + bounds[i + 1].getMinX() + ", " + bounds[i + 1].getMinY() + "), " +
//                                        "(" + bounds[i + 1].getMinX() + ", " + bounds[i + 1].getMaxY() + ")" + RESET);
                                    deltaY = -deltaY;
                                    circle.setFill(obstacles[i].getFill());
                                }
                                if (checkConditionLeftAndRight(circle, bounds[i + 1])) {
                                    deltaX = -deltaX;
                                    circle.setFill(obstacles[i].getFill());
                                }
                            }
                        }
                    }
                })
        );
        return timeline;
    }

    private boolean checkConditionsUpAdnDown(Circle circle, Bounds boundsOfObstacle) {

        return circle.getLayoutX() - circle.getRadius() + 1 < boundsOfObstacle.getMaxX() &&
                circle.getLayoutX() + circle.getRadius() > boundsOfObstacle.getMinX() &&
                circle.getLayoutY() - circle.getRadius() < boundsOfObstacle.getMaxY() &&
                circle.getLayoutY() + circle.getRadius() >= boundsOfObstacle.getMinY();
    }

    private boolean checkConditionLeftAndRight(Circle circle, Bounds boundsObstacle) {
        return circle.getLayoutX() - circle.getRadius() < boundsObstacle.getMaxX() &&
                circle.getLayoutX() + circle.getRadius() >= boundsObstacle.getMinX() &&
                circle.getLayoutY() - circle.getRadius() + 1 < boundsObstacle.getMaxY() &&
                circle.getLayoutY() + circle.getRadius() > boundsObstacle.getMinY();
    }

}