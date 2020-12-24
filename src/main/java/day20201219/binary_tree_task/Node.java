package day20201219.binary_tree_task;

import lombok.Getter;
import lombok.Setter;

import static day20201219.binary_tree_task.Const.*;

@Setter
@Getter
public class Node {
    private String value;
    private Node leftNode;
    private Node rightNode;

    public Node() {
        this.value = "empty";
        this.leftNode = null;
        this.rightNode = null;
    }

    @Override
    public String toString() {
        return RESET +
                "(Value: " + BLUE + value + RESET +
                ", Left:" + RED + leftNode + RESET +
                ", Right:" + RED + rightNode + RESET + ")";
    }
}
