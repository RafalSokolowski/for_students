package day20201220.end_project.checker;

import day20201220.end_project.utils.Messages;
import day20201220.end_project.utils.Print;

import java.util.Scanner;

import static day20201220.end_project.utils.Const.RED;
import static day20201220.end_project.utils.Const.RESET;

public class Checkers {

    Checkerboard board;

    public Checkers() {
        this.board = new Checkerboard();
        board.start();
        board.printBoard();
    }

    public void start() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("\nProvide coordinates in format C6D5 (which means from C6 to D5) and press enter (exit to leave): ");
        String coordinates = scanner.nextLine().toUpperCase();

        while (!coordinates.equalsIgnoreCase("exit")) {

            if (coordinates.length() != 4) {
                System.out.println(RED + "Wrong coordinates, try again" + RESET);
            } else {
                board.movePieceByString(coordinates.substring(0,2), coordinates.substring(2,4));
                board.printBoard();
            }
            System.out.print("\nProvide coordinates in format C6D5 (which means from C6 to D5) and press enter (exit to leave): ");
            coordinates = scanner.nextLine().toUpperCase();
        }

    }

}
