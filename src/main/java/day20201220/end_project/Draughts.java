package day20201220.end_project;

import day20201220.end_project.board.Board;
import day20201220.end_project.board.Position;
import day20201220.end_project.figure.OneFigure;
import day20201220.end_project.figure.SixFigures;

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
        Player black = board.initializeBlackPlayers();
        Player white = board.initializeWhitePlayer();

        System.out.println("\n0. Phase - start no movements");
        board.printBoard();

        System.out.println("\n1. Phase - light B3 -> C4");
        board.movePiece("B3","C4");
        board.printBoard();

        System.out.println("\n2. Phase - dark E6 -> F5");
        board.movePiece("E6","F5");
        board.printBoard();

        System.out.println("\n3. Phase - light C4 -> D5");
        board.movePiece("C4","D5");
        board.printBoard();

        System.out.println("\n4. Phase - dark C6 -> E4");
        board.movePiece("C6","E4");
        board.printBoard();

        System.out.println("\n5. Phase - intentional mistake - capturing is mandatory");
        board.movePiece("F3","G4");
        board.printBoard();

        System.out.println("\n5. Phase - repeat - light F3 -> D5");
        board.movePiece("F3","D5");
        board.printBoard();

        System.out.println("\n6. Phase - dark D7 -> C6");
        board.movePiece("D7","C6");
        board.printBoard();

        System.out.println("\n7. Phase");
        board.movePiece("D5","E6");
        board.printBoard();

        System.out.println("\n8. Phase");
        board.movePiece("F5","D7");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePiece("D3","E4");
        board.printBoard();

        System.out.println("\n10. Phase");
        board.movePiece("A6","B5");
        board.printBoard();

        System.out.println("\n11. Phase");
        board.movePiece("C2","D3");
        board.printBoard();

        System.out.println("\n12. Phase");
        board.movePiece("B5","A4");
        board.printBoard();

        System.out.println("\n12. Phase");
        board.movePiece("E4","F5");
        board.printBoard();

        System.out.println("\n13. Phase");
        board.movePiece("G6","E4");
        board.printBoard();

        System.out.println("\n14. Phase");
        board.movePiece("E4","C2");
//        board.movePiece("D3","F5"); /// <- błąd : (
        board.printBoard();

        System.out.println("\n15. Phase");
        board.movePiece("B1","D3");
        board.printBoard();

        System.out.println("\n16. Phase");
        board.movePiece("B7","A6");
        board.printBoard();

        System.out.println("\n17. Phase");
        board.movePiece("D1","C2");
        board.printBoard();

        System.out.println("\n18. Phase");
        board.movePiece("D3","E4");
        board.printBoard();

        System.out.println("\n18. Phase");
        board.movePiece("C6","B5");
        board.printBoard();

        System.out.println("\n19. Phase");
        board.movePiece("H3","G4");
        board.printBoard();

        System.out.println("\n19. Phase"); // <- białe ruszają się dwa razy !!!!
        board.movePiece("G4","H5");
        board.printBoard();

        System.out.println("\n20. Phase"); // <- białe ruszają się trzy razy !!!!
        board.movePiece("H5","G6");
        board.printBoard();

        System.out.println("\n21. Phase");
        board.movePiece("H7","F5");
        board.printBoard();

        System.out.println("\n22. Phase");
        board.movePiece("F5","D3");
        board.printBoard();

        System.out.println("\n22. Phase");
        board.movePiece("D3","B1");
        board.printBoard();

//        System.out.println(playerBlack.getFirstSix());
//        board.placeFiguresFromLong(playerBlack.getFirstSix());
//
//        OneFigure test0 = new OneFigure("100110111");
//        test0.print();
//        OneFigure test1 = new OneFigure(Long.parseLong("100110111",2));
//        test1.print();
//
//        System.out.println();
//        SixFigures test2 = new SixFigures("100011001100011011100011101100011111100010001100010011");
//        test2.print();
//
//        System.out.println();
//        SixFigures test3 = new SixFigures(9906294496305939L);
//        test3.print();

    }
}
