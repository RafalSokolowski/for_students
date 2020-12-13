package day20201108.homework;

import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@Getter
public class Fish {

    @ToString.Exclude
    public static final String[] occurrences = {"Big Sea", "Small Sea", "Medium Sea"};
    @ToString.Exclude
    public static final String GREEN = "\u001B[32m";
    @ToString.Exclude
    public static final String RESET = "\u001B[0m";

    private final boolean isFreshwater;
    private int length;
    private int weight;
    private final Color color;
    private String[] occurs;

    public Fish(boolean isFreshwater, int length, int weight, Color color, String[] occurs) {
        this.isFreshwater = isFreshwater;
        setLength(length);
        setWeight(weight);
        this.color = color;
        setOccurs(occurs);
    }

    public void setLength(int length) {
        if (length <= 0) throw new IllegalArgumentException("Length of fish cannot be negative!");
        if (length > 100) throw new IllegalArgumentException("This pond did not accept fish grater than 100 :)");
        this.length = length;
    }

    public void setWeight(int weight) {
        if (length <= 0.0) throw new IllegalArgumentException("Weight of fish cannot be negative!");
        if (length > 20.0) throw new IllegalArgumentException("This pond did not accept fish heavier than 20 kg :)");
        this.weight = weight;
    }

    public void setOccurs(String[] occurs) {
        if (occurs.length == 0) throw new IllegalArgumentException("Occurrence cannot be empty");
//        Arrays.stream(occurrences)
        this.occurs = occurs;
    }

    private void addOccurrence(String string) {
        int newOccurrenceIndex = this.occurs.length + 1;
        String[] result = new String[newOccurrenceIndex];
        result[newOccurrenceIndex - 1] = string;
        this.occurs = result;
    }

    enum Color {
        BLUE,
        RED,
        YELLOW
    }

    @Override
    public String toString() {
        return "Fish{" +
                "isFreshwater=" + isFreshwater +
                ", length=" + length +
                ", " + GREEN + "weight=" + weight + RESET +
                ", color=" + color +
                ", occurs=" + Arrays.toString(occurs) +
                '}';
    }
}
