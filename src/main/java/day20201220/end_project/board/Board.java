package day20201220.end_project.board;

import day20201220.end_project.Player;
import day20201220.end_project.figure.Empty;
import day20201220.end_project.figure.OnTheBoard;
import day20201220.end_project.figure.OneFigure;
import day20201220.end_project.figure.SixFigures;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

// TODO: posprawdzeć importy i może rozdzielić Const na typy stałych (eg figury oddzielnie i kolory)?
import static day20201220.end_project.utils.Const.*;

@Getter
public class Board {

    private Map<Position, OnTheBoard> board;
    private Map<Position, OneFigure> players;

    private boolean capturingMandatoryByOpponent;
    private boolean continueCapturingIfPossible;
    private List<Position> mandatoryPositions;
    private List<Position> piecesToBeRemoved;

    //    private Map<Position, List<Position>> toBeRemoved_MandatoryPositions;
    private Map<Position, Map<Position, List<Position>>> positionsFrom_ToBeRemoved_MandatoryPositionsTo;
    private int darkOrLight;
    private boolean hasCapturedInPreviosuRound;

    public Board() {
        this.board = new HashMap<>(8 * 8);
        this.players = new HashMap<>(8 * 8);

        this.capturingMandatoryByOpponent = false;
        this.continueCapturingIfPossible = false;
        this.mandatoryPositions = new ArrayList<>();
        this.piecesToBeRemoved = new ArrayList<>();

//        this.toBeRemoved_MandatoryPositions = new HashMap<>();
        this.positionsFrom_ToBeRemoved_MandatoryPositionsTo = new HashMap<>();
        darkOrLight = 1;
        this.hasCapturedInPreviosuRound = false;
    }

    ////////////////////////////////////////////// INITIALIZATION ////////////////////////////////////////////////////////////

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

    //////////////////////  MOVEMENT - try new approach with listener //////////////////////////////////////////////////////////////////////////////////////////////

