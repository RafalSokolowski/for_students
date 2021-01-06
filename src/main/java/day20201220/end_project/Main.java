package day20201220.end_project;

import day20201220.end_project.checker.Checkerboard;

public class Main {
    public static void main(String[] args) {

        Checkerboard board = new Checkerboard();
        board.start();

        System.out.println("\n0. Phase - start no movements");
        board.printBoard();

        System.out.println("\n0. Phase intentional mistake inappropriate string position");
        board.movePieceByString("6", "D5");
        board.printBoard();

        System.out.println("\n0. Phase intentional mistake inappropriate string position");
        board.movePieceByString("C66", "D5");
        board.printBoard();

        System.out.println("\n0. Phase intentional mistake inappropriate string position");
        board.movePieceByString("c6", "D5");
        board.printBoard();

        System.out.println("\n0. Phase intentional mistake inappropriate string position");
        board.movePieceByString("Z6", "D5");
        board.printBoard();

        System.out.println("\n0. Phase intentional mistake inappropriate string position");
        board.movePieceByString("CC6", "D5");
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
