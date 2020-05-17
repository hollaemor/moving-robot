package org.hollaemor.movingrobot.orchestration;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hollaemor.movingrobot.datatransfer.StartingInstruction;

import java.awt.*;

import static org.hollaemor.movingrobot.orchestration.Direction.*;

@Slf4j
public class Robot {

    private Playground playground;

    private int currentDirectionIndex;

    private static final Direction[] DIRECTIONS = {EAST, SOUTH, WEST, NORTH};

    @Getter
    private Point position;

    public Robot(StartingInstruction startingInstruction, Playground playground) {
        this.position = new Point();
        this.playground = playground;
        currentDirectionIndex = getCurrentDirectionIndex(startingInstruction.getDirection());
        updatePosition(startingInstruction.getSteps());
    }

    public void waitAround() {
        log.debug("Waiting.. Doing nothing");
    }

    public void waitAround(int numberOfTimes) {
        checkGreaterThanZero(numberOfTimes);
        log.debug("waiting for {} turn(s)", numberOfTimes);
    }

    public void turnRight() {
        turnRight(1);
    }

    public void turnRight(int numberOfTurns) {
        checkGreaterThanZero(numberOfTurns);
        currentDirectionIndex = (currentDirectionIndex + numberOfTurns) % DIRECTIONS.length;
    }

    public void turnLeft() {
        turnLeft(1);
    }

    public void turnLeft(int numberOfTurns) {
        checkGreaterThanZero(numberOfTurns);
        currentDirectionIndex = currentDirectionIndex - numberOfTurns;
        if (currentDirectionIndex < 0) {
            currentDirectionIndex = DIRECTIONS.length - (Math.abs(currentDirectionIndex) % DIRECTIONS.length);
        }
    }

    public void turnAround() {
        turnRight(2);
    }

    public void turnAround(int numberOfTurns) {
        checkGreaterThanZero(numberOfTurns);
        turnRight(numberOfTurns * 2);
    }

    public void moveStepsForward(int numberOfSteps) {
        checkGreaterThanZero(numberOfSteps);
        updatePosition(numberOfSteps);
    }

    public void moveStepsBackward(int numberOfSteps) {
        checkGreaterThanZero(numberOfSteps);
        updatePosition(numberOfSteps * -1);
    }

    public Direction getDirection() {
        return DIRECTIONS[currentDirectionIndex];
    }

    private int getCurrentDirectionIndex(Direction direction) {
        int index = 0;
        for (int i = 0; i < DIRECTIONS.length; i++) {
            if (DIRECTIONS[i] == direction) {
                index = i;
                break;
            }
        }
        return index;
    }

    private void updatePosition(int numberOfSteps) {
        position = playground.moveInPlayground(getDirection(), position, numberOfSteps);
    }

    private void checkGreaterThanZero(int parameter) {
        assert parameter > 0 : "parameter must be greater than 0";
    }
}

