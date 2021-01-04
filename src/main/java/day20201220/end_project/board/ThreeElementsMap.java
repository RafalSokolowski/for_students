package day20201220.end_project.board;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ThreeElementsMap {

    private Position positionFrom;
    private List<Position> mandatoryPositionsTo;
    private Position positionToBeRemoved;

    public void add(Position positionFrom, List<Position> mandatoryPositionsTo, Position positionToBeRemoved) {

    }
}
