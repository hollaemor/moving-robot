package org.hollaemor.movingrobot.controller;

import org.hollaemor.movingrobot.exception.InvalidMoveException;
import org.hollaemor.movingrobot.orchestration.Direction;
import org.hollaemor.movingrobot.datatransfer.OrchestrationResult;
import org.hollaemor.movingrobot.orchestration.Move;
import org.hollaemor.movingrobot.properties.GridProperties;
import org.hollaemor.movingrobot.service.OrchestrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.awt.*;
import java.util.List;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IndexController.class)
public class IndexControllerTest {

    @MockBean
    private GridProperties gridProperties;

    @MockBean
    private OrchestrationService orchestrationService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenIndexPageIsRequestedThenPopulateModel() throws Exception {
        // given
        given(gridProperties.getColumns()).willReturn(3);
        given(gridProperties.getRows()).willReturn(5);

        // when / then
        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(view().name("index"))
                .andExpect(model().attribute("rows", 5))
                .andExpect(model().attribute("columns", 3))
                .andExpect(model().attribute("maxSteps", 4))
                .andExpect(model().attribute("directions", List.of(Direction.EAST, Direction.SOUTH)))
                .andExpect(model().attribute("movements", Move.values()));
    }


    @Test
    public void whenPayloadIsInvalidDataThenReturnBadRequest() throws Exception {
        // given
        var invalidJson = "{\"start\": { \"steps\" : 1, \"direction\": \"EAST\" } }";

        mvc.perform(post("/orchestrate").contentType(MediaType.APPLICATION_JSON).content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("At least one instruction should be provided"));
    }

    @Test
    public void whenPayloadIsUnreadableThenReturnBadRequest() throws Exception {
        // given
        var invalidJson = "{\"start\": { \"steps\" : 1, \"direction\": \"XXX\" } }";

        mvc.perform(post("/orchestrate").contentType(MediaType.APPLICATION_JSON).content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid request"));
    }


    @Test
    public void whenInvalidMoveExceptionIsThrownThenReturnBadRequest() throws Exception {
        // given
        given(orchestrationService.orchestrate(any()))
                .willThrow(new InvalidMoveException("Invalid instruction sequence"));

        //  when / then
        mvc.perform(post("/orchestrate").contentType(MediaType.APPLICATION_JSON).content(validPayload()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid instruction sequence"));
    }


    @Test
    public void whenAssertionErrorIsThrownThenMapToBadRequest() throws Exception {
        // given
        given(orchestrationService.orchestrate(any()))
                .willThrow(new AssertionError("some assertion failed"));

        // when / then
        mvc.perform(post("/orchestrate").contentType(MediaType.APPLICATION_JSON).content(validPayload()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("some assertion failed"));
    }

    @Test
    public void whenPayloadIsValidThenReturnValidResponse() throws Exception {
        // given
        given(orchestrationService.orchestrate(any()))
                .willReturn(new OrchestrationResult(Direction.NORTH, new Point(2, 3)));

        // when / then
        mvc.perform(post("/orchestrate").contentType(MediaType.APPLICATION_JSON).content(validPayload()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.direction").value(Direction.NORTH.name()))
                .andExpect(jsonPath("$.position.x").value(2))
                .andExpect(jsonPath("$.position.y").value(3));
    }

    private String validPayload() {
        var instruction = "{ \"id\": 1, \"move\": \"RIGHT\", \"frequency\": 2}";
        return String.format("{\"start\": { \"steps\" : 1, \"direction\": \"EAST\" }, \"instructions\": [%s] }", instruction);
    }
}
