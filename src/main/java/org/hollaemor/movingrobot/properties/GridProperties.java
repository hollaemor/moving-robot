package org.hollaemor.movingrobot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

@ConfigurationProperties("grid")
@Validated
@Data
public class GridProperties {

    @Min(value = 1, message = "rows should be at least 1")
    private int rows;

    @Min(value = 1, message = "columns should be at least 1")
    private int columns;

}
