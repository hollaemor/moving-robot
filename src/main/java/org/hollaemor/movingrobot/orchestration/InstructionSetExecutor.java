package org.hollaemor.movingrobot.orchestration;

import org.hollaemor.movingrobot.datatransfer.OrchestrationResult;
import org.hollaemor.movingrobot.datatransfer.Instruction;

import java.util.List;

public interface InstructionSetExecutor {
    OrchestrationResult executeForRobot(Robot robot, List<Instruction> instructions);
}
