package day20201220.end_project.checker;

import day20201220.end_project.figure.OnTheBoard;
import day20201220.end_project.figure.OneFigure;
import day20201220.end_project.utils.Print;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static day20201220.end_project.utils.Const.*;

@Getter
public class Checkerboard {

    private Map<Position, OnTheBoard> board;
    private Map<Position, OneFigure> players;
    private Map<Position, Map<Position, List<Position>>> positionsFrom_ToBeRemoved_MandatoryPositionsTo;
    Movement movement;

    public Checkerboard() {
        this.board = new HashMap<>(CHECKERBOARD_SIZE * CHECKERBOARD_SIZE);
        this.players = new HashMap<>(CHECKERBOARD_SIZE * CHECKERBOARD_SIZE);
        this.positionsFrom_ToBeRemoved_MandatoryPositionsTo = new HashMap<>();

        movement = new Movement(board, players, positionsFrom_ToBeRemoved_MandatoryPositionsTo);
    }

    public void start() {
        new Initialization(board, players).setup();
    }

    public boolean movePieceByString(String stringFrom, String stringTo) {
        return movement.moveByString(stringFrom, stringTo);
    }

    public void printBoard() {
        Print.checkerboard(board, players);
    }

}
