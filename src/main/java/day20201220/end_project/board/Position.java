package day20201220.end_project.board;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Position implements Comparable<Position> {

    private int y;
    private int x;

//    @Override
//    public int compareTo(Position p) {
//        if (x == p.getX()) return y - p.getY();
//        return x - p.getX();
//    }

    public boolean isInvalid() {
        return x < 0 || x >= 7 || y < 0 || y >= 7;
    }

    public boolean isValid() {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    @Override
    public int compareTo(Position p) {
        if (y == p.getY()) return x - p.getX();
        return y - p.getY();
    }

    @Override
    public String toString() {
//        return "(" + (char) (x+65) + "," + (y+1) + ")";
        return "" + (char) (x + 65) + (y + 1);
    }
}
