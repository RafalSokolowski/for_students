package day20201219.binary_tree_task;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import static day20201219.binary_tree_task.Const.*;

public class BinaryTree {

    private final String filePathAndName;
    private String[] binaryTreeWords;

    public BinaryTree(String filePathAndName) {
        this.filePathAndName = filePathAndName;
        this.binaryTreeWords = new String[0];
    }

    public void calculate() {
        try (Scanner scanner = new Scanner(new File(filePathAndName))) {

            String[] word;
            Node binaryTree = new Node();

            while (scanner.hasNextLine()) {
                word = scanner.nextLine().split(" ");

                if (word.length == 1)
                    binaryTree.setValue(word[0]);
                else
                    addNodeRecursively(binaryTree, word[0], word[1]);
            }

            traversBinaryTreeTopDown(binaryTree, "");

            System.out.println("1. BinaryTree:     " + binaryTree);
            System.out.println("2. Words down top: " + Arrays.toString(binaryTreeWords));
            System.out.println("3. The winner is:  " + BLUE + getAlphabeticalOldestWord(binaryTreeWords) + RESET);

        } catch (FileNotFoundException e) {
            System.err.println("File was not found (please check the file path and name): " + e.getMessage());
        }
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

    public void traversBinaryTreeTopDown(Node node, String word) {
        word += node.getValue();

        if (node.getLeftNode() != null) {
            traversBinaryTreeTopDown(node.getLeftNode(), word);
        }
        if (node.getRightNode() != null) {
            traversBinaryTreeTopDown(node.getRightNode(), word);
        }
        if (node.getLeftNode() == null && node.getRightNode() == null) {
            addToBinaryTreeWords(word);
        }
    }

    private void addToBinaryTreeWords(String word) {
        binaryTreeWords = Arrays.copyOf(binaryTreeWords, binaryTreeWords.length + 1);
        binaryTreeWords[binaryTreeWords.length - 1] = new StringBuilder(word).reverse().toString();
    }

    private String getAlphabeticalOldestWord(String[] words) {
        Arrays.sort(words);
        return words[words.length - 1];
    }

}