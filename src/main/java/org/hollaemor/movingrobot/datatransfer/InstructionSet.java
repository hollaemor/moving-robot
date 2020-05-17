package org.hollaemor.movingrobot.datatransfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstructionSet {

    @Valid
    private StartingInstruction start;

    @NotEmpty(message = "At least one instruction should be provided")
    private List<@Valid Instruction> instructions;
}
