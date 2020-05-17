package org.hollaemor.movingrobot.datatransfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hollaemor.movingrobot.orchestration.Direction;

import java.awt.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrchestrationResult {

    private Direction direction;
    private Point position;
}
