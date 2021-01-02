package day20201220.end_project.board;

import day20201220.end_project.Player;
import day20201220.end_project.figure.Empty;
import day20201220.end_project.figure.OnTheBoard;
import day20201220.end_project.figure.OneFigure;
import day20201220.end_project.figure.SixFigures;
import javafx.geometry.Pos;
import lombok.Getter;

import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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
                } else {
                    board.put(new Position(y, x), new Empty(0, y, x));
                }
            }
        }
    }

    // TODO: zrefaktorować do stałych bo poczatkowy układ jest zawsze taki sam
    public Player initiateDarkPlayer() {
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

        return playerDark;
    }

    // TODO: zrefaktorować do stałych bo poczatkowy układ jest zawsze taki sam
    public Player initiateLightPlayer() {
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

        return lightPlayer;
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
//              System.out.print(" " + (e.getValue().getColor() == 0 ? LIGHT_FIELD : DARK_FIELD));              // !!! Here light with dark can be change on the board
            }

            if (e.getKey().getX() == 7) System.out.println(" " + counter.getAndIncrement());
        });

        System.out.println("  " + A + "  " + B + " " + C + "  " + D + " " + E + "  " + F + " " + G + " " + H);

        players.entrySet().stream()
                .collect(Collectors.groupingBy(e -> e.getValue().getColor(), Collectors.counting()))
                .forEach((k, v) -> System.out.printf("%s = #%2d piece(s)\n", (k == 0 ? "Dark " : "Light"), v));
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

    //////////////////////  MOVEMENT - try new approach  ///////////////////////////////////////////////////////////////////////////////////////

    public boolean movePieceByString_(String stringFrom, String stringTo) {

        if (areStringMoveInvalid(stringFrom, stringTo)) {
            System.out.println(RED + "ERROR:" + RESET + " Cannot move as direction (from or to) is/are not valid... " +
                    "double check from " + RED + stringFrom + RESET + " and to " + RED + stringTo + RESET + " values");
            return false;
        }

        Position positionFrom = getPositionFromString(stringFrom);
        Position positionTo = getPositionFromString(stringTo);
        if (isMovementPositionsAreNotCorrect(positionFrom, positionTo)) {
            return false;
        }

        OneFigure piece = players.get(positionFrom);

        if (isDameMoving(piece.getFigure())) {
            System.out.println(RED + "Dame is moving - implement that :)");
            // Dame is moving
        } else {
            movePawnByPosition(positionFrom, positionTo);   // Pawn is moving
        }


        return true;
    }

    public boolean movePawnByPosition(Position positionFrom, Position positionTo) {
        if (capturingMandatoryByOpponent) {
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

                updatePlayersMapAndStatus(positionFrom, positionTo);

                continueCapturingIfPossible = canContinueCapturing(positionTo, PAWN);
                if (continueCapturingIfPossible) {
                    capturingMandatoryByOpponent = true;
                }
            }
        } else {
            if (isMovementPositionsAreNotCorrect(positionFrom, positionTo)) {
                return false;
            }
            updatePlayersMapAndStatus(positionFrom, positionTo);
        }

        if (!continueCapturingIfPossible) {
            canBeCapturedAfterMovement(positionTo);
        }
        continueCapturingIfPossible = false;

        OneFigure piece = players.get(positionTo);
        if (shouldWeChangePieceToDame(piece.getColor(), piece.getPosition().getY())) {
            String playersColor = piece.getColor() == DARK ? "Dark" : "Light";
            System.out.println(BLUE + "RULE:" + RESET + " " + playersColor +
                    " player piece reached the crownhead and " + BLUE + "becomes the Dame" + RESET +
                    "... congratulation " + playersColor + " player");
            changePawnToDame(piece);
        }
        return true;
    }


    //////////////////////  MOVEMENT - old working approach ///////////////////////////////////////////////////////////////////////////////////////

    // TODO: add loggers
    // TODO: ustalić ograniczenie ruchu na zmianę
    public boolean movePieceByString(String stringFrom, String stringTo) {
        Position positionFrom = getPositionFromString(stringFrom);
        Position positionTo = getPositionFromString(stringTo);

//        System.out.println(RED + positionFrom + RESET);
//        System.out.println(RED + players.get(positionFrom) + RESET);
//        System.out.println(RED + players.get(positionFrom).getColor() + RESET);
//        System.out.println(RED + (players.get(positionFrom).getColor() == DARK_COLOR ? "Dark" : "Light") + RESET);

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

                updatePlayersMapAndStatus(positionFrom, positionTo);
//                players.get(positionFrom).setPosition(positionTo);
//                players.put(positionTo, players.get(positionFrom));
//                players.remove(positionFrom);

                continueCapturingIfPossible = canContinueCapturing(positionTo, PAWN);
                if (continueCapturingIfPossible) {
                    System.out.println(RED + "Continue capturing is possible!!!" + RESET);
                    capturingMandatoryByOpponent = true;
                }

            }
        } else {

            if (isMovementConditionsNotCorrect(stringFrom, stringTo)) {
                return false;
            }

//            if (areStringMoveInvalid(stringFrom, stringTo)) {
//                System.out.println(RED + "ERROR:" + RESET + " Cannot move as direction (from or to) is/are not valid... " +
//                        "double check from " + RED + stringFrom + RESET + " and to " + RED + stringTo + RESET + " values");
//                return false;
//            }
//
//            if (ifPieceFromNotOnTheBoard(positionFrom)) {
//                System.out.println(RED + "ERROR:" + RESET + " Cannot move from " + RED + stringFrom + RESET +
//                        " direction... there is no player's piece");
//                return false;
//            }
//
//            if (isPositionNotExists(positionTo)) {
//                System.out.println(RED + "ERROR:" + RESET + " Cannot move to " + RED + stringTo + RESET +
//                        " direction... this direction does not exist on the board");
//                return false;
//            }
//            if (isPositionTakenByOtherPiece(positionTo)) {
//                System.out.println(RED + "ERROR:" + RESET + " Cannot move to " + RED + stringTo + RESET +
//                        " direction... this direction is taken by the other piece");
//                return false;
//            }
//
//            int pieceColor = players.get(positionFrom).getColor();
//            if (isPieceMovementInvalid(positionFrom, positionTo, pieceColor)) {
//                System.out.println(RED + "ERROR:" + RESET + " this movement is not allowed and "
//                        + RED + "was not proceed" + RESET + ", see above for details");
//                return false;
//            }

            updatePlayersMapAndStatus(positionFrom, positionTo);

//            players.get(positionFrom).setPosition(positionTo);
//            players.put(positionTo, players.get(positionFrom));
//            players.remove(positionFrom);

        }
        if (!continueCapturingIfPossible) {
            canBeCapturedAfterMovement(positionTo);
        }
        continueCapturingIfPossible = false;

        OneFigure piece = players.get(positionTo);
        if (shouldWeChangePieceToDame(piece.getColor(), piece.getPosition().getY())) {
            String playersColor = piece.getColor() == DARK ? "Dark" : "Light";
            System.out.println(BLUE + "RULE:" + RESET + " " + playersColor +
                    " player piece reached the crownhead and " + BLUE + "becomes the Dame" + RESET +
                    "... congratulation " + playersColor + " player");
            changePawnToDame(piece);
        }

