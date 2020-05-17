package org.hollaemor.movingrobot.datatransfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hollaemor.movingrobot.orchestration.Direction;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StartingInstruction {

    @Min(value = 1, message = "steps should be at least 1")
    private int steps;

    @NotNull(message = "direction is required")
    private Direction direction;
}
