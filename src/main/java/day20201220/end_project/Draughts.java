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

        System.out.println("\n1. Phase - light B3 -> C4");
        board.movePieceByString("B3","C4");
        board.printBoard();

        System.out.println("\n2. Phase - dark E6 -> F5");
        board.movePieceByString("E6","F5");
        board.printBoard();

        System.out.println("\n3. Phase - light C4 -> D5");
        board.movePieceByString("C4","D5");
        board.printBoard();

        System.out.println("\n4. Phase - dark C6 -> E4");
        board.movePieceByString("C6","E4");
        board.printBoard();

        System.out.println("\n5. Phase - intentional mistake - capturing is mandatory");
        board.movePieceByString("F3","G4");
        board.printBoard();

        System.out.println("\n5. Phase - repeat - light F3 -> D5");
        board.movePieceByString("F3","D5");
        board.printBoard();

        System.out.println("\n6. Phase - dark D7 -> C6");
        board.movePieceByString("D7","C6");
        board.printBoard();

        System.out.println("\n7. Phase D5 -> E6");
        board.movePieceByString("D5","E6");
        board.printBoard();

        System.out.println("\n8. Phase F5 -> D7");
        board.movePieceByString("F5","D7");
        board.printBoard();

        System.out.println("\n9. Phase");
        board.movePieceByString("D3","E4");
        board.printBoard();

        System.out.println("\n10. Phase");
        board.movePieceByString("A6","B5");
        board.printBoard();

        System.out.println("\n11. Phase");
        board.movePieceByString("C2","D3");
        board.printBoard();

        System.out.println("\n12. Phase");
        board.movePieceByString("B5","A4");
        board.printBoard();

        System.out.println("\n12. Phase");
        board.movePieceByString("E4","F5");
        board.printBoard();

        System.out.println("\n13. Phase");
        board.movePieceByString("G6","E4");
        board.printBoard();

        System.out.println("\n14. Phase");
        board.movePieceByString("E4","C2");
