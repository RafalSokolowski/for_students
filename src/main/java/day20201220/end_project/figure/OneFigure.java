package day20201220.end_project.figure;

import day20201220.end_project.checker.Position;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import static day20201220.end_project.utils.Const.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class OneFigure implements OnTheBoard, Comparable<OneFigure> {

    private int state;          // 0-eliminated, 1-in the game
    private int figure;         // 0-pawn, 1-dame
    private int color;          // 0-dark, 1-light
    private Position position;  // (y,x)

    public OneFigure(String string) {
        if (string.length() != 9)
            throw new IllegalArgumentException(OneFigure.class.getSimpleName() + " accepts only string with length equal to 9, not " + string.length());
        if (!string.matches("[0,1]+"))
            throw new IllegalArgumentException(OneFigure.class.getSimpleName() + " accepts only string with 0 or 1");

        this.state = Integer.parseInt(string.substring(0, 1));
        this.figure = Integer.parseInt(string.substring(1, 2));
        this.color = Integer.parseInt(string.substring(2, 3));

        int y = Integer.parseInt(string.substring(3, 6), 2);
        int x = Integer.parseInt(string.substring(6, 9), 2);
        this.position = new Position(y, x);
    }

    public OneFigure(long number) {
        this.state = (int) number >> 8 & 1;
        this.figure = (int) number >> 7 & 1;
        this.color = (int) number >> 6 & 1;

        int y = (int) number >> 3 & 0b111;
        int x = (int) number & 0b111;
        this.position = new Position(y, x);
    }

    @Override
    public int compareTo(OneFigure oneFigure) {
        return position.compareTo(oneFigure.getPosition());
    }

    public String values() {
        return "State: " + BLUE + state + RESET + ", Figure: " + BLUE + figure + RESET + ", Color: " +
                BLUE + color + RESET + ", Y: " + BLUE + position.getY() + RESET + ", X:" + BLUE + position.getX() + RESET;
    }

    public void print() {
        System.out.println(values());
    }

    @Override
    public String toString() {
        String stringY = Integer.toBinaryString(position.getY());
        String stringX = Integer.toBinaryString(position.getX());

        return Integer.toBinaryString(state) +
                Integer.toBinaryString(figure) +
                Integer.toBinaryString(color) +
                (stringY.length() == 3 ? stringY : stringY.length() == 2 ? "0" + stringY : "00" + stringY) +
                (stringX.length() == 3 ? stringX : (stringX.length() == 2) ? "0" + stringX : "00" + stringX);
    }
}
