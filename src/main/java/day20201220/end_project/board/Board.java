package day20201220.end_project.board;

import day20201220.end_project.Player;
import day20201220.end_project.figure.Empty;
import day20201220.end_project.figure.OnTheBoard;
import day20201220.end_project.figure.OneFigure;
import day20201220.end_project.figure.SixFigures;
import lombok.Getter;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

// TODO: posprawdzeć importy i może rozdzielić Const na typy stałych (eg figury oddzielnie i kolory)?
import static day20201220.end_project.utils.Const.*;

@Getter
public class Board {

    private Map<Position, OnTheBoard> board;
    private Map<Position, OneFigure> players;

    boolean capturingMandatoryByOpponent;
    boolean continueCapturingIfPossible;
    List<Position> mandatoryPosition;
    List<Position> pieceToBeRemoved;

    public Board() {
        this.board = new HashMap<>(8 * 8);
        this.players = new HashMap<>(8 * 8);

        this.capturingMandatoryByOpponent = false;
        this.continueCapturingIfPossible = false;
        this.mandatoryPosition = new ArrayList<>();
        this.pieceToBeRemoved = new ArrayList<>();
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

        AtomicInteger counter = new AtomicInteger(1);
        board.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(e -> {
            if (e.getKey().getX() == 0) System.out.print(counter);

            OneFigure oneFigure = players.get(e.getKey());
            if (oneFigure != null && oneFigure.getPosition().equals(e.getKey())) {

                // TODO: zrefaktorować to na oddzielne metody bo jest mess
                if (oneFigure.getState() == 1 && oneFigure.getFigure() == 0 && oneFigure.getColor() == 0)
                    System.out.print(" " + DARK_PAWN);
                if (oneFigure.getState() == 1 && oneFigure.getFigure() == 0 && oneFigure.getColor() == 1)
                    System.out.print(" " + LIGHT_PAWN);
                if (oneFigure.getState() == 1 && oneFigure.getFigure() == 1 && oneFigure.getColor() == 0)
                    System.out.print(" " + DARK_DAME);
                if (oneFigure.getState() == 1 && oneFigure.getFigure() == 1 && oneFigure.getColor() == 1)
                    System.out.print(" " + LIGHT_DAME);

            } else {
                System.out.print(" " + (e.getValue().getColor() == 0 ? DARK_FIELD : LIGHT_FIELD));              // !!! Here light with dark can be change on the board
//                System.out.print(" " + (e.getValue().getColor() == 0 ? LIGHT_FIELD : DARK_FIELD));              // !!! Here light with dark can be change on the board
            }

            if (e.getKey().getX() == 7) System.out.println(" " + counter.getAndIncrement());
        });

        System.out.println("  " + A + "  " + B + " " + C + "  " + D + " " + E + "  " + F + " " + G + " " + H);

        players.entrySet().stream()
                .collect(Collectors.groupingBy(e -> e.getValue().getColor(), Collectors.counting()))
                .forEach((k, v) -> System.out.printf("%s = #%2d piece(s)\n", (k == 0 ? "Light" : "Dark "), v));
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

    // TODO: add loggers
    // TODO: ustalić ograniczenie ruchu na zmianę
    public boolean movePiece(String from, String to) {   //////////////////////  MOVEMENT  ////////////////////////////////////////////////////////////////////////////////

        Position positionFrom = getPositionFromString(from);
        Position positionTo = getPositionFromString(to);

        if (capturingMandatoryByOpponent) {
//            if (!to.equalsIgnoreCase(mandatoryPosition.toString())) {
            if (!mandatoryPosition.contains(positionTo)) {
                System.out.println(RED + "ERROR:" + RESET + " This move" + RED + " was not proceed" + RESET +
                        " because capturing / continue capturing is mandatory... try again according to the " +
                        BLUE + "RULE" + RESET + " mentioned above");
                return false;
            } else {
                capturingMandatoryByOpponent = false;
                Position pieceToBeRemovedByIndexOfPositionToInMandatoryPositions = pieceToBeRemoved.get(mandatoryPosition.indexOf(positionTo));
                players.get(pieceToBeRemovedByIndexOfPositionToInMandatoryPositions).setState(0);
                players.remove(pieceToBeRemovedByIndexOfPositionToInMandatoryPositions);

                mandatoryPosition.clear();
                pieceToBeRemoved.clear();

                players.get(positionFrom).setPosition(positionTo);
                players.put(positionTo, players.get(positionFrom));
                players.remove(positionFrom);

                continueCapturingIfPossible = canContinueCapturing(positionTo);
                if (continueCapturingIfPossible) {
                    System.out.println(RED + "Continue capturing is possible!!!" + RESET);
                    capturingMandatoryByOpponent = true;
                }

            }
        } else {

            if (isPieceMovementStringInvalid(from) || isPieceMovementStringInvalid(to)) {
                System.out.println(RED + "ERROR:" + RESET + " Cannot move as direction (from or to) is/are invalid... " +
                        "double check from " + RED + from + RESET + " and " + RED + to + RESET + " values");
                return false;
            }


            if (!players.containsKey(positionFrom)) {
                System.out.println(RED + "ERROR:" + RESET + " Cannot move from " + RED + from + RESET +
                        " direction... there is no player's piece");
                return false;
            }

            // TODO: sprawdzić czy nie chce się ruszyć na białe bo poruszamy się tylko po czarnych
            if (!board.containsKey(positionTo)) {
                System.out.println(RED + "ERROR:" + RESET + " Cannot move to " + RED + to + RESET +
                        " direction... this direction does not exist on the board");
                return false;
            }
            if (players.containsKey(positionTo)) {
                System.out.println(RED + "ERROR:" + RESET + " Cannot move to " + RED + to + RESET +
                        " direction... this direction is taken by the other piece");
                return false;
            }

            int pieceColor = players.get(positionFrom).getColor();
            if (isPieceMovementInvalid(positionFrom, positionTo, pieceColor)) {
                System.out.println(RED + "ERROR:" + RESET + " this movement is not allowed and "
                        + RED + "was not proceed" + RESET + ", see above for details");
                return false;
            }

            players.get(positionFrom).setPosition(positionTo);
            players.put(positionTo, players.get(positionFrom));
            players.remove(positionFrom);


        }
        if (!continueCapturingIfPossible) {
            canBeCapturedAfterMovement(positionTo);
        }
        continueCapturingIfPossible = false;

//        System.out.println(RED + positionFrom + RESET);
//        System.out.println(RED + players.get(positionFrom) + RESET);
//        players.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(p -> System.out.println(RED + p + RESET));

//        players.get(positionFrom).setPosition(positionTo);
//        players.put(positionTo, players.get(positionFrom));
//        players.remove(positionFrom);
//        System.out.println(BLUE + "Removed - positionFrom : " + positionFrom + RESET);

        ///////aaaaa

//        capturingMandatory = isCapturingMandatory(positionTo);

//        if (continueCapturingIfPossible) {
//            continueCapturingIfPossible = false;
//            canContinueCapturing(positionTo);
//        }


        return true;
    }

    // TODO: add implementation
    private boolean isPieceMovementStringInvalid(String string) {
        return false;
    }

    private Position getPositionFromString(String string) {
        int y = Integer.parseInt(string.substring(1, 2)) - 1;
        int x = string.charAt(0) - 65;
        return new Position(y, x);
    }

    // TODO 1: rozważyć implementacje na bazie compareTo ?  TODO 2: sprawdzić ternary operator ewentualnie zamiast if
    private boolean isPieceMovementInvalid(Position oldPosition, Position newPosition, int pieceColor) {
        int oldPositionX = oldPosition.getX();
        int oldPositionY = oldPosition.getY();
        int newPositionX = newPosition.getX();
        int newPositionY = newPosition.getY();

        if (Math.abs(oldPositionX - newPositionX) != 1) {
            System.out.println(RED + "ERROR add message:" + RESET + " too far for Y axis");
            return true;
        }

        if (pieceColor == 0 && oldPositionY - newPositionY != -1) {      // Dark player
            System.out.println(RED + "ERROR add message:" + RESET + " possible reasons - too far for X axis, wrong direction or not diagonal in dark player case");
            return true;
        }

        if (pieceColor == 1 && oldPositionY - newPositionY != 1) {     // Light player
            System.out.println(RED + "ERROR add message:" + RESET + "  possible reasons - too far for X axis, wrong direction or not diagonal in light player case");
            return true;
        }

        return false;
    }

    private void canBeCapturedAfterMovement(Position justMovedTo) {
        Position positionLeftTop = new Position(justMovedTo.getY() + 1, justMovedTo.getX() - 1);
        Position positionRightTop = new Position(justMovedTo.getY() + 1, justMovedTo.getX() + 1);
        Position positionLeftBottom = new Position(justMovedTo.getY() - 1, justMovedTo.getX() - 1);
        Position positionRightBottom = new Position(justMovedTo.getY() - 1, justMovedTo.getX() + 1);
//        int playerSide = players.get(justMovedTo).getColor();
//
//        if (players.containsKey(positionLeftTop) &&
//                players.get(positionLeftTop).getColor() != playerSide &&
//                positionRightBottom.isValid() &&
//                !players.containsKey(positionRightBottom)
//        )
//        if (isCapturingAllowed(positionLeftTop, positionRightBottom, playerSide)) {
//            System.out.println(BLUE + "RULE:" + RESET + " " + (playerSide == DARK_COLOR ? "Dark" : "Light") +
//                    " player needs to move from " + BLUE + positionLeftTop + RESET +
//                    " to " + BLUE + positionRightBottom + RESET + " because capturing is mandatory");
////            capturingMandatory = true;
//            mandatoryPosition = positionRightBottom;
//            pieceToBeRemoved = justMovedTo;
//            return true;
//        }
        ifCapturingMandatoryByOpponent(positionLeftTop, positionRightBottom, justMovedTo);
        ifCapturingMandatoryByOpponent(positionRightBottom, positionLeftTop, justMovedTo);

        ifCapturingMandatoryByOpponent(positionRightTop, positionLeftBottom, justMovedTo);
        ifCapturingMandatoryByOpponent(positionLeftBottom, positionRightTop, justMovedTo);

    }

    private void ifCapturingMandatoryByOpponent(Position from, Position to, Position justMovedTo) {
        int playerSide = players.get(justMovedTo).getColor();

        if (isCapturingAllowed(from, to, playerSide)) {
            System.out.println(BLUE + "RULE:" + RESET + " " + (playerSide == DARK_COLOR ? "Dark" : "Light") +
                    " player mandatory movement from " + BLUE + from + RESET +
                    " to " + BLUE + to + RESET + " because capturing is mandatory");
            capturingMandatoryByOpponent = true;
//            continueCapturingIfPossible = true;
            mandatoryPosition.add(to);
            pieceToBeRemoved.add(justMovedTo);
        }
    }

    private boolean isCapturingAllowed(Position from, Position to, int playerSide) {
        return players.containsKey(from) &&
                players.get(from).getColor() != playerSide &&
                to.isValid() &&
                !players.containsKey(to);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean canContinueCapturing(Position position) {
        Position positionLeftTop = new Position(position.getY() + 1, position.getX() - 1);
        Position positionLeftTopDiagonal = new Position(position.getY() + 2, position.getX() - 2);

        Position positionRightTop = new Position(position.getY() + 1, position.getX() + 1);
        Position positionRightTopDiagonal = new Position(position.getY() + 2, position.getX() + 2);

        Position positionLeftBottom = new Position(position.getY() - 1, position.getX() - 1);
        Position positionLeftBottomDiagonal = new Position(position.getY() - 2, position.getX() - 2);

        Position positionRightBottom = new Position(position.getY() - 1, position.getX() + 1);
        Position positionRightBottomDiagonal = new Position(position.getY() - 2, position.getX() + 2);

//        int playerSide = players.get(justMovedTo).getColor();
//
//        if (players.containsKey(positionLeftTop) &&
//                players.get(positionLeftTop).getColor() != playerSide &&
//                positionRightBottom.isValid() &&
//                !players.containsKey(positionRightBottom)
//        )
//        if (isCapturingAllowed(positionLeftTop, positionRightBottom, playerSide)) {
//            System.out.println(BLUE + "RULE:" + RESET + " " + (playerSide == DARK_COLOR ? "Dark" : "Light") +
//                    " player needs to move from " + BLUE + positionLeftTop + RESET +
//                    " to " + BLUE + positionRightBottom + RESET + " because capturing is mandatory");
////            capturingMandatory = true;
//            mandatoryPosition = positionRightBottom;
//            pieceToBeRemoved = justMovedTo;
//            return true;
//        }
        boolean flag1 = ifContinuingCapturingMandatory(positionLeftTop, positionLeftTopDiagonal, position);
        boolean flag2 = ifContinuingCapturingMandatory(positionRightBottom, positionRightBottomDiagonal, position);

        boolean flag3 = ifContinuingCapturingMandatory(positionRightTop, positionRightTopDiagonal, position);
        boolean flag4 = ifContinuingCapturingMandatory(positionLeftBottom, positionLeftBottomDiagonal, position);

        if (flag1 || flag2 || flag3 || flag4) {
            System.out.println(RED + flag1 + ", " + flag2 + ", " + flag3 + ", " + flag4 + ", " + RESET);
            return true;
        }
        return false;
    }

    private boolean ifContinuingCapturingMandatory(Position toBeCaptured, Position positionAfterCapturing, Position justMovedTo) {
        int playerSide = players.get(justMovedTo).getColor();
        if (isCapturingAllowed(toBeCaptured, positionAfterCapturing, playerSide)) {
//            System.out.println(BLUE + "RULE:" + RESET + " " + (playerSide == DARK_COLOR ? "Dark" : "Light") +
            System.out.println(BLUE + "RULE:" + RESET + " " + (playerSide == DARK_COLOR ? "Light" : "Dark") +
                    " player is required to continue capturing... from " + BLUE + justMovedTo + RESET +
                    " to " + BLUE + positionAfterCapturing + RESET);

            mandatoryPosition.add(positionAfterCapturing);
            pieceToBeRemoved.add(toBeCaptured);
            return true;
        }
        return false;
    }

}
