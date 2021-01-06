package day20201220.end_project.checkers;

import day20201220.end_project.figure.OnTheBoard;
import day20201220.end_project.figure.OneFigure;
import day20201220.end_project.utils.Messages;
import lombok.AllArgsConstructor;

import java.util.Map;

import static day20201220.end_project.utils.Const.*;

@AllArgsConstructor
public class Conditions {

    private Map<Position, OnTheBoard> board;
    private Map<Position, OneFigure> players;


    public boolean isMovementConditionsNotCorrect(String stringFrom, String stringTo) {
        if (areStringMoveInvalid(stringFrom, stringTo)) {
            System.out.println(Messages.errorInvalidString(stringFrom, stringTo));
            return true;
        }

        Position positionFrom = getPositionFromString(stringFrom);
        Position positionTo = getPositionFromString(stringTo);

        return isMovementPositionsAreNotCorrect(positionFrom, positionTo);
    }

    public boolean areStringMoveInvalid(String stringFrom, String stringTo) {
        return isPieceMovementStringInvalid(stringFrom) || isPieceMovementStringInvalid(stringTo);
    }

    private boolean isMovementPositionsAreNotCorrect(Position positionFrom, Position positionTo) {
        if (positionFrom.equals(positionTo)) {
            System.out.println(Messages.errorPositionsEqual());
            return true;
        }

        if (ifPieceFromNotOnTheBoard(positionFrom)) {
            System.out.println(Messages.errorNoPlayerPiece(positionFrom));
            return true;
        }

        if (isPositionNotExists(positionTo)) {
            System.out.println(Messages.errorPositionNotExists(positionTo));
            return true;
        }
        if (isPositionTakenByOtherPiece(positionTo)) {
            System.out.println(Messages.errorTaken(positionTo));
            return true;
        }

        OneFigure piece = players.get(positionFrom);
        if (isPieceMovementInvalid(positionFrom, positionTo, piece)) {
            System.out.println(Messages.errorNotAllowed());
            return true;
        }

        return false;
    }

    private boolean isPieceMovementStringInvalid(String string) {
        Position tryCreatePosition = new Position(string);
        return tryCreatePosition.getY() == INVALID_COORDINATE || tryCreatePosition.getX() == INVALID_COORDINATE;
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
    private boolean isPieceMovementInvalid(Position positionFrom, Position positionTo, OneFigure piece) {


        int pawnOrDame = piece.getFigure();
        int color = piece.getColor();

        return pawnOrDame == PAWN ?
                isPawnMovementInvalid(positionFrom, positionTo, color) :
                isDameMovementInvalid(positionFrom, positionTo);
    }

    private boolean isPawnMovementInvalid(Position positionFrom, Position positionTo, int pieceColor) {
        int oldPositionX = positionFrom.getX();
        int oldPositionY = positionFrom.getY();
        int newPositionX = positionTo.getX();
        int newPositionY = positionTo.getY();
        if (Math.abs(oldPositionX - newPositionX) != 1) {
            System.out.println(Messages.errorTooFarY());
            return true;
        }

        if (pieceColor == 0 && oldPositionY - newPositionY != -1) {      // Dark pawn
            System.out.println(Messages.errorTooFarX(DARK));
            return true;
        }

        if (pieceColor == 1 && oldPositionY - newPositionY != 1) {     // Light pawn
            System.out.println(Messages.errorTooFarX(LIGHT));
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
            System.out.println(Messages.ruleNotDiagonal());
            return true;
        }

        if (Math.abs(oldPositionX - newPositionX) != Math.abs(oldPositionY - newPositionY)) {
            System.out.println(Messages.ruleTooManyDiagonals());
            return true;
        }

        return false;
    }

    private boolean isPositionNotExists(Position position) {
        return !board.containsKey(position);
    }

    private boolean isPositionTakenByOtherPiece(Position position) {
        return players.containsKey(position);
    }

}
