package day20201220.end_project.utils;

import day20201220.end_project.checker.Position;

import java.util.List;

import static day20201220.end_project.utils.Const.*;

public class Messages {

    public static String reachCrownhead(String darkOrLight) {
        return BLUE + "RULE:" + RESET + " " + darkOrLight +
                " player piece reached the crownhead and " + BLUE + "becomes the Dame" + RESET +
                "... congratulation " + darkOrLight + " player";
    }

    public static String ruleMovement1(int darkOrLight, int pawnOrDame, Position position) {
        return BLUE + "RULE:" + RESET + " " + (darkOrLight == DARK ? "Dark" : "Light") + " player's " +
                (pawnOrDame == PAWN ? "pawn" : "dame") + " mandatory movement from " +
                BLUE + position + RESET + " to ";
    }

    public static String ruleMovement2(int counter, List<Position> positions, Position opponent) {
        return "(" + counter + ") " + BLUE + positions + RESET + " and opponent " + BLUE + opponent + RESET + " will be captured, ";
    }

    public static String ruleNotDiagonal() {
        return RED + "ERROR add message:" + RESET + " possible reasons - dame movement is not diagonal";
    }

    public static String ruleTooManyDiagonals() {
        return RED + "ERROR add message:" + RESET + " possible reasons - dame jumped over too many diagonal line(s)";
    }

    public static String ruleWin(long lightPieces) {
        return BLUE + "\n!!! " + (lightPieces == 0 ? "DARK" : "LIGHT") + " PLAYER HAS WON THE GAME... CONGRATULATIONS !!!\n" + RESET;
    }

    public static String errorString(String stringFrom, String stringTo) {
        return RED + "ERROR:" + RESET + " This move was not proceed " + RED + "incorrect coordinates" +
                RESET + " (double check from " + RED + stringFrom + RESET + " and to " + RED + stringTo + RESET + ")";
    }

    public static String errorPositionFrom(Position positionFrom) {
        return RED + "ERROR:" + RESET + " This move was not proceed" + RED +
                " (incorrect position from - " + positionFrom + ")" + RESET +
                " because capturing / continue capturing is mandatory... try again according to the " +
                BLUE + "RULE" + RESET + " mentioned above";
    }

    public static String errorPositionTo(Position positionTo) {
        return RED + "ERROR:" + RESET + " This move was not proceed" + RED +
                " (incorrect position to - " + positionTo + ")" + RESET +
                " because capturing / continue capturing is mandatory... try again according to the " +
                BLUE + "RULE" + RESET + " mentioned above";
    }

    public static String errorMovementNotAltering(int darkOrLight) {
        return BLUE + "RULE:" + RESET + " movement needs to be altering... its " +
                BLUE + (darkOrLight == 0 ? "Light" : "Dark") + RESET + " turn now (" + RED +
                "movement was not proceeded, try again" + RESET + ")";
    }

    public static String errorTooFarY() {
        return RED + "ERROR add message:" + RESET + " too far for Y axis";
    }

    public static String errorTooFarX(int darkOrLight) {
        return darkOrLight == 0 ?
                RED + "ERROR add message:" + RESET + " possible reasons - too far for X axis, wrong direction or not diagonal in dark player case" :
                RED + "ERROR add message:" + RESET + " possible reasons - too far for X axis, wrong direction or not diagonal in light player case";
    }

    public static String errorPositionsEqual() {
        return RED + "ERROR add message:" + RESET + " you are not moving... positions form and to are equal :)";
    }

    public static String errorNoPlayerPiece(Position positionFrom) {
        return RED + "ERROR:" + RESET + " Cannot move from " + RED + positionFrom + RESET + " direction... there is no player's piece";
    }

    public static String errorPositionNotExists(Position positionTo) {
        return RED + "ERROR:" + RESET + " Cannot move to " + RED + positionTo + RESET + " direction... this direction does not exist on the board";
    }

    public static String errorTaken(Position positionTo) {
        return RED + "ERROR:" + RESET + " Cannot move to " + RED + positionTo + RESET + " direction... this direction is taken by the other piece";
    }

    public static String errorNotAllowed() {
        return RED + "ERROR:" + RESET + " this movement is not allowed and " + RED + "was not proceed" + RESET + ", see above for details";
    }

    public static String errorInvalidString(String stringFrom, String stringTo) {
        return RED + "ERROR:" + RESET + " Cannot move as direction (from or to) is/are not valid... " +
                "double check from " + RED + stringFrom + RESET + " and to " + RED + stringTo + RESET + " values";
    }

    public static void printPieces(long lightPieces, long darkPieces) {
        System.out.printf("Light = #%2d piece(s)\n", lightPieces);
        System.out.printf("Dark  = #%2d piece(s)\n", darkPieces);
    }

}
