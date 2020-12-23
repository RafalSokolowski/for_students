package day20201219.binary_tree_task;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BinaryTree {

    private final String filePathAndName;
    private Node[] lastNodes;

    public BinaryTree(String filePathAndName) {
        this.filePathAndName = filePathAndName;
        this.lastNodes = new Node[0];
    }

    public List<String> build() {
        try (Scanner scanner = new Scanner(new File(filePathAndName))) {

            String[] word;
            String[] directions;
            String letter;

            Node root = new Node();

            while (scanner.hasNextLine()) {

                word = scanner.nextLine().split(" ");

                if (word.length == 1) {
                    root.setValue(word[0]);
                } else {
                    addNodeRecursively(root, word[0], word[1]);
                }
            }

            System.out.println(root);
//            System.out.println(BLUE + "LAST NODES 1:" + RESET);
//            Arrays.stream(lastNodes).forEach(System.out::println);
//            System.out.println();
//            System.out.println(BLUE + "LAST NODES 2:" + RESET);
//            Arrays.stream(cleanLastNodes(lastNodes)).forEach(System.out::println);

//            for (Node n : lastNodes) {
//                if (n.getLeftNode() == null && n.getRightNode() == null) {
//
//                }
//            }

//            getAllValuesInThePath(root);

//            traverse(root);

            traversByRav(root, "");

//            takeWordsFromBinaryTreeString(root.toString());

        } catch (FileNotFoundException e) {
            System.err.println("File was not found (please check the file path and name): " + e.getMessage());
        }

        return null;
    }

    private void addNodeRecursively(Node node, String directions, String value) {
        if (directions.isEmpty()) {
            node.setValue(value);
            addToNodesArrayAndIncreaseSize(node, lastNodes);
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

    private void addToNodesArrayAndIncreaseSize(Node node, Node[] nodes) {
        nodes = Arrays.copyOf(nodes, nodes.length + 1);
        nodes[nodes.length - 1] = node;
    }

    public Node[] cleanLastNodes (Node[] nodes) {
        Node[] lastNodes = new Node[0];
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].getLeftNode() != null || nodes[i].getRightNode() != null) {
                addToNodesArrayAndIncreaseSize(nodes[i],lastNodes);
            }
        }
        return lastNodes;
    }

//    private String getAllValuesInThePath(Node node) {
//        String result = "";
//        while (node.getLeftNode() != null || node.getRightNode() != null) {
////            result += node.getValue();
//            if (node.getLeftNode() != null)
//                result += getAllValuesInThePath(node.getLeftNode());
//            if (node.getRightNode() != null)
//                result += getAllValuesInThePath(node.getRightNode());
//        }
//        System.out.println(RED + result + RESET);
//        return result;
//    }


    public void traverse(Node node) {
        if (node == null) {
            return;
        } else {
            // display values to the left of current node
            traverse(node.getLeftNode());
            // display current node
            System.out.println(node.getValue());
            // display values to the right of current node
            traverse(node.getRightNode());
        }
    }

    public void traversByRav(Node node, String path) {
        path += node.getValue();
        if (node == null) {
            System.err.println(path);
            return;
        }
        if (node.getLeftNode() != null) {
            traversByRav(node.getLeftNode(), path);
        }
        if (node.getRightNode() != null) {
            traversByRav(node.getRightNode(), path);
        }
        if (node.getLeftNode() == null && node.getRightNode() == null) {
            System.err.println(path);
        }
    }


    private void takeWordsFromBinaryTreeString(String string) {
        String[] strings = string.split("Left:null, Right:null");
        System.err.println(Arrays.toString(strings));
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



