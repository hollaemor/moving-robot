package org.hollaemor.movingrobot.orchestration;

import org.hollaemor.movingrobot.exception.InvalidMoveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class GridPlaygroundTest {

    private GridPlayground gridPlayground;

    @BeforeEach
    public void setup() {
        gridPlayground = new GridPlayground(5, 5);
    }

    @Test
    public void whenRowOrColumnIsNegative_ThenThrowException() {
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> new GridPlayground(-2, 3))
                .withMessage("rows must be greater than 0");

        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> new GridPlayground(4, 0))
                .withMessage("columns must be greater than 0");
    }
    @Test
    public void whenDirectionIsEastAndMovementIsForward_ThenUpdatePosition() {
        // given
        var startingPoint = new Point(0, 0);

        // when
        var newPosition = gridPlayground.moveInPlayground(Direction.EAST, startingPoint, 3);

        // then
        assertThat(newPosition).isEqualTo(new Point(3, 0));
    }

    @Test
    public void whenDirectionIsEastAndMovementIsBeyondGrid_ThenThrowException() {
        // given / when / then
        assertThatExceptionOfType(InvalidMoveException.class)
                .isThrownBy(() -> gridPlayground.moveInPlayground(Direction.EAST, new Point(2, 0), 20))
                .withMessage("Invalid move requested: [22,0]. Available spaces: [0-4,0-4]");
    }

    @Test
    public void whenDirectionIsWestAndMovementIsForward_ThenUpdatePosition() {
        // given
        var startingPoint = new Point(4, 0);

        // when
        var newPosition = gridPlayground.moveInPlayground(Direction.WEST, startingPoint, 2);

        // then
        assertThat(newPosition).isEqualTo(new Point(2, 0));
    }

    @Test
    public void whenDirectionIsWestAndMovementIsBeyondGrid_thenThrowException() {
        // given / when / then
        assertThatExceptionOfType(InvalidMoveException.class)
                .isThrownBy(() -> gridPlayground.moveInPlayground(Direction.WEST, new Point(2, 0), 30));
    }

    @Test
    public void whenDirectionIsSouthAndMovementIsForward_ThenUpdatePosition() {
        // given
        var startingPosition = new Point(0, 0);

        // when
        var newPosition = gridPlayground.moveInPlayground(Direction.SOUTH, startingPosition, 2);

        // then
        assertThat(newPosition).isEqualTo( new Point(0, 2));
    }

    @Test
    public void whenDirectionIsSouthAndMovementIsBeyondGrid_ThenThrowException() {
        // given / when / then
        assertThatExceptionOfType(InvalidMoveException.class)
                .isThrownBy(() -> gridPlayground.moveInPlayground(Direction.SOUTH, new Point(0,0), 40));
    }

    @Test
    public void whenDirectionIsNorthAndMovementIsForward_ThenUpdatePosition() {
        // given
        var startingPosition = new Point(0, 3);

        // when
        var newPosition = gridPlayground.moveInPlayground(Direction.NORTH, startingPosition, 2);

        // then
        assertThat(newPosition).isEqualTo(new Point(0,1));
    }

    @Test
    public void whenDirectionIsNorthAndMovementIsBeyondGrid_ThenThrowException() {
        // given / when / then
        assertThatExceptionOfType(InvalidMoveException.class)
                .isThrownBy(() -> gridPlayground.moveInPlayground(Direction.NORTH, new Point(0,0), 20));
    }


    @Test
    public void whenDirectionIsNorthAndMovementIsBackwards_ThenUpdatePosition() {
        // given
        var startingPosition = new Point(0, 1);

        // when
        var newPosition = gridPlayground.moveInPlayground(Direction.NORTH, startingPosition, -2);

        // then
        assertThat(newPosition).isEqualTo(new Point(0, 3));
    }

    @Test
    public void whenDirectionIsSouthAndMovementIsBackwards_ThenUpdatePosition() {
        // given
        var startingPosition = new Point(1, 2);

        // when
        var newPosition = gridPlayground.moveInPlayground(Direction.SOUTH, startingPosition, -2);

        // then
        assertThat(newPosition).isEqualTo(new Point(1, 0));
    }

    @Test
    public void whenDirectionIsEastAndMovementIsBackwards_ThenUpdatePosition() {
        // given
        var startingPosition = new Point(2, 4);

        // when
        var newPosition = gridPlayground.moveInPlayground(Direction.EAST, startingPosition, -2);

        // then
        assertThat(newPosition).isEqualTo(new Point(0,4));
    }


    @Test
    public void whenDirectionIsWestAndMovementIsBackwards_ThenUpdatePosition() {
        // given
        var startingPosition = new Point(0, 2);

        // when
        var newPosition = gridPlayground.moveInPlayground(Direction.WEST, startingPosition, -2);

        // then
        assertThat(newPosition).isEqualTo(new Point(2,2));
    }

}
