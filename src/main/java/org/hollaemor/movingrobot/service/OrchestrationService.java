package org.hollaemor.movingrobot.service;

import org.hollaemor.movingrobot.datatransfer.OrchestrationResult;
import org.hollaemor.movingrobot.datatransfer.InstructionSet;
import org.hollaemor.movingrobot.orchestration.InstructionSetExecutor;
import org.hollaemor.movingrobot.orchestration.Playground;
import org.hollaemor.movingrobot.orchestration.Robot;
import org.springframework.stereotype.Service;

@Service
public class OrchestrationService {

    private Playground playground;
    private InstructionSetExecutor instructionSetExecutor;

    public OrchestrationService(Playground playground, InstructionSetExecutor instructionSetExecutor) {
        this.playground = playground;
        this.instructionSetExecutor = instructionSetExecutor;
    }

    public OrchestrationResult orchestrate(InstructionSet instructionSet) {
        var robot = new Robot(instructionSet.getStart(), playground);
        return instructionSetExecutor.executeForRobot(robot, instructionSet.getInstructions());
    }
}