    public boolean movePieceByString(String stringFrom, String stringTo) {

        Position positionFrom = getPositionFromString(stringFrom);
        Position positionTo = getPositionFromString(stringTo);


        // Check whether there are any mandatory movements
//        while (!positionsFrom_ToBeRemoved_MandatoryPositionsTo.isEmpty() || hasJustCaptured) {
        if (!positionsFrom_ToBeRemoved_MandatoryPositionsTo.isEmpty()) {


            if (!positionsFrom_ToBeRemoved_MandatoryPositionsTo.containsKey(positionFrom)) {
                System.out.println(RED + "ERROR:" + RESET + " This move was not proceed" + RED +
                        " (incorrect position from - " + positionFrom + ")" + RESET +
                        " because capturing / continue capturing is mandatory... try again according to the " +
                        BLUE + "RULE" + RESET + " mentioned above");
                return false;
            }

            List<Position> allMandatoryPositions = positionsFrom_ToBeRemoved_MandatoryPositionsTo
                    .get(positionFrom).values()
                    .stream().flatMap(Collection::stream)
                    .collect(Collectors.toList());

//            System.out.println();
//            System.out.println("1.                 PositionFrom: " + positionsFrom_ToBeRemoved_MandatoryPositionsTo.keySet());
//            System.out.println("2.          Map by positionFrom: " + positionsFrom_ToBeRemoved_MandatoryPositionsTo.get(positionFrom));
//            System.out.println("3.        Positions toBeRemoved: " + positionsFrom_ToBeRemoved_MandatoryPositionsTo.get(positionFrom).keySet());
//            System.out.println("4. Possible mandatory movements: " + positionsFrom_ToBeRemoved_MandatoryPositionsTo.get(positionFrom).values());
//            System.out.println("5.   Mandatory positions merged: " + allMandatoryPositions);
//            System.out.println("\n");

            if (!allMandatoryPositions.contains(positionTo)) {
                System.out.println(RED + "ERROR:" + RESET + " This move was not proceed" + RED +
                        " (incorrect position to - " + positionTo + ")" + RESET +
                        " because capturing / continue capturing is mandatory... try again according to the " +
                        BLUE + "RULE" + RESET + " mentioned above");
                return false;
            }

            positionsFrom_ToBeRemoved_MandatoryPositionsTo.get(positionFrom).forEach((k, v) -> {
                if (v.contains(positionTo)) {
                    players.get(k).setState(ELIMINATED);
                    players.remove(k);
                    positionsFrom_ToBeRemoved_MandatoryPositionsTo.clear();
                    hasCapturedInPreviosuRound = true;
                }
            });


//            // 3. update piece move from to
//            updateMovedPiece(positionFrom, positionTo);
//
//            // 4. change pawn to dame if needed
//            if (shouldWeChangePieceToDame(players.get(positionTo))) {
//                String playersColor = players.get(positionFrom).getColor() == DARK ? "Dark" : "Light";
//                System.out.println(BLUE + "RULE:" + RESET + " " + playersColor +
//                        " player piece reached the crownhead and " + BLUE + "becomes the Dame" + RESET +
//                        "... congratulation " + playersColor + " player");
//                changePawnToDame(players.get(positionFrom));
//            }


        } else {
            // 1. is movement valid
            if (isMovementConditionsNotCorrect(stringFrom, stringTo)) {
                return false;
            }
        }

        // 2. change light to dark (back and forth)
        OneFigure piece = players.get(positionFrom);
        if (piece.getColor() != darkOrLight) {
            System.out.println(BLUE + "RULE:" + RESET + " movement needs to be altering... it is not " +
                    BLUE + (piece.getColor() == 0 ? "Dark" : "light") + RESET + " turn (" + RED +
                    "movement was not proceeded, try again" + RESET + ")");
            return false;
        }

        // 3. update piece move from to
        updateMovedPiece(positionFrom, positionTo);


        if (hasCapturedInPreviosuRound) {
            singlePieceCapturingListener(players.get(positionTo));
            if (positionsFrom_ToBeRemoved_MandatoryPositionsTo.isEmpty()) {
                // 4. change pawn to dame if needed
                if (shouldWeChangePieceToDame(piece)) {
                    String playersColor = piece.getColor() == DARK ? "Dark" : "Light";
                    System.out.println(BLUE + "RULE:" + RESET + " " + playersColor +
                            " player piece reached the crownhead and " + BLUE + "becomes the Dame" + RESET +
                            "... congratulation " + playersColor + " player");
                    changePawnToDame(piece);
                }
                // 5. change next player color
                darkOrLight = changeDarkLightTurn(darkOrLight);
                // 6. is there anything-to-capture listener
                capturingListener(darkOrLight);
            }
            hasCapturedInPreviosuRound = false;
        } else {
            // 4. change pawn to dame if needed
            if (shouldWeChangePieceToDame(piece)) {
                String playersColor = piece.getColor() == DARK ? "Dark" : "Light";
                System.out.println(BLUE + "RULE:" + RESET + " " + playersColor +
                        " player piece reached the crownhead and " + BLUE + "becomes the Dame" + RESET +
                        "... congratulation " + playersColor + " player");
                changePawnToDame(piece);
            }
            // 5. change next player color
            darkOrLight = changeDarkLightTurn(darkOrLight);
            // 6. is there anything-to-capture listener
            capturingListener(darkOrLight);
        }


        // 7. print RULE if any
        positionsFrom_ToBeRemoved_MandatoryPositionsTo.forEach((k, v) -> {
            System.out.print(BLUE + "RULE:" + RESET + " " + (darkOrLight == DARK ? "Dark" : "Light") + " player's " +
                    (players.get(positionTo).getFigure() == PAWN ? "pawn" : "dame") + " mandatory movement from " +
                    BLUE + k + RESET + " to ");
            AtomicInteger counter = new AtomicInteger(1);
            v.forEach((keyV, valueV) -> {
                System.out.print("(" + counter.getAndIncrement() + ") " + BLUE + valueV + RESET + " and opponent " + BLUE + keyV + RESET + " will be captured, ");
            });
            System.out.println();
        });

        return true;
    }

    private int changeDarkLightTurn(int darkOrLight) {
        return darkOrLight == 0 ? 1 : 0;
    }


    //////////////////////  MOVEMENT - old working approach ///////////////////////////////////////////////////////////////////////////////////////

