package org.hollaemor.movingrobot.orchestration;

import org.hollaemor.movingrobot.exception.InvalidMoveException;

import java.awt.*;

public interface Playground {
    Point moveInPlayground(Direction direction, Point currentPosition, int steps) throws InvalidMoveException;
}
