package day20201220.end_project.figure;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SixFigures {

    private OneFigure first;            // TODO: odwrotnie numeracja OneFigure
    private OneFigure second;
    private OneFigure third;
    private OneFigure fourth;
    private OneFigure fifth;
    private OneFigure sixth;

    public SixFigures(String string) {
        if (string.length() != 54)
            throw new IllegalArgumentException(OneFigure.class.getSimpleName() + " accepts only string with length equal to 54, not " + string.length());
        if (!string.matches("[0,1]+"))
            throw new IllegalArgumentException(OneFigure.class.getSimpleName() + " accepts only string with 0 or 1");

        this.first = new OneFigure(string.substring(0, 9));
        this.second = new OneFigure(string.substring(9, 18));
        this.third = new OneFigure(string.substring(18, 27));
        this.fourth = new OneFigure(string.substring(27, 36));
        this.fifth = new OneFigure(string.substring(36, 45));
        this.sixth = new OneFigure(string.substring(45, 54));
    }

    public SixFigures(long number) {
        this.first = new OneFigure(number >> 9*5 & 0b111111111);
        this.second = new OneFigure(number >> 9*4 & 0b111111111);
        this.third = new OneFigure(number >> 9*3 & 0b111111111);
        this.fourth = new OneFigure(number >> 9*2 & 0b111111111);
        this.fifth = new OneFigure(number >> 9 & 0b111111111);
        this.sixth = new OneFigure(number & 0b111111111);
    }

    public String values() {
        return "First:   " + first.values() + "\nSecond: " + second.values() + "\nThird: " + third.values() +
                "\nForth: " + fourth.values() + "\nFifth:  " + fifth.values() + "\nSixth: " + sixth.values();
    }

    public void print() {
        System.out.printf(
                        "First  - %s\n" +
                        "Second - %s\n" +
                        "Third  - %s\n" +
                        "Forth  - %s\n" +
                        "Fifth  - %s\n" +
                        "Sixth  - %s\n",
                first.values(), second.values(), third.values(), fourth.values(), fifth.values(), sixth.values()
        );
    }

    @Override
    public String toString() {
//        return "" + empty + BLUE + first + RESET + second + BLUE + third + RESET + fort + BLUE + fifth + RESET + sixth;
        return "" + first + second + third + fourth + fifth + sixth;
    }
}
