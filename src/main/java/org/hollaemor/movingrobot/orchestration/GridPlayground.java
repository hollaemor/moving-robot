package org.hollaemor.movingrobot.orchestration;

import org.hollaemor.movingrobot.exception.InvalidMoveException;

import java.awt.*;

public class GridPlayground implements Playground {

    private int rows;
    private int columns;

    private enum Navigation {
        ROW, COLUMN
    }

    public GridPlayground(int rows, int columns) {
        assert rows > 0 : "rows must be greater than 0";
        assert columns > 0 : "columns must be greater than 0";
        // using zero-based index
        this.rows = rows - 1;
        this.columns = columns - 1;
    }

    @Override
    public Point moveInPlayground(Direction direction, Point currentPosition, int steps) throws InvalidMoveException {
        return isRowMovement(direction) ? moveInRow(direction, currentPosition, steps) :
                moveInColumn(direction, currentPosition, steps);
    }

    private boolean isRowMovement(Direction direction) {
        return direction == Direction.EAST || direction == Direction.WEST;
    }

    private Point moveInRow(Direction direction, Point currentPosition, int steps) {
        int updatedRowPosition = currentPosition.x + (direction == Direction.EAST ? steps : -1 * steps);
        var newPosition = new Point(updatedRowPosition, currentPosition.y);
        checkInvalidMove(newPosition, Navigation.ROW);
        return newPosition;
    }

    private Point moveInColumn(Direction direction, Point currentPosition, int steps) {
        int updatedColumnPosition = currentPosition.y + (direction == Direction.SOUTH ? steps : -1 * steps);
        var newPosition = new Point(currentPosition.x, updatedColumnPosition);
        checkInvalidMove(newPosition, Navigation.COLUMN);
        return newPosition;
    }

    private void checkInvalidMove(Point point, Navigation navigation) {
        var axis = navigation == Navigation.ROW ? point.x : point.y;
        var limit = navigation == Navigation.ROW ? rows : columns;

        if (axis < 0 || axis > limit) {
            throw new InvalidMoveException(String.format("Invalid move requested: [%d,%d]. Available spaces: [0-%d,0-%d]",
                    point.x, point.y, rows, columns));
        }
    }
}
