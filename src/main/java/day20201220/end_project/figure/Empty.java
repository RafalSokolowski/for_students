package day20201220.end_project.figure;

import day20201220.end_project.board.Position;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static day20201122.homework.Const.BLUE;
import static day20201122.homework.Const.RESET;
import static day20201220.end_project.utils.Const.BLACK_FIELD;
import static day20201220.end_project.utils.Const.WHITE_FIELD;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Empty implements OnTheBoard, Comparable<Empty> {

    private int color;     // 0-black, 1-white
    private int y;          // y position
    private int x;          // x position

//    @Override
//    public int compareTo(Empty empty) {
//        if (x == empty.getX()) return y - empty.getY();
//        return x - empty.getX();
//    }

    @Override
    public int compareTo(Empty empty) {
        if (y == empty.getY()) return x-empty.getX();
        return y-empty.getY();
    }

    public String values() {
        return "Color: " + BLUE + color + RESET + ", Y: " + BLUE + y + RESET + ", X:" + BLUE + x + RESET;
    }

    public void print() {
        System.out.println(values());
    }

    @Override
    public String toString() {
        String stringY = Integer.toBinaryString(y);
        String stringX = Integer.toBinaryString(x);

        return (color == 0 ? BLACK_FIELD : WHITE_FIELD) + " " +
                (stringY.length() == 3 ? stringY : stringY.length() == 2 ? "0" + stringY : "00" + stringY) + " " +
                (stringX.length() == 3 ? stringX : (stringX.length() == 2) ? "0" + stringX : "00" + stringX);
    }
}
