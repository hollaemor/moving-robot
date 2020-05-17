package org.hollaemor.movingrobot.controller;

import org.hollaemor.movingrobot.orchestration.Direction;
import org.hollaemor.movingrobot.datatransfer.OrchestrationResult;
import org.hollaemor.movingrobot.datatransfer.InstructionSet;
import org.hollaemor.movingrobot.orchestration.Move;
import org.hollaemor.movingrobot.properties.GridProperties;
import org.hollaemor.movingrobot.service.OrchestrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
public class IndexController {

    private GridProperties gridProperties;
    private OrchestrationService orchestrationService;

    public IndexController(GridProperties gridProperties, OrchestrationService orchestrationService) {
        this.gridProperties = gridProperties;
        this.orchestrationService = orchestrationService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("rows", gridProperties.getRows());
        model.addAttribute("columns",gridProperties.getColumns());
        model.addAttribute("directions", List.of(Direction.EAST, Direction.SOUTH));
        model.addAttribute("maxSteps", getLargerSide());
        model.addAttribute("movements", Move.values());

        return "index";
    }


    @PostMapping(value = "/orchestrate")
    public @ResponseBody OrchestrationResult orchestrate(@RequestBody @Valid InstructionSet instructionSet) {
        return orchestrationService.orchestrate(instructionSet);
    }

    private int getLargerSide() {
        var largerSide = gridProperties.getRows() > gridProperties.getColumns()?
                gridProperties.getRows() : gridProperties.getColumns();

        // subtract 1 since we're using 0-based indexing
        return largerSide -1;
    }
}