//        board.movePiece("D3","F5"); /// <- błąd : (
        board.printBoard();

        System.out.println("\n15. Phase");
        board.movePieceByString("B1","D3");
        board.printBoard();

        System.out.println("\n16. Phase");
        board.movePieceByString("B7","A6");
        board.printBoard();

        System.out.println("\n17. Phase");
        board.movePieceByString("D1","C2");
        board.printBoard();

        System.out.println("\n18. Phase");
        board.movePieceByString("D3","E4");
        board.printBoard();

        System.out.println("\n18. Phase");
        board.movePieceByString("C6","B5");
        board.printBoard();

        System.out.println("\n19. Phase");
        board.movePieceByString("H3","G4");
        board.printBoard();

        System.out.println("\n19. Phase"); // <- białe ruszają się dwa razy !!!!
        board.movePieceByString("G4","H5");
        board.printBoard();

        System.out.println("\n20. Phase"); // <- białe ruszają się trzy razy !!!!
        board.movePieceByString("H5","G6");
        board.printBoard();

        System.out.println("\n21. Phase");
        board.movePieceByString("H7","F5");
        board.printBoard();

        System.out.println("\n22. Phase");
        board.movePieceByString("F5","D3");
        board.printBoard();

        System.out.println("\n22. Phase");
        board.movePieceByString("D3","B1");
        board.printBoard();

        System.out.println("\n23. Phase - intentional mistake (A2 -> A3)");
        board.movePieceByString("A2","A3");
        board.printBoard();

        System.out.println("\n24. Phase - dame movement");
        board.movePieceByString("B1","E4");
        board.printBoard();

        System.out.println("\n25. Phase - dame movement - intentional mistake (E4 -> D4");
        board.movePieceByString("E4","D4");
        board.printBoard();

        System.out.println("\n26. Phase - dame movement - intentional mistake (E4 -> D4");
        board.movePieceByString("E4","D4");
        board.printBoard();

        System.out.println("\n27. Phase - E4 -> B7");
        board.movePieceByString("E4","B7");
        board.printBoard();

        System.out.println("\n27. Phase - intentional mistake B7 -> E6");
        board.movePieceByString("B7","E6");
        board.printBoard();

        System.out.println("\n28. Phase");
        board.movePieceByString("E2","F3");
        board.printBoard();

        System.out.println("\n29. Phase");
        board.movePieceByString("F1","E2");
        board.printBoard();

        System.out.println("\n30. Phase");
        board.movePieceByString("B5","C4");
        board.printBoard();

        System.out.println("\n31. Phase");
        board.movePieceByString("C4","B3");
        board.printBoard();

        System.out.println("\n32. Phase");
        board.movePieceByString("A2","C4");
        board.printBoard();

        System.out.println("\n33. Phase");
        board.movePieceByString("F3","G4");
        board.printBoard();

        System.out.println("\n34. Phase");
        board.movePieceByString("C4","D5");
        board.printBoard();

        System.out.println("\n35. Phase - intentional mistake - capturing by dame is mandatory");
        board.movePieceByString("B7","C6");
        board.printBoard();

        System.out.println("\n36. Phase - intentional mistake - capturing by dame is mandatory in a way to capture as many as possible !");
        board.movePieceByString("B7","E4");
        board.printBoard();

        System.out.println("\n37. Phase ");
        board.movePieceByString("B7","F3");
        board.printBoard();

        System.out.println("\n38. Phase ");  // TODO: zablokować zbijanie spowrotem po tej samej diagonali ???
        board.movePieceByString("F3","D1");
        board.printBoard();

        System.out.println("\n39_diff. Phase ");  /// zmiana koncepcji , patrz TO-DO powyżej
        board.movePieceByString("D1","H5");
        board.printBoard();

        System.out.println("\n39_diff. Phase ");  /// zmiana koncepcji , patrz TO-DO powyżej
        board.movePieceByString("H5","D1");
        board.printBoard();

        System.out.println("\n39. Phase ");
        board.movePieceByString("F7","E6");
        board.printBoard();

        System.out.println("\n40. Phase ");
        board.movePieceByString("C8","B7");
        board.printBoard();

        System.out.println("\n41. Phase ");
        board.movePieceByString("A6","B5");
        board.printBoard();

        System.out.println("\n43. Phase "); // nowy kierunek try
        board.movePieceByString("D1","B3");
        board.printBoard();

        System.out.println("\n44. Phase ");
        board.movePieceByString("G2","H3");
        board.printBoard();

        System.out.println("\n45. Phase ");
        board.movePieceByString("H1","G2");
        board.printBoard();

        System.out.println("\n46. Phase ");
        board.movePieceByString("G2","F3");
        board.printBoard();

        System.out.println("\n47. Phase ");
        board.movePieceByString("F3","G4");
        board.printBoard();

        System.out.println("\n48. Phase ");
        board.movePieceByString("E6","F5");
        board.printBoard();

        System.out.println("\n49. Phase ");
        board.movePieceByString("G4","E6");
        board.printBoard();

        System.out.println("\n50. Phase ");
        board.movePieceByString("E6","C8");
        board.printBoard();

        System.out.println("\n51. Phase ");
        board.movePieceByString("C8","A6");
        board.printBoard();

        System.out.println("\n52. Phase ");
        board.movePieceByString("A6","C4");
        board.printBoard();

        System.out.println("\n53. Phase ");
        board.movePieceByString("C4","A2");
        board.printBoard();

        System.out.println("\n53. Phase ");
        board.movePieceByString("E8","F7");
        board.printBoard();

        System.out.println("\n53. Phase ");
        board.movePieceByString("A2","B1");
        board.printBoard();

        System.out.println("\n53. Phase ");
        board.movePieceByString("A8","B7");
        board.printBoard();

        System.out.println("\n53. Phase ");
        board.movePieceByString("B7","C6");
        board.printBoard();

        System.out.println("\n53. Phase ");
        board.movePieceByString("C6","D5");
        board.printBoard();

        System.out.println("\n53. Phase ");
        board.movePieceByString("D5","C4");
        board.printBoard();

        System.out.println("\n53. Phase ");
        board.movePieceByString("B1","A2");
        board.printBoard();

        System.out.println("\n53. Phase ");
        board.movePieceByString("F7","E6");
        board.printBoard();

//        System.out.println("\n43. Phase ");
//        board.movePieceByString("B7","F3");
//        board.printBoard();
//
//        System.out.println("\n44. Phase ");
//        board.movePieceByString("G4","E6");
//        board.printBoard();
//
//        System.out.println("\n45. Phase ");
//        board.movePieceByString("E6","C8");
//        board.printBoard();
//
//        System.out.println("\n46. Phase ");
//        board.movePieceByString("C8","A6");
//        board.printBoard();
//
//        System.out.println("\n47. Phase - A6 -> F1");
//        board.movePieceByString("A6","F1");
//        board.printBoard();
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
