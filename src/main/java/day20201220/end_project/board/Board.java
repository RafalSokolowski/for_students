package day20201220.end_project.board;

import day20201220.end_project.Player;
import day20201220.end_project.figure.Empty;
import day20201220.end_project.figure.OnTheBoard;
import day20201220.end_project.figure.OneFigure;
import day20201220.end_project.figure.SixFigures;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static day20201106.Const.*;

@Getter
public class Board {

    private Map<Position, OnTheBoard> board;
    private Map<Position, OneFigure> players;

    public Board() {
        this.board = new HashMap<>(8 * 8);
        this.players = new HashMap<>(8 * 8);
    }

    public void initializeBoard() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (y % 2 == 0 && x % 2 != 0 || y % 2 != 0 && x % 2 == 0) {
                    board.put(new Position(y, x), new Empty(1, y, x));
//                    System.out.print(" " + WHITE_FIELD);
                } else {
                    board.put(new Position(y, x), new Empty(0, y, x));
//                    System.out.print(" " + BLACK_FIELD);
                }
            }
        }
    }

    // TODO: zrefaktorować do stałych bo poczatkowy układ jest zawsze taki sam
    public Player initializeBlackPlayers() {
        OneFigure black01 = new OneFigure(1, 0, 0, new Position(2, 1));
        OneFigure black02 = new OneFigure(1, 0, 0, new Position(2, 3));
        OneFigure black03 = new OneFigure(1, 0, 0, new Position(2, 5));
        OneFigure black04 = new OneFigure(1, 0, 0, new Position(2, 7));
        OneFigure black05 = new OneFigure(1, 0, 0, new Position(1, 0));
        OneFigure black06 = new OneFigure(1, 0, 0, new Position(1, 2));

        OneFigure black07 = new OneFigure(1, 0, 0, new Position(1, 4));
        OneFigure black08 = new OneFigure(1, 0, 0, new Position(1, 6));
        OneFigure black09 = new OneFigure(1, 0, 0, new Position(0, 1));
        OneFigure black10 = new OneFigure(1, 0, 0, new Position(0, 3));
        OneFigure black11 = new OneFigure(1, 0, 0, new Position(0, 5));
        OneFigure black12 = new OneFigure(1, 0, 0, new Position(0, 7));

        SixFigures black_01_06 = new SixFigures(black01, black02, black03, black04, black05, black06);
        SixFigures black_07_12 = new SixFigures(black07, black08, black09, black10, black11, black12);

        Player playerBlack = new Player(
                "Mr. Black",
                Long.parseLong(black_01_06.toString(), 2),
                Long.parseLong(black_07_12.toString(), 2)
        );

        placeFiguresFromLong(playerBlack.getFirstSix());
        placeFiguresFromLong(playerBlack.getLastSix());

        return playerBlack;
    }

    // TODO: zrefaktorować do stałych bo poczatkowy układ jest zawsze taki sam
    public Player initializeWhitePlayer() {
        OneFigure white01 = new OneFigure(1, 0, 1, new Position(5, 0));
        OneFigure white02 = new OneFigure(1, 0, 1, new Position(5, 2));
        OneFigure white03 = new OneFigure(1, 0, 1, new Position(5, 4));
        OneFigure white04 = new OneFigure(1, 0, 1, new Position(5, 6));
        OneFigure white05 = new OneFigure(1, 0, 1, new Position(6, 1));
        OneFigure white06 = new OneFigure(1, 0, 1, new Position(6, 3));

        OneFigure white07 = new OneFigure(1, 0, 1, new Position(6, 5));
        OneFigure white08 = new OneFigure(1, 0, 1, new Position(6, 7));
        OneFigure white09 = new OneFigure(1, 0, 1, new Position(7, 0));
        OneFigure white10 = new OneFigure(1, 0, 1, new Position(7, 2));
        OneFigure white11 = new OneFigure(1, 0, 1, new Position(7, 4));
        OneFigure white12 = new OneFigure(1, 0, 1, new Position(7, 6));

        SixFigures white_01_06 = new SixFigures(white01, white02, white03, white04, white05, white06);
        SixFigures white_07_12 = new SixFigures(white07, white08, white09, white10, white11, white12);

        Player playerWhite = new Player(
                "Mr. White",
                Long.parseLong(white_01_06.toString(), 2),
                Long.parseLong(white_07_12.toString(), 2)
        );

        placeFiguresFromLong(playerWhite.getFirstSix());
        placeFiguresFromLong(playerWhite.getLastSix());

        return playerWhite;
    }

    public void printMap() {
        board.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(e -> {
            System.out.println(e.getKey() + " = " + e.getValue());
        });
    }

    public void printBoard() {
        System.out.println("  " + A + "  " + B + " " + C + "  " + D + " " + E + "  " + F + " " + G + " " + H);

        AtomicInteger counter = new AtomicInteger(8);
        board.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(e -> {
            if (e.getKey().getX() == 0) System.out.print(counter);

            OneFigure oneFigure = players.get(e.getKey());
            if (oneFigure != null && oneFigure.getPosition().equals(e.getKey())) {

                // TODO: zrefaktorować to na oddzielne metody bo jest mess
                if (oneFigure.getState() == 1 && oneFigure.getFigure() == 0 && oneFigure.getColor() == 0)
                    System.out.print(" " + BLACK_FIGURE);
                if (oneFigure.getState() == 1 && oneFigure.getFigure() == 0 && oneFigure.getColor() == 1)
                    System.out.print(" " + WHITE_FIGURE);
                if (oneFigure.getState() == 1 && oneFigure.getFigure() == 1 && oneFigure.getColor() == 0)
                    System.out.print(" " + BLACK_QUEEN);
                if (oneFigure.getState() == 1 && oneFigure.getFigure() == 1 && oneFigure.getColor() == 1)
                    System.out.print(" " + WHITE_QUEEN);

            } else {
                System.out.print(" " + (e.getValue().getColor() == 0 ? BLACK_FIELD : WHITE_FIELD));
            }

            if (e.getKey().getX() == 7) System.out.println(" " + counter.getAndDecrement());
        });

        System.out.println("  " + A + "  " + B + " " + C + "  " + D + " " + E + "  " + F + " " + G + " " + H);
    }

//    public void print() {
//
//        System.out.println("  " + A + " " + B + " " + C + " " + D + " " + E + " " + F + " " + G + " " + H);
//        for (int i = 0; i < 8; i++) {
//            System.out.print(8 - i);
//            for (int j = 0; j < 8; j++) {
//                if (i % 2 == 0 && j % 2 != 0 || i % 2 != 0 && j % 2 == 0) {
//                    System.out.print(" " + WHITE_FIELD);
//                } else {
//                    System.out.print(" " + BLACK_FIELD);
//                }
//            }
//            System.out.println(" " + (8 - i));
//        }
//        System.out.println("  " + A + " " + B + " " + C + " " + D + " " + E + " " + F + " " + G + " " + H);
//    }

//    public Map<Position, OnTheBoard> sort (Map<Position, OnTheBoard> map) {
//        return map.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap());
//    }

    public void placeFiguresFromLong(long number) {
        SixFigures sixFigures = new SixFigures(number);

        OneFigure first = sixFigures.getFirst();        // 100 011 001 -> 3,1
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
