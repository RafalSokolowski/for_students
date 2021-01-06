package day20201220.end_project.checker;

import day20201220.end_project.figure.OnTheBoard;
import day20201220.end_project.figure.OneFigure;
import day20201220.end_project.utils.Messages;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static day20201220.end_project.utils.Const.*;

@RequiredArgsConstructor
public class Movement {

    @NonNull
    private Map<Position, OnTheBoard> board;
    @NonNull
    private Map<Position, OneFigure> players;
    @NonNull
    private Map<Position, Map<Position, List<Position>>> positionsFrom_ToBeRemoved_MandatoryPositionsTo;

    private boolean hasCapturedInPreviousRound = false;
    private int darkOrLight = LIGHT;

    public boolean moveByString(String stringFrom, String stringTo) {

        Conditions conditions = new Conditions(board, players);

        if (conditions.areStringMoveInvalid(stringFrom, stringTo)) {
            System.out.println(Messages.errorString(stringFrom, stringTo));
            return false;
        }
        Position positionFrom = new Position(stringFrom);
        Position positionTo = new Position(stringTo);

        if (!positionsFrom_ToBeRemoved_MandatoryPositionsTo.isEmpty()) {
            if (!positionsFrom_ToBeRemoved_MandatoryPositionsTo.containsKey(positionFrom)) {
                System.out.println(Messages.errorPositionFrom(positionFrom));
                return false;
            }

            List<Position> allMandatoryPositions = positionsFrom_ToBeRemoved_MandatoryPositionsTo
                    .get(positionFrom).values()
                    .stream().flatMap(Collection::stream)
                    .collect(Collectors.toList());

            if (!allMandatoryPositions.contains(positionTo)) {
                System.out.println(Messages.errorPositionTo(positionTo));
                return false;
            }

            positionsFrom_ToBeRemoved_MandatoryPositionsTo.get(positionFrom).forEach((k, v) -> {
                if (v.contains(positionTo)) {
                    players.get(k).setState(ELIMINATED);
                    players.remove(k);
                    positionsFrom_ToBeRemoved_MandatoryPositionsTo.clear();
                    hasCapturedInPreviousRound = true;
                }
            });

        } else {
            if (conditions.isMovementConditionsNotCorrect(stringFrom, stringTo))
                return false;
        }

        OneFigure piece = players.get(positionFrom);
        if (piece.getColor() != darkOrLight) {
            System.out.println(Messages.errorMovementNotAltering(piece.getColor()));
            return false;
        }

        updateMovedPiece(positionFrom, positionTo);

        Listener listener = new Listener(board, players, positionsFrom_ToBeRemoved_MandatoryPositionsTo);

        if (hasCapturedInPreviousRound) {
            if (shouldWeChangePieceToDame(piece)) {
                String playersColor = piece.getColor() == DARK ? "Dark" : "Light";
                System.out.println(Messages.reachCrownhead(playersColor));
                changePawnToDame(piece);
            }
            listener.singlePiece(players.get(positionTo));

            if (positionsFrom_ToBeRemoved_MandatoryPositionsTo.isEmpty()) {
                darkOrLight = changeDarkLightTurn(darkOrLight);
                listener.allPieces(darkOrLight);
            }
            hasCapturedInPreviousRound = false;

        } else {
            if (shouldWeChangePieceToDame(piece)) {
                String playersColor = piece.getColor() == DARK ? "Dark" : "Light";
                System.out.println(Messages.reachCrownhead(playersColor));
                changePawnToDame(piece);
            }
            darkOrLight = changeDarkLightTurn(darkOrLight);
            listener.allPieces(darkOrLight);
        }

        printRuleIfAny(positionTo);

        return true;
    }

    private int changeDarkLightTurn(int darkOrLight) {
        return darkOrLight == 0 ? 1 : 0;
    }

    private boolean shouldWeChangePieceToDame(OneFigure piece) {
        if (piece.getFigure() == DAME) return false;
        if (piece.getColor() == DARK && piece.getPosition().getY() == LAST_COORDINATE) return true;
        if (piece.getColor() == LIGHT && piece.getPosition().getY() == FIRST_COORDINATE) return true;
        return false;
    }

    private void changePawnToDame(OneFigure piece) {
        piece.setFigure(DAME);
    }

    private void updateMovedPiece(Position positionFrom, Position positionTo) {
        players.get(positionFrom).setPosition(positionTo);      // 1. change of the position - new (y,x) coordinates
        players.put(positionTo, players.get(positionFrom));     // 2. change in the players database
        players.remove(positionFrom);                           // 3. remove OneFigure with old position
    }

    private void printRuleIfAny (Position positionTo) {
        positionsFrom_ToBeRemoved_MandatoryPositionsTo.forEach((k, v) -> {
            System.out.print(Messages.ruleMovement1(darkOrLight, players.get(positionTo).getFigure(), k));

            AtomicInteger counter = new AtomicInteger(1);
            v.forEach((keyV, valueV) -> {
                System.out.print(Messages.ruleMovement2(counter.getAndIncrement(), valueV, keyV));
            });
            System.out.println();
        });
    }

}