//        System.out.println(RED + positionTo + RESET);
//        System.out.println(RED + players.get(positionTo) + RESET);
//        System.out.println(RED + players.get(positionTo).getColor() + RESET);
//        System.out.println(RED + (players.get(positionTo).getColor() == DARK_COLOR ? "Dark" : "Light") + RESET);

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

/////////////////////////////////////////////////////// CHECK CONDITIONS /////////////////////////////////////////////////////////////////

    private boolean isMovementConditionsNotCorrect(String stringFrom, String stringTo) {
        if (areStringMoveInvalid(stringFrom, stringTo)) {
            System.out.println(RED + "ERROR:" + RESET + " Cannot move as direction (from or to) is/are not valid... " +
                    "double check from " + RED + stringFrom + RESET + " and to " + RED + stringTo + RESET + " values");
            return true;
        }

        Position positionFrom = getPositionFromString(stringFrom);
        Position positionTo = getPositionFromString(stringTo);

//        if (ifPieceFromNotOnTheBoard(positionFrom)) {
//            System.out.println(RED + "ERROR:" + RESET + " Cannot move from " + RED + stringFrom + RESET +
//                    " direction... there is no player's piece");
//            return true;
//        }
//
//        if (isPositionNotExists(positionTo)) {
//            System.out.println(RED + "ERROR:" + RESET + " Cannot move to " + RED + stringTo + RESET +
//                    " direction... this direction does not exist on the board");
//            return true;
//        }
//        if (isPositionTakenByOtherPiece(positionTo)) {
//            System.out.println(RED + "ERROR:" + RESET + " Cannot move to " + RED + stringTo + RESET +
//                    " direction... this direction is taken by the other piece");
//            return true;
//        }
//
//        int pieceColor = players.get(positionFrom).getColor();
//        if (isPieceMovementInvalid(positionFrom, positionTo, pieceColor)) {
//            System.out.println(RED + "ERROR:" + RESET + " this movement is not allowed and "
//                    + RED + "was not proceed" + RESET + ", see above for details");
//            return true;
//        }
        if (isMovementPositionsAreNotCorrect(positionFrom, positionTo)) {
            return true;
        }

        return false;
    }

    private boolean isMovementPositionsAreNotCorrect(Position positionFrom, Position positionTo) {
        if (positionFrom.equals(positionTo)) {
            System.out.println(RED + "ERROR add message:" + RESET + " you are not moving... positions form and to are equal");
            return true;
        }

        if (ifPieceFromNotOnTheBoard(positionFrom)) {
            System.out.println(RED + "ERROR:" + RESET + " Cannot move from " + RED + positionFrom + RESET +
                    " direction... there is no player's piece");
            return true;
        }

        if (isPositionNotExists(positionTo)) {
            System.out.println(RED + "ERROR:" + RESET + " Cannot move to " + RED + positionTo + RESET +
                    " direction... this direction does not exist on the board");
            return true;
        }
        if (isPositionTakenByOtherPiece(positionTo)) {
            System.out.println(RED + "ERROR:" + RESET + " Cannot move to " + RED + positionTo + RESET +
                    " direction... this direction is taken by the other piece");
            return true;
        }

        OneFigure piece = players.get(positionFrom);
        if (isPieceMovementInvalid(positionFrom, positionTo, piece)) {
            System.out.println(RED + "ERROR:" + RESET + " this movement is not allowed and "
                    + RED + "was not proceed" + RESET + ", see above for details");
            return true;
        }
        return false;
    }

    private boolean areStringMoveInvalid(String stringFrom, String stringTo) {
        return isPieceMovementStringInvalid(stringFrom) || isPieceMovementStringInvalid(stringTo);
    }

    // TODO: add implementation
    private boolean isPieceMovementStringInvalid(String string) {
        return false;
    }

    private boolean ifPieceFromNotOnTheBoard(Position positionFrom) {
        return !players.containsKey(positionFrom);
    }

    private Position getPositionFromString(String string) {
        int y = Integer.parseInt(string.substring(1, 2)) - 1;
        int x = string.charAt(0) - 65;
        return new Position(y, x);
    }

    /**
     * Check whether
     * (1) movement in Y axis is not too far (more than one)
     * (2) movement in X axis is not too far (more than one) and not diagonal (pieces can move only on diagonals)
     * (3) above (ie 2) for Dark and Light player separately
     */
    // TODO 1: rozważyć implementacje na bazie compareTo ?  TODO 2: sprawdzić ternary operator ewentualnie zamiast if
    private boolean isPieceMovementInvalid(Position positionFrom, Position positionTo, OneFigure piece) {


        int pawnOrDame = piece.getFigure();
        int color = piece.getColor();

        return pawnOrDame == PAWN ?
                isPawnMovementInvalid(positionFrom, positionTo, color) :
                isDameMovementInvalid(positionFrom, positionTo);

//        if (pawnOrDame == PAWN) {
//            return isPawnMovementInvalid(positionFrom, positionTo, color);    // Pawn is moving
//        } else {
//            return isDameMovementInvalid(positionFrom, positionTo);           // Dame is moving
//        }

    }

    private boolean isPawnMovementInvalid(Position positionFrom, Position positionTo, int pieceColor) {
        int oldPositionX = positionFrom.getX();
        int oldPositionY = positionFrom.getY();
        int newPositionX = positionTo.getX();
        int newPositionY = positionTo.getY();
        if (Math.abs(oldPositionX - newPositionX) != 1) {
            System.out.println(RED + "ERROR add message:" + RESET + " too far for Y axis");
            return true;
        }

        if (pieceColor == 0 && oldPositionY - newPositionY != -1) {      // Dark pawn
            System.out.println(RED + "ERROR add message:" + RESET + " possible reasons - too far for X axis, wrong direction or not diagonal in dark player case");
            return true;
        }

        if (pieceColor == 1 && oldPositionY - newPositionY != 1) {     // Light pawn
            System.out.println(RED + "ERROR add message:" + RESET + "  possible reasons - too far for X axis, wrong direction or not diagonal in light player case");
            return true;
        }

        return false;
    }

    private boolean isDameMovementInvalid(Position positionFrom, Position positionTo) {
        int oldPositionX = positionFrom.getX();
        int oldPositionY = positionFrom.getY();
        int newPositionX = positionTo.getX();
        int newPositionY = positionTo.getY();

        if (!(newPositionX % 2 == 0 && newPositionY % 2 != 0 || newPositionX % 2 != 0 && newPositionY % 2 == 0)) {
            System.out.println(RED + "ERROR add message:" + RESET + " possible reasons - dame movement is not diagonal");
            return true;
        }

        if (Math.abs(oldPositionX - newPositionX) != Math.abs(oldPositionY - newPositionY)) {
            System.out.println(RED + "ERROR add message:" + RESET + " possible reasons - dame jumped over too many diagonal line(s) ");
            return true;
        }

        return false;
//        if (Math.abs(oldPositionX - newPositionX) <= 8) {
//            System.out.println(RED + "ERROR add message:" + RESET + " too far for Y axis");
//            return true;
//        }

//        if (pieceColor == 0 && oldPositionY - newPositionY != -1) {      // Dark pawn
//            System.out.println(RED + "ERROR add message:" + RESET + " possible reasons - too far for X axis, wrong direction or not diagonal in dark player case");
//            return true;
//        }

//        if (pieceColor == 1 && oldPositionY - newPositionY != 1) {     // Light pawn
//            System.out.println(RED + "ERROR add message:" + RESET + "  possible reasons - too far for X axis, wrong direction or not diagonal in light player case");
//            return true;
//        }
//
//        return false;
    }

    private boolean isPositionNotExists(Position position) {
        return !board.containsKey(position);
    }

    private boolean isPositionTakenByOtherPiece(Position position) {
        return players.containsKey(position);
    }

