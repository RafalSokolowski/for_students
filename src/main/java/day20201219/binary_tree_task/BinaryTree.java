package day20201219.binary_tree_task;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static day20201219.binary_tree_task.TerminalColors.*;

public class BinaryTree {

    private final String filePathAndName;
    private String[] wordsDownUp;

    public BinaryTree(String filePathAndName) {
        this.filePathAndName = filePathAndName;
        this.wordsDownUp = new String[0];
    }

    public List<String> build() {
        try (Scanner scanner = new Scanner(new File(filePathAndName))) {

            String[] word;

            Node root = new Node();

            while (scanner.hasNextLine()) {

                word = scanner.nextLine().split(" ");

                if (word.length == 1) {
                    root.setValue(word[0]);
                } else {
                    addNodeRecursively(root, word[0], word[1]);
                }
            }

            System.out.println("1. BinaryTree: " + root);

            traversByRav(root, "");

            System.out.println("2. Words:      " + Arrays.toString(wordsDownUp));

            System.out.println("3. The winner: " + BLUE+selectOldestLexicographicallyReversedWord(wordsDownUp)+RESET);

        } catch (FileNotFoundException e) {
            System.err.println("File was not found (please check the file path and name): " + e.getMessage());
        }

        return null;
    }

    private void addNodeRecursively(Node node, String directions, String value) {
        if (directions.isEmpty()) {
            node.setValue(value);
            return;
        }
        if ("r".equalsIgnoreCase(directions.substring(0, 1))) {
            if (node.getRightNode() == null)
                node.setRightNode(new Node());
            addNodeRecursively(node.getRightNode(), directions.substring(1), value);
        } else {
            if (node.getLeftNode() == null)
                node.setLeftNode(new Node());
            addNodeRecursively(node.getLeftNode(), directions.substring(1), value);
        }
    }

    public void traversByRav(Node node, String word) {
        word += node.getValue();

        if (node.getLeftNode() != null) {
            traversByRav(node.getLeftNode(), word);
        }
        if (node.getRightNode() != null) {
            traversByRav(node.getRightNode(), word);
        }
        if (node.getLeftNode() == null && node.getRightNode() == null) {
            addTwoWordsDownUp(word);
        }
    }

    private void addTwoWordsDownUp(String word) {
        wordsDownUp = Arrays.copyOf(wordsDownUp, wordsDownUp.length + 1);
        wordsDownUp[wordsDownUp.length - 1] = new StringBuilder(word).reverse().toString();
    }

    private String selectOldestLexicographicallyReversedWord(String[] words) {
        Arrays.sort(words);
        return words[words.length-1];
    }

}

//        5 pkt - za pełną poprawnośc algorytmiczną
//        1 pkt - estetyka kodu
//        2 pkt - optymalizacja kodu (minimalizacja rozwiązań, szybkośc algorytmu, złożoność pamięciowa i czasowa)
//
//        Odczytaj z pliku poprawnie zbudowane drzewo binarne, zbuduj je, a następnie odpowiedz na pytanie jakie jest
//        najstarsze słowo które powstało.
//
//        Konstrukcja pliku:
//
//        RR W
//        R L
//        RL T
//        H
//        L S
//        LL T
//        LR U
//        LRR P
//
//        Oznaczenia: L - lewe dziecko, R - prawe dziecko, luzna liczba to etykieta naszego węzła



