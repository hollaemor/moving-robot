package org.hollaemor.movingrobot.orchestration;

import org.hollaemor.movingrobot.datatransfer.OrchestrationResult;
import org.hollaemor.movingrobot.datatransfer.Instruction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DefaultInstructionSetExecutorTest {

    private DefaultInstructionSetExecutor executor;

    @Mock
    private Robot robot;

    @BeforeEach
    public void setup() {
        executor = new DefaultInstructionSetExecutor();
    }

    @Test
    public void whenInstructionsAreGivenThenCallRobotMethodsInRightSequence() {
        // given
        var instructions = List.of(
                new Instruction(2, Move.BACKWARD, 1),
                new Instruction(3, Move.FORWARD, 2),
                new Instruction(6, Move.TURNAROUND, 4),
                new Instruction(1, Move.WAIT, 8),
                new Instruction(8, Move.RIGHT, 5),
                new Instruction(4, Move.LEFT, 3)
        );

        given(robot.getDirection()).willReturn(Direction.WEST);
        given(robot.getPosition()).willReturn(new Point(2,5));

        // when
        OrchestrationResult orchestrationResult = executor.executeForRobot(robot, instructions);

        // then
        verify(robot).waitAround(8);
        verify(robot).moveStepsBackward(1);
        verify(robot).moveStepsForward(2);
        verify(robot).turnLeft(3);
        verify(robot).turnAround(4);
        verify(robot).turnRight(5);

        assertThat(orchestrationResult).isNotNull();
        assertThat(orchestrationResult.getDirection()).isEqualTo(Direction.WEST);
        assertThat(orchestrationResult.getPosition()).isEqualTo(new Point(2,5));
    }
}
