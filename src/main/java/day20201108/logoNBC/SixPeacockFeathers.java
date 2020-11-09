package day20201108.logoNBC;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SixPeacockFeathers {

    public static Group create() {
        Shape peacockFeatherYellow = new PeacockFeather(
                new Point(275, 347),
                new Point(71, 347),
                new Point(99, 227),
                Color.rgb(252,183,17)
        ).create();

        Shape peacockFeatherOrange = new PeacockFeather(
                new Point(267, 327),
                new Point(82, 197),
                new Point(177, 121),
                Color.rgb(243,112,33)
        ).create();

        Shape peacockFeatherRed = new PeacockFeather(
                new Point(268, 305),
                new Point(178, 82),
                new Point(299, 77),
                Color.rgb(204,0,76)
        ).create();

        Shape peacockFeatherPurple = new PeacockFeather(
                new Point(344, 303),
                new Point(313, 81),
                new Point(434, 81),
                Color.rgb(100,96,170)
        ).create();

        Shape peacockFeatherBlue = new PeacockFeather(
                new Point(346, 325),
                new Point(438, 116),
                new Point(529, 197),
                Color.rgb(0,137,208)
        ).create();

        Shape peacockFeatherGreen = new PeacockFeather(
                new Point(338, 347),
                new Point(523, 224),
                new Point(545, 347),
                Color.rgb(13,177,75)
        ).create();

        return new Group(
                peacockFeatherYellow,
                peacockFeatherOrange,
                peacockFeatherRed,
                peacockFeatherPurple,
                peacockFeatherBlue,
                peacockFeatherGreen
        );
    }

}