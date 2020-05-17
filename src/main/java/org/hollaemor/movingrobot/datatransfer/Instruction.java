package org.hollaemor.movingrobot.datatransfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hollaemor.movingrobot.orchestration.Move;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Instruction implements Comparable<Instruction> {

    private int id;

    @NotNull(message = "move is required")
    private Move move;

    @Min(value = 1, message = "frequency should be at least 1")
    private int  frequency;


    @Override
    public int compareTo(Instruction other) {
        return Integer.compare(id, other.id);
    }
}
