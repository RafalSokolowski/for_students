package day20201220.end_project.checker;

import day20201220.end_project.figure.OnTheBoard;
import day20201220.end_project.figure.OneFigure;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

import static day20201220.end_project.utils.Const.*;

@AllArgsConstructor
public class Listener {

    private Map<Position, OnTheBoard> board;
    private Map<Position, OneFigure> players;
    private Map<Position, Map<Position, List<Position>>> positionsFrom_ToBeRemoved_MandatoryPositionsTo;

    /**
     * capturing listener is divided into two separate logics for pawn and dame
     * allPieces is listening for any capturing capabilities for particular player (Dark or Light)
     *
     * @param darkOrLight for light or dark player
     */
    public void allPieces(int darkOrLight) {
        players.forEach((k, v) -> {
            if (v.getColor() == darkOrLight) {
                singlePiece(v);
            }
        });
    }

    /**
     * singlePieces is listening for capturing capabilities for particular piece (pawn or dame)
     *
     * @param piece for single piece on the board (pawn or dame)
     */
    public void singlePiece(OneFigure piece) {

        Position position = piece.getPosition();
        int pawnOrDame = piece.getFigure();
        int pieceColor = piece.getColor();

        if (isDameMoving(pawnOrDame)) {
            dameCapturingListener(position, pieceColor);
        } else {
            pawnCapturingListener(position, pieceColor);
        }
    }

    /**
     * Dame capturing listener set of methods:
     */

    private void dameCapturingListener(Position dameToBeListen, int dameColor) {
        /** 1. get all diagonals, ie. dame paths divided by current dame position */
        List<List<Position>> allDiagonalsSplit = getDiagonalsSplitByPosition(dameToBeListen);

        /** 2. find any opponents on this diagonals */
        List<Position> opponentPiecesOnDamePath = new ArrayList<>();
        allDiagonalsSplit.forEach(p -> opponentPiecesOnDamePath.addAll(getOpponentPiecesOnDiagonal(p, dameColor)));

        /** 3. remove opponents which has other piece(s) on the path , ie. cannot be captured */
        List<Position> filteredOpponentPiecesOnDamePath = opponentPiecesOnDamePath.stream()
                .filter(p -> noOtherPiecesInBetween(dameToBeListen, p))
                .collect(Collectors.toList());

        /** 4. get possible positions of the dame after capturing */
        for (Position p : filteredOpponentPiecesOnDamePath) {
            getEmptyFieldsAfterByPossibleCapturing(dameToBeListen, p).ifPresent(fields ->
                    addRecordToCapturingMap(dameToBeListen, p, fields)
            );
        }
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

    private Optional<List<Position>> getEmptyFieldsAfterByPossibleCapturing(Position positionDame, Position justMovedTo) {
        List<Position> emptyFieldsAfter = getEmptyFieldsAfter(positionDame, justMovedTo);
        if (emptyFieldsAfter.isEmpty()) return Optional.empty();

        int opponentColor = players.get(justMovedTo).getColor();

        List<Position> result = new ArrayList<>();
        emptyFieldsAfter.forEach(p -> {
            if (canCapturingByDame(p, justMovedTo, opponentColor)) {
                result.add(p);
            }
        });

        if (result.isEmpty()) {
            return Optional.of(emptyFieldsAfter);
        }

        return Optional.of(result);
    }

    private boolean canCapturingByDame(Position position, Position isGoingToBeRemoved, int opponentColor) {
        if (isDameMoving(DAME)) {
            List<List<Position>> allDiagonals = getDiagonalsSplitByPosition(position);

            for (List<Position> partDiagonal : allDiagonals) {
                if (partDiagonal.size() >= 2) {
                    for (int i = 0; i < partDiagonal.size() - 1; i++) {
                        if (!partDiagonal.get(i).equals(isGoingToBeRemoved) &&
                                players.containsKey(partDiagonal.get(i)) &&
                                players.get(partDiagonal.get(i)).getColor() == opponentColor &&
                                !players.containsKey(partDiagonal.get(i + 1))) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean isDameMoving(int pawnOrDame) {
        return pawnOrDame == DAME;
    }

    private List<Position> getEmptyFieldsAfter(Position positionDame, Position justMovedTo) {
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

    private List<Position> getFieldsInBetween(Position positionOne, Position positionTwo) {
        int oneX = positionOne.getX();
        int oneY = positionOne.getY();
        int twoX = positionTwo.getX();
        int twoY = positionTwo.getY();

        int deltaX = Math.abs(oneX - twoX);
        int deltaY = Math.abs(oneY - twoY);

        if (deltaX != deltaY) {
//            System.out.println(RED + "INTERNAL MESSAGE: problem with diagonal distance in getFieldsInBetween (to be removed in production version)" + RESET);
            return Collections.emptyList();
        }

        if (deltaX <= 1) {
//            System.out.println(RED + "INTERNAL MESSAGE: no empty fields in between (to be removed in production version)" + RESET);
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

    /**
     * Pawn capturing listener set of methods:
     */

    private void pawnCapturingListener(Position pawnToBeListen, int pawnColor) {

        Position positionLeftTop = new Position(pawnToBeListen.getY() + 1, pawnToBeListen.getX() - 1);
        Position positionRightTop = new Position(pawnToBeListen.getY() + 1, pawnToBeListen.getX() + 1);
        Position positionLeftBottom = new Position(pawnToBeListen.getY() - 1, pawnToBeListen.getX() - 1);
        Position positionRightBottom = new Position(pawnToBeListen.getY() - 1, pawnToBeListen.getX() + 1);

        Position[] positionsNextToBeListen = {positionLeftTop, positionRightTop, positionLeftBottom, positionRightBottom};

        List<Position> opponentsNextToBeListen = new ArrayList<>(4);
        for (Position p : positionsNextToBeListen) {
            if (players.containsKey(p) && players.get(p).getColor() != pawnColor) {
                opponentsNextToBeListen.add(p);
            }
        }

        if (opponentsNextToBeListen.isEmpty()) {
            return;
        }

        Position positionAfter;
        for (Position positionToBeRemoved : opponentsNextToBeListen) {
            positionAfter = getFieldAfter(pawnToBeListen, positionToBeRemoved);
            if (positionAfter.isValid() && !players.containsKey(positionAfter)) {
                addRecordToCapturingMap(pawnToBeListen, positionToBeRemoved, Arrays.asList(positionAfter));
            }
        }
    }

    private Position getFieldAfter(Position positionToBeListen, Position positionOpponent) {

        int deltaY = positionOpponent.getY() - positionToBeListen.getY();
        int deltaX = positionOpponent.getX() - positionToBeListen.getX();

        return new Position(
                positionOpponent.getY() + deltaY,
                positionOpponent.getX() + deltaX
        );
    }

    private void addRecordToCapturingMap(Position positionToBeListen, Position positionToBeRemoved, List<Position> positionsAfter) {
        if (!positionsFrom_ToBeRemoved_MandatoryPositionsTo.containsKey(positionToBeListen)) {
            positionsFrom_ToBeRemoved_MandatoryPositionsTo.put(
                    positionToBeListen,
                    new HashMap<>() {{
                        put(positionToBeRemoved, positionsAfter);
                    }}
            );
        } else {
            positionsFrom_ToBeRemoved_MandatoryPositionsTo.get(positionToBeListen)
                    .put(positionToBeRemoved, positionsAfter);
        }
    }

}