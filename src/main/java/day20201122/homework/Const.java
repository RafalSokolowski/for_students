package day20201122.homework;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Const {

    public static final double WIDTH = 900;
    public static final double HEIGHT = 600;

    public static final double OBSTACLE_WIDTH = 200;
    public static final double OBSTACLE_HEIGHT = 100;

    public static final int BALL_RADIUS = 20;
    public static final int SPACE_FOR_BALL = 2 * BALL_RADIUS + 10;

    public static final Shape[] OBSTACLES_TEST = {
            new Rectangle(100, 100, 200, 100),
            new Rectangle(100, 400, 200, 100),
            new Rectangle(400, 100, 200, 100),
            new Rectangle(400, 400, 200, 100)
    };

    public static final int MILIS_PER_PIXEL_BOUNCING = 3;
    public static final int MILIS_PER_PIXEL_GRAVITY = 30;

    public static final double EARTH_GRAVITY = 9.807;   // m/s²
    public static final double MOON_GRAVITY = 1.62;     // m/s²
    public static final double MARS_GRAVITY = 3.711;    // m/s²

    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String RESET = "\u001B[0m";

}
