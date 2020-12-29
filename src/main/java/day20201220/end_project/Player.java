package day20201220.end_project;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static day20201220.end_project.utils.Const.BLUE;
import static day20201220.end_project.utils.Const.RESET;

@Getter
@AllArgsConstructor
public class Player {

    private final String name;
    private long firstSix;
    private long lastSix;

    @Override
    public String toString() {
        return  "Player:   " + BLUE + name + RESET + "\n" +
                "1st long: " + BLUE + firstSix + RESET + " (in binary: " + Long.toBinaryString(firstSix) + ")\n"+
                "2nd long: " + BLUE + lastSix + RESET + " (in binary: " + Long.toBinaryString(lastSix) + ")";
    }
}
