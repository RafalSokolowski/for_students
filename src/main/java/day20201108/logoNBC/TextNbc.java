package day20201108.logoNBC;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.AllArgsConstructor;

import java.io.InputStream;

@AllArgsConstructor
public class TextNbc {

    private final double positionX;
    private final double positionY;
    private final String toWrite;
    private final int fontSize;

    public Text create() {
        Text text = new Text(positionX, positionY, toWrite);

        String path = "Font/FontNBC.ttf";
        InputStream fontStream = TextNbc.class.getClassLoader().getResourceAsStream(path);
        Font fontNBC = Font.loadFont(fontStream, fontSize);
        text.setFont(fontNBC);
        text.setFill(Color.BLACK);

        return text;
    }

}
