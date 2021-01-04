package day20201220.end_project.board;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static day20201220.end_project.utils.Const.INVALID_COORDINATE;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Position implements Comparable<Position> {

    private final int y;
    private final int x;

    public Position(String positionString) {
        if (isPositionStringValid(positionString)) {
            int[] yx = getYXFromString(positionString);
            this.y = yx[0];
            this.x = yx[1];
        } else {
            this.y = INVALID_COORDINATE;
            this.x = INVALID_COORDINATE;
        }
    }

    public boolean isInvalid() {
        return x < 0 || x >= 7 || y < 0 || y >= 7;
    }

    public boolean isValid() {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    private boolean isPositionStringValid(String positionString) {
        if (positionString.length() != 2) {
            return false;
        }
        try {
//            String[] positionStringTab = positionString.split("");
//            int x = Integer.parseInt(positionStringTab[0]);
//            int y = Integer.parseInt(positionStringTab[1]);
            int[] yx = getYXFromString(positionString);
            return new Position(yx[0], yx[1]).isValid();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private int[] getYXFromString(String string) {
        String[] positionStringTab = string.split("");
        int x = Integer.parseInt(positionStringTab[0]);
        int y = Integer.parseInt(positionStringTab[1]);
        return new int[]{y, x};
    }

    private Integer tryParseToInteger(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return null;
        }
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
