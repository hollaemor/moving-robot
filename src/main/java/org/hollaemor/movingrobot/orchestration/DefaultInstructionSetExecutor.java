package org.hollaemor.movingrobot.orchestration;

import org.hollaemor.movingrobot.datatransfer.Instruction;
import org.hollaemor.movingrobot.datatransfer.OrchestrationResult;

import java.util.List;

public class DefaultInstructionSetExecutor implements InstructionSetExecutor {

    @Override
    public OrchestrationResult executeForRobot(Robot robot, List<Instruction> instructions) {
        instructions.stream().sorted()
                .forEach(instruction -> executeForRobot(robot, instruction));
        return new OrchestrationResult(robot.getDirection(), robot.getPosition());
    }

    private void executeForRobot(Robot robot, Instruction instruction) {
        switch (instruction.getMove()) {
            case WAIT:
                robot.waitAround(instruction.getFrequency());
                break;
            case TURNAROUND:
                robot.turnAround(instruction.getFrequency());
                break;
            case LEFT:
                robot.turnLeft(instruction.getFrequency());
                break;
            case RIGHT:
                robot.turnRight(instruction.getFrequency());
                break;
            case FORWARD:
                robot.moveStepsForward(instruction.getFrequency());
                break;
            case BACKWARD:
                robot.moveStepsBackward(instruction.getFrequency());
                break;
        }
    }
}