////////////////////////////////////////////// CHECK CAPTURING BY THE OPPONENT AFTER MOVEMENT ////////////////////////////////

    private void canBeCapturedAfterMovement(Position justMovedTo) {

        int playerColor = players.get(justMovedTo).getColor();
        String opponentColor = playerColor == DARK ? "Light" : "Dark";
        Map<Position, OneFigure> opponentDames = getOpponentDames(playerColor);

        if (!opponentDames.isEmpty()) {
            opponentDames.forEach((k, v) -> {
                List<Position> emptyFieldsAfter = getEmptyFieldsAfter(k, justMovedTo);
                getEmptyFieldsAfterByPossibleCapturing(k, justMovedTo);

                if (arePositionsOnTheSameDiagonal(justMovedTo, k) && noOtherPiecesInBetween(justMovedTo, k) && !emptyFieldsAfter.isEmpty()) {

                    mandatoryPosition = emptyFieldsAfter;
                    pieceToBeRemoved.add(justMovedTo);
                    capturingMandatoryByOpponent = true;

                    mandatoryPosition.forEach(p -> System.out.println(BLUE + "RULE:" + RESET + " " + opponentColor +
                            " player Dame mandatory movement from " + BLUE + k + RESET + " to " + BLUE + p + RESET +
                            " because capturing is mandatory"));

//                    System.out.println(RED + getFieldsInBetween(k, justMovedTo) + RESET);


//                    mandatoryPosition.clear();
                }
            });
        }

        canBeCapturedAfterMovementByPawn(justMovedTo);

//        Position positionLeftTop = new Position(justMovedTo.getY() + 1, justMovedTo.getX() - 1);
//        Position positionRightTop = new Position(justMovedTo.getY() + 1, justMovedTo.getX() + 1);
//        Position positionLeftBottom = new Position(justMovedTo.getY() - 1, justMovedTo.getX() - 1);
//        Position positionRightBottom = new Position(justMovedTo.getY() - 1, justMovedTo.getX() + 1);
////        int playerSide = players.get(justMovedTo).getColor();
////
////        if (players.containsKey(positionLeftTop) &&
////                players.get(positionLeftTop).getColor() != playerSide &&
////                positionRightBottom.isValid() &&
////                !players.containsKey(positionRightBottom)
////        )
////        if (isCapturingAllowed(positionLeftTop, positionRightBottom, playerSide)) {
////            System.out.println(BLUE + "RULE:" + RESET + " " + (playerSide == DARK_COLOR ? "Dark" : "Light") +
////                    " player needs to move from " + BLUE + positionLeftTop + RESET +
////                    " to " + BLUE + positionRightBottom + RESET + " because capturing is mandatory");
//////            capturingMandatory = true;
////            mandatoryPosition = positionRightBottom;
////            pieceToBeRemoved = justMovedTo;
////            return true;
////        }
//        ifCapturingMandatoryByOpponent(positionLeftTop, positionRightBottom, justMovedTo);
//        ifCapturingMandatoryByOpponent(positionRightBottom, positionLeftTop, justMovedTo);
//
//        ifCapturingMandatoryByOpponent(positionRightTop, positionLeftBottom, justMovedTo);
//        ifCapturingMandatoryByOpponent(positionLeftBottom, positionRightTop, justMovedTo);
    }

    private boolean arePositionsOnTheSameDiagonal(Position positionOne, Position positionTwo) {
        int oneX = positionOne.getX();
        int oneY = positionOne.getY();
        int twoX = positionTwo.getX();
        int twoY = positionTwo.getY();

//        if (twoX % 2 == 0 && twoY % 2 != 0 || twoX % 2 != 0 && twoY % 2 == 0) {
//            return true;
//        }

        if (Math.abs(oneX - twoX) == Math.abs(oneY - twoY)) {
            return true;
        }

        return false;
    }

    private boolean noOtherPiecesInBetween(Position positionOne, Position positionTwo) {
        List<Position> piecesInBetween = getFieldsInBetween(positionOne, positionTwo);
        for (Position p : piecesInBetween) {
            if (players.containsKey(p)) return false;
        }
        return true;
    }

    private List<Position> getFieldsInBetween(Position positionOne, Position positionTwo) {
        int oneX = positionOne.getX();
        int oneY = positionOne.getY();
        int twoX = positionTwo.getX();
        int twoY = positionTwo.getY();

        int deltaX = Math.abs(oneX - twoX);
        int deltaY = Math.abs(oneY - twoY);

        if (deltaX != deltaY) {
            System.out.println(RED + "INTERNAL MESSAGE: problem with diagonal distance in getFieldsInBetween (to be removed in production version)" + RESET);
            return Collections.emptyList();
        }

        if (deltaX <= 1) {
            System.out.println("INTERNAL MESSAGE: no empty fields in between (to be removed in production version)");
            return Collections.emptyList();
        }

        List<Position> result = new ArrayList<>(deltaX - 1);
        while (deltaX-- > 1) {
            result.add(new Position(
                    oneY > twoY ? oneY - deltaX : oneY + deltaX,
                    oneX > twoX ? oneX - deltaX : oneX + deltaX
            ));
        }

        return result;
    }

    private List<Position> getEmptyFieldsAfterByPossibleCapturing(Position positionDame, Position justMovedTo) {
        List<Position> emptyFieldsAfter = getEmptyFieldsAfter(positionDame, justMovedTo);

        emptyFieldsAfter.forEach(p -> {
            if (canContinueCapturing(p, DAME)) {
                System.out.println("Can continue capturing after dame movement: " + RED + p + RESET);
            }
        });

        return Collections.emptyList();
    }

    private List<Position> getEmptyFieldsAfter(Position positionDame, Position justMovedTo) {
//        if (positionAfter.isEmpty()) {
//            System.out.println(RED + "INTERNAL MESSAGE: problem with getEmptyFieldsAfter, should not be empty (to be removed in production version)" + RESET);
//            return Collections.emptyList();
//        }
        int justMovedToX = justMovedTo.getX();
        int justMovedToY = justMovedTo.getY();
        int positionDameX = positionDame.getX();
        int positionDameY = positionDame.getY();

        int deltaX = justMovedToX > positionDameX ? 1 : -1;
        int deltaY = justMovedToY > positionDameY ? 1 : -1;

        List<Position> result = new ArrayList<>();
        Position positionAfter = new Position(justMovedToY + deltaY, justMovedToX + deltaX);

        while (!players.containsKey(positionAfter) && positionAfter.isValid()) {
            result.add(positionAfter);
            positionAfter = new Position(
                    positionAfter.getY() + deltaY,
                    positionAfter.getX() + deltaX
            );
        }

        return result;
    }

    private void canBeCapturedAfterMovementByPawn(Position justMovedTo) {
        Position positionLeftTop = new Position(justMovedTo.getY() + 1, justMovedTo.getX() - 1);
        Position positionRightTop = new Position(justMovedTo.getY() + 1, justMovedTo.getX() + 1);
        Position positionLeftBottom = new Position(justMovedTo.getY() - 1, justMovedTo.getX() - 1);
        Position positionRightBottom = new Position(justMovedTo.getY() - 1, justMovedTo.getX() + 1);

        ifCapturingMandatoryByOpponent(positionLeftTop, positionRightBottom, justMovedTo);
        ifCapturingMandatoryByOpponent(positionRightBottom, positionLeftTop, justMovedTo);
        ifCapturingMandatoryByOpponent(positionRightTop, positionLeftBottom, justMovedTo);
        ifCapturingMandatoryByOpponent(positionLeftBottom, positionRightTop, justMovedTo);
    }

    private void ifCapturingMandatoryByOpponent(Position positionFrom, Position positionTo, Position justMovedTo) {
        int playerColor = players.get(justMovedTo).getColor();

        if (isCapturingAllowed(positionFrom, positionTo, playerColor)) {
            // as the opponent is capturing then justMoveTo is to be captured and playerColor needs to be switch
            System.out.println(BLUE + "RULE:" + RESET + " " + (playerColor == DARK ? "Light" : "Dark") +
                    " player mandatory movement from " + BLUE + positionFrom + RESET +
                    " to " + BLUE + positionTo + RESET + " because capturing is mandatory");
            capturingMandatoryByOpponent = true;
            mandatoryPosition.add(positionTo);
            pieceToBeRemoved.add(justMovedTo);
        }
    }

    private boolean isCapturingAllowed(Position from, Position to, int playerSide) {
        return players.containsKey(from) &&
                players.get(from).getColor() != playerSide &&
                to.isValid() &&
                !players.containsKey(to);
    }

