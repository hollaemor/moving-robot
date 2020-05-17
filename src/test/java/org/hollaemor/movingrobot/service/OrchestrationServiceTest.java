package org.hollaemor.movingrobot.service;

import org.hollaemor.movingrobot.datatransfer.OrchestrationResult;
import org.hollaemor.movingrobot.datatransfer.Instruction;
import org.hollaemor.movingrobot.datatransfer.InstructionSet;
import org.hollaemor.movingrobot.datatransfer.StartingInstruction;
import org.hollaemor.movingrobot.orchestration.Robot;
import org.hollaemor.movingrobot.orchestration.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class OrchestrationServiceTest {

    @Mock
    private InstructionSetExecutor instructionSetExecutor;

    @Mock
    private Playground playground;

    @InjectMocks
    private OrchestrationService orchestrationService;

    @Captor
    private ArgumentCaptor<List<Instruction>> instructionsCaptor;

    @Captor
    private ArgumentCaptor<Robot> robotCaptor;

    @Test
    public void orchestrateShouldInitializeRobot() {
        // given
        var instructionSet = new InstructionSet(new StartingInstruction(3, Direction.WEST), Collections.emptyList());

        given(playground.moveInPlayground(any(), any(), anyInt()))
                .willReturn(new Point(0,0));
       given(instructionSetExecutor.executeForRobot(any(), anyList()))
       .willReturn(new OrchestrationResult(Direction.SOUTH, new Point(2,5)));

        // when
        var result = orchestrationService.orchestrate(instructionSet);

        // then
        assertThat(result.getDirection()).isEqualTo(Direction.SOUTH);
        assertThat(result.getPosition()).isEqualTo(new Point(2, 5));

        verify(instructionSetExecutor).executeForRobot(robotCaptor.capture(), anyList());
        assertThat(robotCaptor.getValue().getDirection()).isEqualTo(Direction.WEST);
        assertThat(robotCaptor.getValue().getPosition()).isEqualTo(new Point(0,0));
    }

    @Test
    public void orchestrateShouldCallInstructionSetExecutor() {
        // given
        var instruction = new Instruction(1, Move.FORWARD, 3);
        var instructionSet = new InstructionSet(new StartingInstruction(1, Direction.EAST), List.of(instruction));

        // when
        orchestrationService.orchestrate(instructionSet);

        // then
        verify(instructionSetExecutor).executeForRobot(any(), instructionsCaptor.capture());
        assertThat(instructionsCaptor.getValue()).containsOnly(instruction);
    }
}
