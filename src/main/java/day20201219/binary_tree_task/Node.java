package day20201219.binary_tree_task;

import lombok.Getter;
import lombok.Setter;

import static day20201219.binary_tree_task.TerminalColors.*;

@Setter
@Getter
public class Node {
    private String value;
    private Node leftNode;
    private Node rightNode;

    public Node(String value) {
        this.value = value;
        this.leftNode = null;
        this.rightNode = null;
    }

    public Node() {
        this.value = "empty";
        this.leftNode = null;
        this.rightNode = null;
    }


    @Override
    public String toString() {
//        return value + "\n" + "L:" + leftNode + ", R:" + rightNode;
        return RESET+"(Value: " + BLUE + value + RESET + ", Left:" + RED+leftNode+RESET + ", Right:" + RED+rightNode+RESET + ")";
    }
}