///////////////////////////////////////// CAN CONTINUE CAPTURING AFTER MOVEMENT ////////////////////////////////////////////////////

    private boolean canContinueCapturing(Position position, int pawnOrDame) {

        if (isDameMoving(pawnOrDame)) {
            System.out.println(RED + "DAME IS MOVING" + RESET);



            return true;

        } else {
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

//            if (flag1 || flag2 || flag3 || flag4) {
//                System.out.println(RED + flag1 + ", " + flag2 + ", " + flag3 + ", " + flag4 + ", " + RESET);
//                return true;
//            }
//            return false;
            return flag1 || flag2 || flag3 || flag4;

        }
    }

//    private List<Position> getDiagonals (Position position) {
//
//    }

    private boolean ifContinuingCapturingMandatory(Position toBeCaptured, Position positionAfterCapturing, Position justMovedTo) {
        int playerSide = players.get(justMovedTo).getColor();
        if (isCapturingAllowed(toBeCaptured, positionAfterCapturing, playerSide)) {
            System.out.println(BLUE + "RULE:" + RESET + " " + (playerSide == DARK ? "Dark" : "Light") +
                    " player is required to continue capturing... from " + BLUE + justMovedTo + RESET +
                    " to " + BLUE + positionAfterCapturing + RESET);

            mandatoryPosition.add(positionAfterCapturing);
            pieceToBeRemoved.add(toBeCaptured);
            return true;
        }
        return false;
    }

