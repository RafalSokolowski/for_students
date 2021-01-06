package day20201220.end_project.checker;

import day20201220.end_project.figure.OneFigure;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CheckerboardTest {

    Checkerboard board;
    private Map<Position, OneFigure> expected;

    @Before
    public void setup() {
        board = new Checkerboard();
        board.start();

        expected = new HashMap<>() {{
            put(new Position(4, 5), new OneFigure("101100101"));
            put(new Position(0, 7), new OneFigure("111000111"));
        }};

    }

    @Test
    public void testGameLightWins() {
        board.movePieceByString("6", "D5");
        board.printBoard();
        board.movePieceByString("C66", "D5");
        board.printBoard();
        board.movePieceByString("c6", "D5");
        board.printBoard();
        board.movePieceByString("Z6", "D5");
        board.printBoard();
        board.movePieceByString("CC6", "D5");
        board.printBoard();
        board.movePieceByString("C6", "D5");
        board.printBoard();
        board.movePieceByString("E6", "F5");
        board.printBoard();
        board.movePieceByString("B3", "C4");
        board.printBoard();
        board.movePieceByString("E6", "F5");
        board.printBoard();
        board.movePieceByString("D5", "E4");
        board.printBoard();
        board.movePieceByString("D5", "B3");
        board.printBoard();
        board.movePieceByString("A2", "C4");
        board.printBoard();
        board.movePieceByString("G6", "H5");
        board.printBoard();
        board.movePieceByString("B1", "A2");
        board.printBoard();
        board.movePieceByString("H7", "G6");
        board.printBoard();
        board.movePieceByString("C2", "B3");
        board.printBoard();
        board.movePieceByString("G8", "H7");
        board.printBoard();
        board.movePieceByString("D1", "C2");
        board.printBoard();
        board.movePieceByString("E6", "D5");
        board.printBoard();
        board.movePieceByString("C4", "E6");
        board.printBoard();
        board.movePieceByString("E6", "G8");
        board.printBoard();
        board.movePieceByString("D7", "E6");
        board.printBoard();
        board.movePieceByString("G8", "F7");
        board.printBoard();
        board.movePieceByString("G8", "C4");
        board.printBoard();
        board.movePieceByString("C4", "D5");
        board.printBoard();
        board.movePieceByString("H5", "G4");
        board.printBoard();
        board.movePieceByString("F3", "H5");
        board.printBoard();
        board.movePieceByString("H5", "F7");
        board.printBoard();
        board.movePieceByString("E8", "G6");
        board.printBoard();
        board.movePieceByString("B3", "A4");
        board.printBoard();
        board.movePieceByString("G6", "H5");
        board.printBoard();
        board.movePieceByString("A4", "B5");
        board.printBoard();
        board.movePieceByString("C8", "D7");
        board.printBoard();
        board.movePieceByString("C2", "B3");
        board.printBoard();
        board.movePieceByString("B7", "C6");
        board.printBoard();
        board.movePieceByString("C4", "G8");
        board.printBoard();
        board.movePieceByString("A6", "C4");
        board.printBoard();
        board.movePieceByString("B3", "D5");
        board.printBoard();
        board.movePieceByString("D5", "B7");
        board.printBoard();
        board.movePieceByString("A8", "C6");
        board.printBoard();
        board.movePieceByString("A2", "B3");
        board.printBoard();
        board.movePieceByString("C6", "D5");
        board.printBoard();
        board.movePieceByString("G8", "C4");
        board.printBoard();
        board.movePieceByString("D7", "C6");
        board.printBoard();
        board.movePieceByString("H3", "G4");
        board.printBoard();
        board.movePieceByString("H5", "F3");
        board.printBoard();
        board.movePieceByString("F3", "D1");
        board.printBoard();
        board.movePieceByString("D1", "A4");
        board.printBoard();
        board.movePieceByString("D1", "A4");
        board.printBoard();
        board.movePieceByString("C4", "A6");
        board.printBoard();
        board.movePieceByString("A4", "B3");
        board.printBoard();
        board.movePieceByString("G2", "H3");
        board.printBoard();
        board.movePieceByString("B3", "A4");
        board.printBoard();
        board.movePieceByString("H3", "G4");
        board.printBoard();
        board.movePieceByString("A4", "B3");
        board.printBoard();
        board.movePieceByString("G4", "H5");
        board.printBoard();
        board.movePieceByString("B3", "A4");
        board.printBoard();
        board.movePieceByString("F1", "E2");
        board.printBoard();
        board.movePieceByString("C6", "D5");
        board.printBoard();
        board.movePieceByString("D3", "C2");
        board.printBoard();
        board.movePieceByString("D3", "E4");
        board.printBoard();
        board.movePieceByString("D5", "F3");
        board.printBoard();
        board.movePieceByString("F3", "D1");
        board.printBoard();
        board.movePieceByString("H1", "G2");
        board.printBoard();
        board.movePieceByString("A4", "B5");
        board.printBoard();
        board.movePieceByString("A6", "E2");
        board.printBoard();
        board.movePieceByString("D1", "F3");
        board.printBoard();
        board.movePieceByString("F3", "H1");
        board.printBoard();
        board.movePieceByString("H5", "G6");
        board.printBoard();
        board.movePieceByString("H7", "F5");
        board.printBoard();

        Assert.assertEquals(
                board.getPlayers().entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toList()),
                expected.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toList())
        );
    }

    @Test
    public void testIncorrectMovements() {

        boolean flag1 = board.movePieceByString("6", "D5");
        boolean flag2 = board.movePieceByString("C66", "D5");
        boolean flag3 = board.movePieceByString("c6", "D5");
        boolean flag4 = board.movePieceByString("Z6", "D5");
        boolean flag5 = board.movePieceByString("CC6", "D5");

        Assert.assertFalse(flag1);
        Assert.assertFalse(flag2);
        Assert.assertFalse(flag3);
        Assert.assertFalse(flag4);
        Assert.assertFalse(flag5);
    }

    @Test
    public void testCorrectMovements() {

        boolean flag1 = board.movePieceByString("C6", "D5");
        boolean flag2 = board.movePieceByString("B3", "C4");
        boolean flag3 = board.movePieceByString("D5", "B3");
        boolean flag4 = board.movePieceByString("A2", "C4");
        boolean flag5 = board.movePieceByString("G6", "H5");

        Assert.assertTrue(flag1);
        Assert.assertTrue(flag2);
        Assert.assertTrue(flag3);
        Assert.assertTrue(flag4);
        Assert.assertTrue(flag5);
    }

}
