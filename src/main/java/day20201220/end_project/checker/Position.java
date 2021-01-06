package day20201220.end_project.checker;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static day20201220.end_project.utils.Const.*;

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
        return x < FIRST_COORDINATE ||
                x >= LAST_COORDINATE ||
                y < FIRST_COORDINATE ||
                y >= LAST_COORDINATE;
    }

    public boolean isValid() {
        return x >= FIRST_COORDINATE &&
                x <= LAST_COORDINATE &&
                y >= FIRST_COORDINATE &&
                y <= LAST_COORDINATE;
    }

    private boolean isPositionStringValid(String positionString) {
        if (positionString.length() != 2) {
            return false;
        }
        try {
            int[] yx = getYXFromString(positionString);
            return new Position(yx[0], yx[1]).isValid();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private int[] getYXFromString(String string) {
        String[] positionStringTab = string.split("");
        int x = string.charAt(0) - CHAR_INT_CONVERSION;
        int y = Integer.parseInt(positionStringTab[1]) - INDEXING_FROM_1;
        return new int[]{y, x};
    }

    @Override
    public int compareTo(Position p) {
        if (y == p.getY()) return x - p.getX();
        return y - p.getY();
    }

    @Override
    public String toString() {
        return "" + (char) (x + CHAR_INT_CONVERSION) + (y + INDEXING_FROM_1);
    }
}
