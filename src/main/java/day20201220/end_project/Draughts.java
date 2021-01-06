package day20201220.end_project;

import day20201220.end_project.board.Board;

public class Draughts {
    public static void main(String[] args) {
//        char whiteField = '\u2B1B';
//        char blackField = '\u2B1C';
//        char whiteFigure = '\u2659';
//        char blackFigure = '\u265F';
//        char whiteQueen = '\u2655';
//        char blackQueen = '\u265B';
//
//        System.out.println(whiteField);
//        System.out.println(blackField);
//        System.out.println(whiteFigure);
//        System.out.println(blackFigure);
//        System.out.println(whiteQueen);
//        System.out.println(blackQueen);

        // _stan (0-zbity, 1-w grze) | _figura (0-pion, 1-damka) | _kolor (0-czarny, 1-białe) | ___ Y | ___ X

//        System.out.println(Long.toBinaryString(100));
//        System.out.println(firstSix);


//        OneFigure empty = new OneFigure(0, 0, 0, new Position(0, 0));
//
//        OneFigure black01 = new OneFigure(1, 0, 0, new Position(2, 1));
//        OneFigure black02 = new OneFigure(1, 0, 0, new Position(2, 3));
//        OneFigure black03 = new OneFigure(1, 0, 0, new Position(2, 5));
//        OneFigure black04 = new OneFigure(1, 0, 0, new Position(2, 7));
//        OneFigure black05 = new OneFigure(1, 0, 0, new Position(1, 0));
//        OneFigure black06 = new OneFigure(1, 0, 0, new Position(1, 2));
//
//        OneFigure black07 = new OneFigure(1, 0, 0, new Position(1, 4));
//        OneFigure black08 = new OneFigure(1, 0, 0, new Position(1, 6));
//        OneFigure black09 = new OneFigure(1, 0, 0, new Position(0, 1));
//        OneFigure black10 = new OneFigure(1, 0, 0, new Position(0, 3));
//        OneFigure black11 = new OneFigure(1, 0, 0, new Position(0, 5));
//        OneFigure black12 = new OneFigure(1, 0, 0, new Position(0, 7));
//
//        SixFigures black_01_06 = new SixFigures(black01, black02, black03, black04, black05, black06);
//        SixFigures black_07_12 = new SixFigures(black07, black08, black09, black10, black11, black12);


//        Player playerBlack = new Player(
//                "Mr. Black",
//                Long.parseLong(black_01_06.toString(), 2),
//                Long.parseLong(black_07_12.toString(), 2)
//        );

//        System.out.println(playerBlack);

        Board board = new Board();
        board.initializeBoard();
        Player black = board.initiateDarkPlayer();
        Player white = board.initiateLightPlayer();

        System.out.println("\n0. Phase - start no movements");
        board.printBoard();

        System.out.println("\n1. Phase - light C6 -> D5");
        board.movePieceByString("C6", "D5");
        board.printBoard();

        System.out.println("\n2. Phase - intentional mistake (should be dark not light) - E6 -> F5");
        board.movePieceByString("E6", "F5");
        board.printBoard();

        System.out.println("\n3. Phase - dark B3 -> C4");
        board.movePieceByString("B3", "C4");
        board.printBoard();

        System.out.println("\n4. Phase - intentional mistake (capturing is mandatory - wrong from) light E6 -> F5");
        board.movePieceByString("E6", "F5");
        board.printBoard();

        System.out.println("\n5. Phase - intentional mistake (capturing is mandatory - wrong to) - dark D5 -> E4");
        board.movePieceByString("D5", "E4");
        board.printBoard();

        System.out.println("\n6. Phase  - dark D5 -> B3");
        board.movePieceByString("D5", "B3");
        board.printBoard();

        System.out.println("\n7. Phase  -  A2 -> C4");
        board.movePieceByString("A2", "C4");
        board.printBoard();

        System.out.println("\n8. Phase  -  G6 -> H5");
        board.movePieceByString("G6", "H5");
        board.printBoard();

        System.out.println("\n8. Phase  -  B1 -> A2");
        board.movePieceByString("B1", "A2");
        board.printBoard();

        System.out.println("\n8. Phase  -  H7 -> G6");
        board.movePieceByString("H7", "G6");
        board.printBoard();

        System.out.println("\n8. Phase  -  C2 -> B3");
        board.movePieceByString("C2", "B3");
        board.printBoard();

        System.out.println("\n8. Phase  -  G8 -> H7");
        board.movePieceByString("G8", "H7");
        board.printBoard();

        System.out.println("\n8. Phase  -  D1 -> C2");
        board.movePieceByString("D1", "C2");
        board.printBoard();

        System.out.println("\n8. Phase  -  E6 -> D5");
        board.movePieceByString("E6", "D5");
        board.printBoard();

        System.out.println("\n8. Phase  -  C4 -> E6");
        board.movePieceByString("C4", "E6");
        board.printBoard();

        System.out.println("\n8. Phase  -  E6 -> G8");
        board.movePieceByString("E6", "G8");
        board.printBoard();

        System.out.println("\n8. Phase  -  D7 -> E6");
        board.movePieceByString("D7", "E6");
        board.printBoard();

        System.out.println("\n9. Phase - intentional mistake as capturing by dame is mandatory -  G8 -> F7");
        board.movePieceByString("G8", "F7");
        board.printBoard();

        System.out.println("\n9. Phase  -  G8 -> C4");
        board.movePieceByString("G8", "C4");
        board.printBoard();

        System.out.println("\n9. Phase  - intentional mistake, its Light turn  C4 -> D5");
        board.movePieceByString("C4", "D5");
        board.printBoard();

        System.out.println("\n9. Phase  -  H5 -> G4");
        board.movePieceByString("H5", "G4");
        board.printBoard();

        System.out.println("\n9. Phase  -  F3 -> H5");
        board.movePieceByString("F3", "H5");
        board.printBoard();

        System.out.println("\n9. Phase  -  H5 -> F7");
        board.movePieceByString("H5", "F7");
        board.printBoard();

        System.out.println("\n9. Phase  -  E8 -> G6");
        board.movePieceByString("E8", "G6");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("B3", "A4");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("G6", "H5");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("A4", "B5");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("C8", "D7");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("C2", "B3");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("B7", "C6");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("C4", "G8");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("A6", "C4");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("B3", "D5");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("D5", "B7");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("A8", "C6");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("A2", "B3");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("C6", "D5");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("G8", "C4");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("D7", "C6");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("H3", "G4");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("H5", "F3");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("F3", "D1");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("D1", "A4");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("D1", "A4");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("C4", "A6");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("A4", "B3");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("G2", "H3");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("B3", "A4");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("H3", "G4");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("A4", "B3");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("G4", "H5");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("B3", "A4");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("F1", "E2");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("C6", "D5");
        board.printBoard();

        System.out.println("\n9. Phase - intentional mistake cannot move backwords : )");
        board.movePieceByString("D3", "C2");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("D3", "E4");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("D5", "F3");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("F3", "D1");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("H1", "G2");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("A4", "B5");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("A6", "E2");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("D1", "F3");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("F3", "H1");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("H5", "G6");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("H7", "F5");
        board.printBoard();



    }
}
