package org.hollaemor.movingrobot.orchestration;

import org.hollaemor.movingrobot.orchestration.Direction;
import org.hollaemor.movingrobot.orchestration.Playground;
import org.hollaemor.movingrobot.datatransfer.StartingInstruction;
import org.hollaemor.movingrobot.orchestration.Robot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class RobotTest {

    @Mock
    private Playground playground;

    @Test
    public void thatRobotIsProperlyInitialized() {
        // given
        var startingInstruction = new StartingInstruction(3, Direction.EAST);
        var point = new Point(0, 3);

        given(playground.moveInPlayground(any(), any(), anyInt()))
                .willReturn(point);

        // when
        var robot = new org.hollaemor.movingrobot.orchestration.Robot(startingInstruction, playground);

        // then
        assertThat(robot.getDirection()).isEqualTo(Direction.EAST);
        assertThat(robot.getPosition()).isEqualTo(point);

        verify(playground).moveInPlayground(Direction.EAST, new Point(), 3);
    }

    @Test
    public void waitShouldNotInitiateMove() {
        // given
        var robot = new org.hollaemor.movingrobot.orchestration.Robot(new StartingInstruction(3, Direction.EAST), playground);

        // when
        robot.waitAround();
        robot.waitAround(10);

        // then
        verify(playground, times(1)).moveInPlayground(any(), any(), anyInt());
        assertThat(robot.getDirection()).isEqualTo(Direction.EAST);
    }

    @Test
    public void turnRightShouldUpdateDirection() {
        // given
        var robot = new org.hollaemor.movingrobot.orchestration.Robot(new StartingInstruction(1, Direction.SOUTH), playground);

        // when
        robot.turnRight();

        // then
        assertThat(robot.getDirection()).isEqualTo(Direction.WEST);

        // when
        robot.turnRight();

        // then
        assertThat(robot.getDirection()).isEqualTo(Direction.NORTH);

        verify(playground, times(1)).moveInPlayground(any(), any(), anyInt());
    }

    @Test
    public void turnRightANumberOfTurnsShouldUpdateDirection() {
        // given
        var robot = new org.hollaemor.movingrobot.orchestration.Robot(new StartingInstruction(2, Direction.SOUTH), playground);

        // when
        robot.turnRight(5);

        // then
        assertThat(robot.getDirection()).isEqualTo(Direction.WEST);
    }

    @Test
    public void turnLeftShouldUpdateDirection() {
        // given
        var robot = new org.hollaemor.movingrobot.orchestration.Robot(new StartingInstruction(1, Direction.EAST), playground);

        // when
        robot.turnLeft();

        // then
        assertThat(robot.getDirection()).isEqualTo(Direction.NORTH);

        // when
        robot.turnLeft();

        // then
        assertThat(robot.getDirection()).isEqualTo(Direction.WEST);
    }


    @Test
    public void turnLeftANumberOfTurnsShouldUpdateDirection() {
        // given
        var robot = new org.hollaemor.movingrobot.orchestration.Robot(new StartingInstruction(1, Direction.EAST), playground);

        // when
        robot.turnLeft(3);

        // then
        assertThat(robot.getDirection()).isEqualTo(Direction.SOUTH);
    }

    @Test
    public void turnAroundShouldUpdateDirection() {
        // given
        var robot = new org.hollaemor.movingrobot.orchestration.Robot(new StartingInstruction(1, Direction.WEST), playground);

        // when
        robot.turnAround();

        // then
        assertThat(robot.getDirection()).isEqualTo(Direction.EAST);
    }

    @Test
    public void turnAroundWithNumberOfTurns_ShouldUpdateDirection() {
        // given
        var robot = new org.hollaemor.movingrobot.orchestration.Robot(new StartingInstruction(1, Direction.WEST), playground);

        // when
        robot.turnAround(2);

        // then
        assertThat(robot.getDirection()).isEqualTo(Direction.WEST);
    }


    @Test
    public void moveStepsForwardShouldDelegateToMoveListener() {
        // given
        given(playground.moveInPlayground(Direction.WEST, new Point(), 2)).willReturn(new Point(0, 2));
        given(playground.moveInPlayground(Direction.WEST, new Point(0, 2), 3)).willReturn(new Point(0, 5));

        var robot = new org.hollaemor.movingrobot.orchestration.Robot(new StartingInstruction(2, Direction.WEST), playground);

        // when
        robot.moveStepsForward(3);

        // then
        verify(playground).moveInPlayground(Direction.WEST, new Point(0, 2), 3);
        assertThat(robot.getPosition()).isEqualTo(new Point(0, 5));
    }


    @Test
    public void moveStepsBackwardShouldDelegateToMoveListener() {
        // given
        given(playground.moveInPlayground(Direction.EAST, new Point(), 2)).willReturn(new Point(0, 4));
        given(playground.moveInPlayground(Direction.EAST, new Point(0, 4), -3)).willReturn(new Point(0, 1));

        var robot = new org.hollaemor.movingrobot.orchestration.Robot(new StartingInstruction(2, Direction.EAST), playground);

        // when
        robot.moveStepsBackward(3);

        // then
        verify(playground).moveInPlayground(Direction.EAST, new Point(0, 4), -3);
        assertThat(robot.getPosition()).isEqualTo(new Point(0, 1));
    }

    @Test
    public void whenStepsAreNegative_ThenThrowException() {
        // given
        var robot = new Robot(new StartingInstruction(1, Direction.WEST), playground);

        // when / then
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> robot.turnLeft(-4))
                .withMessage("parameter must be greater than 0");

        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> robot.turnRight(0));

        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> robot.moveStepsBackward(-2));

        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> robot.moveStepsForward(0));

        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> robot.waitAround(-42));

        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> robot.turnAround(-3));
    }
}