    // TODO: add loggers
    // TODO: ustalić ograniczenie ruchu na zmianę
    public boolean movePieceByString_oldWorkingApproach(String stringFrom, String stringTo) {
        Position positionFrom = getPositionFromString(stringFrom);
        Position positionTo = getPositionFromString(stringTo);

//        System.out.println(RED + positionFrom + RESET);
//        System.out.println(RED + players.get(positionFrom) + RESET);
//        System.out.println(RED + players.get(positionFrom).getColor() + RESET);
//        System.out.println(RED + (players.get(positionFrom).getColor() == DARK_COLOR ? "Dark" : "Light") + RESET);

        if (capturingMandatoryByOpponent) {
            if (!mandatoryPositions.contains(positionTo)) {
                System.out.println(RED + "ERROR:" + RESET + " This move" + RED + " was not proceed" + RESET +
                        " because capturing / continue capturing is mandatory... try again according to the " +
                        BLUE + "RULE" + RESET + " mentioned above");
                return false;
            } else {
                capturingMandatoryByOpponent = false;

//                int indexToBeRemoved;
//                if(pieceToBeRemoved.size() == 1){
//                    indexToBeRemoved = 0;
//                } else {
//                    indexToBeRemoved = mandatoryPosition.indexOf(positionTo);
//                }

                int indexToBeRemoved = piecesToBeRemoved.size() == 1 ? 0 : mandatoryPositions.indexOf(positionTo);
                Position pieceToBeRemovedByIndexOfPositionToInMandatoryPositions = piecesToBeRemoved.get(indexToBeRemoved);

                players.get(pieceToBeRemovedByIndexOfPositionToInMandatoryPositions).setState(0);
                players.remove(pieceToBeRemovedByIndexOfPositionToInMandatoryPositions);

                mandatoryPositions.clear();
                piecesToBeRemoved.clear();

                updateMovedPiece(positionFrom, positionTo);

                continueCapturingIfPossible = canContinueCapturing(positionTo, players.get(positionTo).getFigure());

                /////////////////
//                int pawnOrDame = players.get(positionTo).getFigure();
//                int playerColor = players.get(positionTo).getColor();
//                if (isDameMoving(pawnOrDame)) {
//                    continueCapturingIfPossible = canContinueCapturing(positionTo);
//                } else {
//                    continueCapturingIfPossible = canContinueCapturing(positionTo);
//                }
                /////////////////

                if (continueCapturingIfPossible) {
                    System.out.println(RED + "Continue capturing is possible!!!" + RESET);
                    capturingMandatoryByOpponent = true;
                }

            }
        } else {

            if (isMovementConditionsNotCorrect(stringFrom, stringTo)) {
                return false;
            }

            updateMovedPiece(positionFrom, positionTo);

        }
        if (!continueCapturingIfPossible) {
            canBeCapturedAfterMovementByDameOrPawn(positionTo);
        }
        continueCapturingIfPossible = false;

        OneFigure piece = players.get(positionTo);
        if (shouldWeChangePieceToDame(piece)) {
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
            System.out.println(RED + "ERROR add message:" + RESET + " you are not moving... positions form and to are equal :)");
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

/////////////////////////////////////// CHECK CAPTURING BY THE OPPONENT AFTER MOVEMENT /////////////////////////////////////////////////////

    private void canBeCapturedAfterMovementByDameOrPawn(Position justMovedTo) {

//        int playerColor = players.get(justMovedTo).getColor();
//        String opponentColor = playerColor == DARK ? "Light" : "Dark";
//        Map<Position, OneFigure> opponentDames = getOpponentDames(playerColor);
//
//        if (!opponentDames.isEmpty()) {
//            opponentDames.forEach((k, v) -> {
//                continueCapturingAfterMovementByDame(justMovedTo, k, opponentColor);
////                if (arePositionsOnTheSameDiagonal(justMovedTo, k) && noOtherPiecesInBetween(justMovedTo, k)) {
////                    List<Position> emptyFieldsAfter = getEmptyFieldsAfterByPossibleCapturing(k, justMovedTo);
////                    if (!emptyFieldsAfter.isEmpty()) {
////                        mandatoryPosition.addAll(emptyFieldsAfter);
////                        pieceToBeRemoved.add(justMovedTo);
////                        capturingMandatoryByOpponent = true;
////
////                        mandatoryPosition.forEach(p -> System.out.println(BLUE + "RULE:" + RESET + " " + opponentColor +
////                                " player Dame mandatory movement from " + BLUE + k + RESET + " to " + BLUE + p + RESET +
////                                " because capturing is mandatory"));
////                    }
////                }
//
//                ////////////////////////////////// FIRSt APPORAACH
//
////                List<Position> emptyFieldsAfter = getEmptyFieldsAfterByPossibleCapturing(k, justMovedTo);
////
////                if (arePositionsOnTheSameDiagonal(justMovedTo, k) && noOtherPiecesInBetween(justMovedTo, k) && !emptyFieldsAfter.isEmpty()) {
////                    mandatoryPosition.addAll(emptyFieldsAfter);
////                    pieceToBeRemoved.add(justMovedTo);
////                    capturingMandatoryByOpponent = true;
////
////                    mandatoryPosition.forEach(p -> System.out.println(BLUE + "RULE:" + RESET + " " + opponentColor +
////                            " player Dame mandatory movement from " + BLUE + k + RESET + " to " + BLUE + p + RESET +
////                            " because capturing is mandatory"));
////                }
//            });
//        }
        continueCapturingAfterMovementByDames(justMovedTo);

        continueCapturingAfterMovementByPawns(justMovedTo);
    }

    /////////////////////////////// canBeCapturedAfterMovementByDame ////////////////////////////////////////////////////////////////

//    private void continueCapturingAfterMovementByDame(Position justMovedTo, Position positionDame, String opponentColor) {
//        if (arePositionsOnTheSameDiagonal(justMovedTo, positionDame) && noOtherPiecesInBetween(justMovedTo, positionDame)) {
//            List<Position> emptyFieldsAfter = getEmptyFieldsAfterByPossibleCapturing(positionDame, justMovedTo);
//            if (!emptyFieldsAfter.isEmpty()) {
//                mandatoryPosition.addAll(emptyFieldsAfter);
//                pieceToBeRemoved.add(justMovedTo);
//                capturingMandatoryByOpponent = true;
//
//                mandatoryPosition.forEach(p -> System.out.println(BLUE + "RULE:" + RESET + " " + opponentColor +
//                        " player Dame mandatory movement from " + BLUE + positionDame + RESET + " to " + BLUE + p + RESET +
//                        " because capturing is mandatory"));
//            }
//        }
//    }

    //////////////////////////
    private boolean continueCapturingAfterMovementByDames(Position justMovedTo) {
        int playerColor = players.get(justMovedTo).getColor();
        String opponentColor = playerColor == DARK ? "Light" : "Dark";
        Map<Position, OneFigure> opponentDames = getOpponentDames(playerColor);

        if (opponentDames.isEmpty()) {
            return false;
        }

        List<Boolean> flags = new ArrayList<>();
        opponentDames.forEach((k, v) -> {
            boolean flag = continueCapturingAfterMovementByDame(justMovedTo, k, opponentColor);
            flags.add(flag);

        });

        return flags.stream().reduce((f1, f2) -> f1 || f2).orElse(false);
    }

    private boolean continueCapturingAfterMovementByDame(Position justMovedTo, Position positionDame, String opponentColor) {
        if (!arePositionsOnTheSameDiagonal(justMovedTo, positionDame) || !noOtherPiecesInBetween(justMovedTo, positionDame)) {
            return false;
        }
        List<Position> emptyFieldsAfter = getEmptyFieldsAfterByPossibleCapturing(positionDame, justMovedTo);
        if (emptyFieldsAfter.isEmpty()) {
            return false;
        }

        mandatoryPositions.addAll(emptyFieldsAfter);
        piecesToBeRemoved.add(justMovedTo);
        capturingMandatoryByOpponent = true;

        mandatoryPositions.forEach(p -> System.out.println(BLUE + "RULE:" + RESET + " " + opponentColor +
                " player Dame mandatory movement from " + BLUE + positionDame + RESET + " to " + BLUE + p + RESET +
                " because capturing is mandatory"));

        return true;
    }
    /////////////////////////

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


    // TODO: pawnOrDame is always dame ... veryfi / change it
    private boolean canCapturingByDame(Position position, Position isGoingToBeRemoved, int pawnOrDame, int opponentColor) {
        if (isDameMoving(pawnOrDame)) {
            List<List<Position>> allDiagonals = getDiagonalsSplitByPosition(position);

            System.out.println(BLUE + position + RESET);

            for (List<Position> partDiagonal : allDiagonals) {
                System.out.println(RED + partDiagonal + RESET);
                if (partDiagonal.size() >= 2) {
                    for (int i = 0; i < partDiagonal.size() - 1; i++) {
//                        System.out.println(YELLOW + players.containsKey(partDiagonal.get(i)) + RESET);
//                        System.out.println(YELLOW + (players.get(partDiagonal.get(i)).getColor()) + RESET);
//                        System.out.println(YELLOW + !players.containsKey(partDiagonal.get(i + 1)) + RESET);

                        if (!partDiagonal.get(i).equals(isGoingToBeRemoved) &&
                                players.containsKey(partDiagonal.get(i)) &&
                                players.get(partDiagonal.get(i)).getColor() == opponentColor &&
                                !players.containsKey(partDiagonal.get(i + 1))) {
                            System.out.println(YELLOW + partDiagonal.get(i) + RESET);
                            return true;
                        }
                    }
                }
            }
        }
        return false;

//        else {
//            Position positionLeftTop = new Position(position.getY() + 1, position.getX() - 1);
//            Position positionLeftTopDiagonal = new Position(position.getY() + 2, position.getX() - 2);
//
//            Position positionRightTop = new Position(position.getY() + 1, position.getX() + 1);
//            Position positionRightTopDiagonal = new Position(position.getY() + 2, position.getX() + 2);
//
//            Position positionLeftBottom = new Position(position.getY() - 1, position.getX() - 1);
//            Position positionLeftBottomDiagonal = new Position(position.getY() - 2, position.getX() - 2);
//
//            Position positionRightBottom = new Position(position.getY() - 1, position.getX() + 1);
//            Position positionRightBottomDiagonal = new Position(position.getY() - 2, position.getX() + 2);
//
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
//            boolean flag1 = ifContinuingCapturingMandatory(positionLeftTop, positionLeftTopDiagonal, position);
//            boolean flag2 = ifContinuingCapturingMandatory(positionRightBottom, positionRightBottomDiagonal, position);
//
//            boolean flag3 = ifContinuingCapturingMandatory(positionRightTop, positionRightTopDiagonal, position);
//            boolean flag4 = ifContinuingCapturingMandatory(positionLeftBottom, positionLeftBottomDiagonal, position);
//
////            if (flag1 || flag2 || flag3 || flag4) {
////                System.out.println(RED + flag1 + ", " + flag2 + ", " + flag3 + ", " + flag4 + ", " + RESET);
////                return true;
////            }
////            return false;
//            return flag1 || flag2 || flag3 || flag4;
//
//        }
    }


    //////////////////////////// canBeCapturedAfterMovementByPawn ///////////////////////////////////////////////////////////////////


    private void continueCapturingAfterMovementByPawns(Position justMovedTo) {
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
            mandatoryPositions.add(positionTo);
            piecesToBeRemoved.add(justMovedTo);
        }
    }

    private boolean isCapturingAllowed(Position from, Position to, int playerSide) {
        return players.containsKey(from) &&
                players.get(from).getColor() != playerSide &&
                to.isValid() &&
                !players.containsKey(to);
    }


////////////////////////// DAME & PAWN - can CONTINUE capturing after movement ///////////////////////////////////////////////////////

    private boolean canContinueCapturing(Position position, int pawnOrDame) {

        return pawnOrDame == DAME ?
                canContinueCapturingByDame(position) :
                canContinueCapturingByPawn(position);

//        boolean flagPawn = canContinueCapturingByPawn(position);
//
//        boolean flagDame = canContinueCapturingByDame(position);
////        boolean flagDame = false;
//
//        return flagPawn || flagDame;
//        Position positionLeftTop = new Position(position.getY() + 1, position.getX() - 1);
//        Position positionLeftTopDiagonal = new Position(position.getY() + 2, position.getX() - 2);
//
//        Position positionRightTop = new Position(position.getY() + 1, position.getX() + 1);
//        Position positionRightTopDiagonal = new Position(position.getY() + 2, position.getX() + 2);
//
//        Position positionLeftBottom = new Position(position.getY() - 1, position.getX() - 1);
//        Position positionLeftBottomDiagonal = new Position(position.getY() - 2, position.getX() - 2);
//
//        Position positionRightBottom = new Position(position.getY() - 1, position.getX() + 1);
//        Position positionRightBottomDiagonal = new Position(position.getY() - 2, position.getX() + 2);
//
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
//        boolean flag1 = ifContinuingCapturingMandatory(positionLeftTop, positionLeftTopDiagonal, position);
//        boolean flag2 = ifContinuingCapturingMandatory(positionRightBottom, positionRightBottomDiagonal, position);
//
//        boolean flag3 = ifContinuingCapturingMandatory(positionRightTop, positionRightTopDiagonal, position);
//        boolean flag4 = ifContinuingCapturingMandatory(positionLeftBottom, positionLeftBottomDiagonal, position);
//
////            if (flag1 || flag2 || flag3 || flag4) {
////                System.out.println(RED + flag1 + ", " + flag2 + ", " + flag3 + ", " + flag4 + ", " + RESET);
////                return true;
////            }
////            return false;
//        return flag1 || flag2 || flag3 || flag4;
    }

    private boolean canContinueCapturingByPawn(Position position) {
        System.out.println(YELLOW + "canContinueCapturingByPawn" + RESET);

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

    private boolean ifContinuingCapturingMandatory(Position toBeCaptured, Position positionAfterCapturing, Position justMovedTo) {
        int playerSide = players.get(justMovedTo).getColor();
        if (isCapturingAllowed(toBeCaptured, positionAfterCapturing, playerSide)) {
            System.out.println(BLUE + "RULE:" + RESET + " " + (playerSide == DARK ? "Dark" : "Light") +
                    " player's " + BLUE + "pawn" + RESET + " is required to continue capturing... from " +
                    BLUE + justMovedTo + RESET + " to " + BLUE + positionAfterCapturing + RESET);

            mandatoryPositions.add(positionAfterCapturing);
            piecesToBeRemoved.add(toBeCaptured);
            return true;
        }
        return false;
    }

    private boolean canContinueCapturingByDame(Position position) {
        System.out.println(YELLOW + "canContinueCapturingByDame" + RESET);

        List<List<Position>> diagonalsSplitByDamePosition = getDiagonalsSplitByPosition(position);
        int dameColor = players.get(position).getColor();

        for (List<Position> oneDiagonalSplit : diagonalsSplitByDamePosition) {
            List<Position> opponentPieces = getOpponentPiece(oneDiagonalSplit, dameColor);
            if (!opponentPieces.isEmpty()) {
                for (Position p : opponentPieces) {
                    List<Position> emptyFieldsAfter = getEmptyFieldsAfter(position, p);
                    if (!emptyFieldsAfter.isEmpty()) {
                        mandatoryPositions.addAll(emptyFieldsAfter);
                        System.out.println(BLUE + "RULE:" + RESET + " " + (dameColor == DARK ? "Dark" : "Light") +
                                " player's " + BLUE + "dame" + RESET + " is required to continue capturing... from " +
                                BLUE + position + RESET + " to " + BLUE + emptyFieldsAfter + RESET);
                        piecesToBeRemoved.add(p);
                    }
                }
            }
        }

        ////////////////////////////// : /

        return !mandatoryPositions.isEmpty();
    }

    private List<Position> getOpponentPiece(List<Position> oneDiagonalSplit, int dameColor) {
        List<Position> result = new ArrayList<>();

        oneDiagonalSplit.forEach(p -> {
            if (players.containsKey(p) && players.get(p).getColor() != dameColor) {
                result.add(p);
            }
        });

        return result;
    }

//    private boolean isEmptyFieldAfter ()

///////////////////////////////////////////// CHANGE PIECE TO DAME ///////////////////////////////////////////////////////////

//    private boolean shouldWeChangePieceToDame(int pieceColor, int pieceAxisY) {
//
//        if (pieceColor == DARK && pieceAxisY == 7) return true;
//        if (pieceColor == LIGHT && pieceAxisY == 0) return true;
//        return false;
//    }

    private boolean shouldWeChangePieceToDame(OneFigure piece) {
        if (piece.getFigure() == DAME) return false;
        if (piece.getColor() == DARK && piece.getPosition().getY() == LAST_COORDINATE) return true;
        if (piece.getColor() == LIGHT && piece.getPosition().getY() == FIRST_COORDINATE) return true;
        return false;
    }

    // TODO: rozważyć falgę mówiącą że dany gracz ma damkę ... ale potem jakoś kaowanie tej flagi jak wszystkie damki danego gracza zginą
    private void changePawnToDame(OneFigure piece) {
        piece.setFigure(DAME);
//        System.out.println(RED+piece+RESET);
    }


//////////////////////////////////////// CAPTURING LISTENER ///////////////////////////////////////////////////////////////////////

    private void capturingListener(int darkOrLight) {
        players.forEach((k, v) -> {
            if (v.getColor() == darkOrLight) {
                singlePieceCapturingListener(v);
            }
        });
    }

    private void singlePieceCapturingListener(OneFigure piece) {

        Position position = piece.getPosition();
        int pawnOrDame = piece.getFigure();
        int pieceColor = piece.getColor();

        if (isDameMoving(pawnOrDame)) {
            dameCapturingListener(position, pieceColor);
        } else {
            pawnCapturingListener(position, pieceColor);
        }
    }

    //////////////////////////////////// DAME ////////////////////////////////

    private void dameCapturingListener(Position dameToBeListen, int dameColor) {
        // 1. get all diagonals, ie. dame paths divided by current dame position
        List<List<Position>> allDiagonalsSplit = getDiagonalsSplitByPosition(dameToBeListen);

        System.out.println("allDiagonalsSplit: " + YELLOW + allDiagonalsSplit + RESET);
//        List<Position> opponentPiecesOnDamePath = allDiagonalsSplit.stream()
//                .filter(p -> getOpponentPiecesOnDiagonal(p, dameColor).isEmpty())
//                .flatMap(Collection::stream)
//                .collect(Collectors.toList());
        // 2. find any opponents on this diagonals
        List<Position> opponentPiecesOnDamePath = new ArrayList<>();
        allDiagonalsSplit.forEach(p -> opponentPiecesOnDamePath.addAll(getOpponentPiecesOnDiagonal(p, dameColor)));

        System.out.println("opponentPiecesOnDamePath: " + YELLOW + opponentPiecesOnDamePath + RESET);

        // 3. remove opponents which has other piece(s) on the path , ie. cannot be captured
        List<Position> filteredOpponentPiecesOnDamePath = opponentPiecesOnDamePath.stream()
                .filter(p -> noOtherPiecesInBetween(dameToBeListen, p))
                .collect(Collectors.toList());

        System.out.println("filteredOpponentPiecesOnDamePath: " + YELLOW + filteredOpponentPiecesOnDamePath + RESET);

        Map<Position, List<Position>> toBeRemoved_MandatoryPositionsTo = new HashMap<>();
        positionsFrom_ToBeRemoved_MandatoryPositionsTo.put(dameToBeListen, toBeRemoved_MandatoryPositionsTo);
//        filteredOpponentPiecesOnDamePath.forEach(p ->
//                toBeRemoved_MandatoryPositionsTo.put(p, getEmptyFieldsAfterByPossibleCapturing(dameToBeListen, p))
//        );
        // 4. get possible positions of the dame after capturing
        for (Position p : filteredOpponentPiecesOnDamePath) {
            toBeRemoved_MandatoryPositionsTo.put(p, getEmptyFieldsAfterByPossibleCapturing(dameToBeListen, p));
        }

        System.out.println("emptyFieldsAfterByPossibleCapturing: " + YELLOW + positionsFrom_ToBeRemoved_MandatoryPositionsTo + RESET);

    }

    private List<List<Position>> getDiagonalsSplitByPosition(Position position) {
        List<List<Position>> result = new ArrayList<>();

        List<Position> result1 = getIterations(position, 1, 1);
        List<Position> result2 = getIterations(position, -1, 1);
        List<Position> result3 = getIterations(position, 1, -1);
        List<Position> result4 = getIterations(position, -1, -1);

        result.add(result1);
        result.add(result2);
        result.add(result3);
        result.add(result4);

        return result;
    }

    private List<Position> getOpponentPiecesOnDiagonal(List<Position> diagonalSplit, int dameColor) {
        if (diagonalSplit.size() <= 1) return Collections.emptyList();
        return diagonalSplit.stream().filter(p -> players.containsKey(p) && players.get(p).getColor() != dameColor).collect(Collectors.toList());
    }

    private boolean noOtherPiecesInBetween(Position positionOne, Position positionTwo) {
        List<Position> piecesInBetween = getFieldsInBetween(positionOne, positionTwo);
        for (Position p : piecesInBetween) {
            if (players.containsKey(p)) return false;
        }
        return true;
    }

    private List<Position> getEmptyFieldsAfterByPossibleCapturing(Position positionDame, Position justMovedTo) {
        List<Position> emptyFieldsAfter = getEmptyFieldsAfter(positionDame, justMovedTo);
        int opponentColor = players.get(justMovedTo).getColor();

        List<Position> result = new ArrayList<>();
        emptyFieldsAfter.forEach(p -> {
            if (canCapturingByDame(p, justMovedTo, DAME, opponentColor)) {  // TODO: dodaje justMovedTo bo jeszcze nie usuwa zbitego
                result.add(p);
//                System.out.println("Can continue capturing after dame movement: " + RED + p + RESET);
            }
        });

        if (result.isEmpty()) {
            return emptyFieldsAfter;
        }

        return result;
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
            System.out.println(RED + "INTERNAL MESSAGE: no empty fields in between (to be removed in production version)" + RESET);
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

    private List<Position> getIterations(Position position, int deltaX, int deltaY) {
        int positionY = position.getY();
        int positionX = position.getX();

        List<Position> result = new ArrayList<>();
        Position iteration = new Position(positionY + deltaY, positionX + deltaX);

        while (iteration.isValid()) {
            result.add(iteration);
            iteration = new Position(iteration.getY() + deltaY, iteration.getX() + deltaX);
        }

        return result;
    }

    //////////////////////////////////// PAWN /////////////////////////////////

    private void pawnCapturingListener(Position pawnToBeListen, int pawnColor) {

        Position positionLeftTop = new Position(pawnToBeListen.getY() + 1, pawnToBeListen.getX() - 1);
        Position positionRightTop = new Position(pawnToBeListen.getY() + 1, pawnToBeListen.getX() + 1);
        Position positionLeftBottom = new Position(pawnToBeListen.getY() - 1, pawnToBeListen.getX() - 1);
        Position positionRightBottom = new Position(pawnToBeListen.getY() - 1, pawnToBeListen.getX() + 1);

        Position[] positionsNextToBeListen = {positionLeftTop, positionRightTop, positionLeftBottom, positionRightBottom};

//        System.out.println("positionsNextToBeListen: " + YELLOW + Arrays.toString(positionsNextToBeListen) + RESET);

        List<Position> opponentsNextToBeListen = new ArrayList<>(4);
        // is position next to me exist and contain any piece && is this piece is opponent piece (different color)
        for (Position p : positionsNextToBeListen) {
            if (players.containsKey(p) && players.get(p).getColor() != pawnColor) {
                opponentsNextToBeListen.add(p);
            }
        }

//        System.out.println("opponentsNextToBeListen: " + YELLOW + opponentsNextToBeListen + RESET);

        if (opponentsNextToBeListen.isEmpty()) {
            return;
        }

        Position positionAfter;
        for (Position positionToBeRemoved : opponentsNextToBeListen) {
            positionAfter = getFieldAfter(pawnToBeListen, positionToBeRemoved);
            if (positionAfter.isValid() && !players.containsKey(positionAfter)) {
                Position finalPositionAfter = positionAfter;
                // TODO: start - do oddzielnej metody
                if (!positionsFrom_ToBeRemoved_MandatoryPositionsTo.containsKey(pawnToBeListen)) {
                    positionsFrom_ToBeRemoved_MandatoryPositionsTo.put(
                            pawnToBeListen,
                            new HashMap<>() {{
                                put(positionToBeRemoved, Arrays.asList(finalPositionAfter));
                            }}
                    );
                } else {
                    positionsFrom_ToBeRemoved_MandatoryPositionsTo.get(pawnToBeListen)
                            .put(positionToBeRemoved, Arrays.asList(positionAfter));
                }
                // TODO: end - do oddzilenje metody
            }

        }
//        if (positionsFrom_ToBeRemoved_MandatoryPositionsTo.isEmpty()) {
//            return;
//        }
//
//        positionsFrom_ToBeRemoved_MandatoryPositionsTo.forEach((k, v) ->
//        {
//            System.out.print(BLUE + "RULE:" + RESET + " mandatory movement from " + BLUE + k + RESET + " to ");
//            AtomicInteger counter = new AtomicInteger(1);
//            v.forEach((keyV, valueV) -> {
//                System.out.print("(" + counter.getAndIncrement() + ") " + BLUE + valueV + RESET + " and opponent " + BLUE + keyV + RESET + " will be captured, ");
//            });
//            System.out.println();
//        });
    }

    private Position getFieldAfter(Position positionToBeListen, Position positionOpponent) {

        int deltaY = positionOpponent.getY() - positionToBeListen.getY();
        int deltaX = positionOpponent.getX() - positionToBeListen.getX();

        return new Position(
                positionOpponent.getY() + deltaY,
                positionOpponent.getX() + deltaX
        );
    }

//////////////////////////////////////// DAME /////////////////////////////////////////////////////////////////////////////////

    private boolean isDameMoving(int pawnOrDame) {
        return pawnOrDame == DAME;
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

    private void updateMovedPiece(Position positionFrom, Position positionTo) {
        players.get(positionFrom).setPosition(positionTo);      // change of position - new (y,x) coordinates
        players.put(positionTo, players.get(positionFrom));     // change in players database
        players.remove(positionFrom);                           // remove OneFigure with old position
    }

}



