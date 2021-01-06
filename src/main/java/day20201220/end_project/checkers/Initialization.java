package day20201220.end_project.checkers;

import day20201220.end_project.Player;
import day20201220.end_project.figure.Empty;
import day20201220.end_project.figure.OnTheBoard;
import day20201220.end_project.figure.OneFigure;
import day20201220.end_project.figure.SixFigures;
import lombok.AllArgsConstructor;

import java.util.Map;

import static day20201220.end_project.utils.Const.*;

@AllArgsConstructor
public class Initialization {

    private Map<Position, OnTheBoard> board;
    private Map<Position, OneFigure> players;

    public void setup() {
        initializeBoard();
        initiateLightPlayer();
        initiateDarkPlayer();

    }

    public void initializeBoard() {
        for (int y = 0; y < CHECKERBOARD_SIZE; y++) {
            for (int x = 0; x < CHECKERBOARD_SIZE; x++) {
                if (y % 2 == 0 && x % 2 != 0 || y % 2 != 0 && x % 2 == 0) {
                    board.put(new Position(y, x), new Empty(1, y, x));
                } else {
                    board.put(new Position(y, x), new Empty(0, y, x));
                }
            }
        }
    }

    public void initiateDarkPlayer() {
        OneFigure dark01 = new OneFigure(1, 0, 0, new Position(2, 1));
        OneFigure dark02 = new OneFigure(1, 0, 0, new Position(2, 3));
        OneFigure dark03 = new OneFigure(1, 0, 0, new Position(2, 5));
        OneFigure dark04 = new OneFigure(1, 0, 0, new Position(2, 7));
        OneFigure dark05 = new OneFigure(1, 0, 0, new Position(1, 0));
        OneFigure dark06 = new OneFigure(1, 0, 0, new Position(1, 2));

        OneFigure dark07 = new OneFigure(1, 0, 0, new Position(1, 4));
        OneFigure dark08 = new OneFigure(1, 0, 0, new Position(1, 6));
        OneFigure dark09 = new OneFigure(1, 0, 0, new Position(0, 1));
        OneFigure dark10 = new OneFigure(1, 0, 0, new Position(0, 3));
        OneFigure dark11 = new OneFigure(1, 0, 0, new Position(0, 5));
        OneFigure dark12 = new OneFigure(1, 0, 0, new Position(0, 7));

        SixFigures black_01_06 = new SixFigures(dark01, dark02, dark03, dark04, dark05, dark06);
        SixFigures black_07_12 = new SixFigures(dark07, dark08, dark09, dark10, dark11, dark12);

        Player playerDark = new Player(
                "Mr. Dark",
                Long.parseLong(black_01_06.toString(), 2),
                Long.parseLong(black_07_12.toString(), 2)
        );

        placeFiguresFromLong(playerDark.getFirstSix());
        placeFiguresFromLong(playerDark.getLastSix());

    }

    public void initiateLightPlayer() {
        OneFigure light01 = new OneFigure(1, 0, 1, new Position(5, 0));
        OneFigure light02 = new OneFigure(1, 0, 1, new Position(5, 2));
        OneFigure light03 = new OneFigure(1, 0, 1, new Position(5, 4));
        OneFigure light04 = new OneFigure(1, 0, 1, new Position(5, 6));
        OneFigure light05 = new OneFigure(1, 0, 1, new Position(6, 1));
        OneFigure light06 = new OneFigure(1, 0, 1, new Position(6, 3));

        OneFigure light07 = new OneFigure(1, 0, 1, new Position(6, 5));
        OneFigure light08 = new OneFigure(1, 0, 1, new Position(6, 7));
        OneFigure light09 = new OneFigure(1, 0, 1, new Position(7, 0));
        OneFigure light10 = new OneFigure(1, 0, 1, new Position(7, 2));
        OneFigure light11 = new OneFigure(1, 0, 1, new Position(7, 4));
        OneFigure light12 = new OneFigure(1, 0, 1, new Position(7, 6));

        SixFigures white_01_06 = new SixFigures(light01, light02, light03, light04, light05, light06);
        SixFigures white_07_12 = new SixFigures(light07, light08, light09, light10, light11, light12);

        Player lightPlayer = new Player(
                "Mr. Light",
                Long.parseLong(white_01_06.toString(), 2),
                Long.parseLong(white_07_12.toString(), 2)
        );

        placeFiguresFromLong(lightPlayer.getFirstSix());
        placeFiguresFromLong(lightPlayer.getLastSix());
    }

    public void printMap() {
        board.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.println(e.getKey() + " = " + e.getValue()));
    }

    private void placeFiguresFromLong(long number) {
        SixFigures sixFigures = new SixFigures(number);

        OneFigure first = sixFigures.getFirst();
        OneFigure second = sixFigures.getSecond();
        OneFigure third = sixFigures.getThird();
        OneFigure fourth = sixFigures.getFourth();
        OneFigure fifth = sixFigures.getFifth();
        OneFigure sixth = sixFigures.getSixth();

        players.put(first.getPosition(), first);
        players.put(second.getPosition(), second);
        players.put(third.getPosition(), third);
        players.put(fourth.getPosition(), fourth);
        players.put(fifth.getPosition(), fifth);
        players.put(sixth.getPosition(), sixth);
    }

}
