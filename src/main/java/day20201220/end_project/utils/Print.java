package day20201220.end_project.utils;

import day20201220.end_project.checkers.Position;
import day20201220.end_project.figure.OnTheBoard;
import day20201220.end_project.figure.OneFigure;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static day20201220.end_project.utils.Const.*;

public class Print {

    public static void checkerboard(Map<Position, OnTheBoard> board, Map<Position, OneFigure> players) {
        printLabel();

        AtomicInteger counter = new AtomicInteger(1);
        board.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(e -> {
            if (e.getKey().getX() == 0) System.out.print(counter);

            OneFigure oneFigure = players.get(e.getKey());
            if (oneFigure != null && oneFigure.getPosition().equals(e.getKey())) {
                printPiece(oneFigure.getState(), oneFigure.getFigure(), oneFigure.getColor());
            } else {
                printField(e.getValue().getColor());
            }
            if (e.getKey().getX() == 7) System.out.println(" " + counter.getAndIncrement());
        });

        printLabel();

        long lightPieces = countPieces(players, 1);
        long darkPieces = countPieces(players, 0);
        Messages.printPieces(lightPieces, darkPieces);

        if (lightPieces == 0 || darkPieces == 0) {
            System.out.println(Messages.ruleWin(lightPieces));
        }
    }

    private static long countPieces(Map<Position, OneFigure> players, int darkOrLight) {
        return players.entrySet().stream().filter(p -> p.getValue().getColor() == darkOrLight).count();
    }

    private static void printPiece (int state, int figure, int color) {
        if (state == 1 && figure == 0 && color == 0)
            System.out.print(" " + DARK_PAWN);
        if (state == 1 && figure == 0 && color == 1)
            System.out.print(" " + LIGHT_PAWN);
        if (state == 1 && figure == 1 && color == 0)
            System.out.print(" " + DARK_DAME);
        if (state == 1 && figure == 1 && color == 1)
            System.out.print(" " + LIGHT_DAME);
    }

    private static void printField(int color) {
        System.out.print(" " + (color == 0 ? DARK_FIELD : LIGHT_FIELD));
    }

    private static void printLabel () {
        System.out.println("  " + A + "  " + B + " " + C + "  " + D + " " + E + "  " + F + " " + G + " " + H);
    }

}
