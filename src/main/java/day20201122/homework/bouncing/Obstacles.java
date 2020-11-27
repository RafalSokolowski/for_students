package day20201122.homework.bouncing;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import lombok.ToString;

import java.util.Optional;

import static day20201122.homework.Const.*;

@ToString
public class Obstacles {

    private final int amountOfObstacles;

    public Obstacles(int amountOfObstacles) {
        this.amountOfObstacles = setAmountOfObstacles(amountOfObstacles);
    }

    public Shape[] generate() {



        switch (amountOfObstacles) {
            case 0:
                return new Shape[]{};
            case 1:
                return new Shape[]{
                        generateObstacle(SPACE_FOR_BALL, WIDTH-SPACE_FOR_BALL, SPACE_FOR_BALL, HEIGHT-SPACE_FOR_BALL)
                };
            case 2:
                return new Shape[]{
                        generateObstacle(0, (int) (WIDTH / 2), SPACE_FOR_BALL, HEIGHT),
                        generateObstacle((int) (WIDTH / 2), WIDTH, SPACE_FOR_BALL, HEIGHT),
                };
            case 3:
                return new Shape[]{
                        generateObstacle(0, (int) (WIDTH / 2), SPACE_FOR_BALL, (int)(HEIGHT / 2)),
                        generateObstacle((int) (WIDTH / 2), WIDTH, SPACE_FOR_BALL, (int)(HEIGHT / 2)),
                        generateObstacle(0, WIDTH, (int)(HEIGHT / 2), HEIGHT),
                };
            default:
                return new Shape[]{
                        generateObstacle(SPACE_FOR_BALL, (int) (WIDTH / 2), SPACE_FOR_BALL, (int)(HEIGHT / 2)),
                        generateObstacle((int) (WIDTH / 2), WIDTH, SPACE_FOR_BALL, (int)(HEIGHT / 2)),
                        generateObstacle(0, (int) (WIDTH / 2), (int)(HEIGHT / 2), HEIGHT),
                        generateObstacle((int) (WIDTH / 2), WIDTH, (int)(HEIGHT / 2), HEIGHT)
                };
        }
    }

    private int setAmountOfObstacles(int amountOfObstacles) {
        if (amountOfObstacles < 0 || amountOfObstacles > 4)
            System.err.println("Current version of the app accepts only 0 to 4 obstacles " +
                    "(and you have provided: " + amountOfObstacles + ")... " +
                    "thus the amount of obstacles was changed to default value, which is 4");
        return amountOfObstacles;
    }

    private Shape generateObstacle(double startX, double endX, double startY, double endY) {
        double x = (endX - startX) * Math.random();
        double y = (endY - startY) * Math.random();
        while (x + OBSTACLE_WIDTH > endX || x < startX)
            x = (endX - startX) * Math.random() + startX;
        while (y + OBSTACLE_HEIGHT > endY || y < startY)
            y = (endY - startY) * Math.random() + startY;

        Shape result = new Rectangle((int) x, (int) y, OBSTACLE_WIDTH, OBSTACLE_HEIGHT);
        result.setFill(randomBallColor());

        return result;
    }

    private Color randomBallColor() {
        int getRandomR = (int) (Math.random() * 255);
        int getRandomG = (int) (Math.random() * 255);
        int getRandomB = (int) (Math.random() * 255);
        return Color.rgb(getRandomR, getRandomG, getRandomB).darker().darker();
    }

}