///////////////////////////////////////////// CHANGE PIECE TO DAME ///////////////////////////////////////////////////////////

    private boolean shouldWeChangePieceToDame(int pieceColor, int pieceAxisY) {
        if (pieceColor == DARK && pieceAxisY == 7) return true;
        if (pieceColor == LIGHT && pieceAxisY == 0) return true;
        return false;
    }

    // TODO: rozważyć falgę mówiącą że dany gracz ma damkę ... ale potem jakoś kaowanie tej flagi jak wszystkie damki danego gracza zginą
    private void changePawnToDame(OneFigure piece) {
        piece.setFigure(DAME);
//        System.out.println(RED+piece+RESET);
    }


//////////////////////////////////////// DAME /////////////////////////////////////////////////////////////////////////////////

    private boolean isDameMoving(int playerPiece) {
        return playerPiece == DAME;
    }

    private boolean isOpponentHasDame(int playerColor) {
        return players.entrySet().stream()
                .anyMatch(e -> e.getValue().getFigure() == DAME && e.getValue().getColor() != playerColor);
    }

    private Map<Position, OneFigure> getOpponentDames(int playerColor) {
        return players.entrySet().stream()
                .filter(e -> e.getValue().getFigure() == DAME && e.getValue().getColor() != playerColor)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void updatePlayersMapAndStatus(Position positionFrom, Position positionTo) {
        players.get(positionFrom).setPosition(positionTo);
        players.put(positionTo, players.get(positionFrom));
        players.remove(positionFrom);
    }

}



