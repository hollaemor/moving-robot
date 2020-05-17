package org.hollaemor.movingrobot.config;

import org.hollaemor.movingrobot.orchestration.GridPlayground;
import org.hollaemor.movingrobot.orchestration.DefaultInstructionSetExecutor;
import org.hollaemor.movingrobot.orchestration.InstructionSetExecutor;
import org.hollaemor.movingrobot.orchestration.Playground;
import org.hollaemor.movingrobot.properties.GridProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GridProperties.class)
public class ApplicationConfig {

    @Bean
    InstructionSetExecutor instructionSetExecutor() {
        return new DefaultInstructionSetExecutor();
    }

    @Bean
    public Playground playground(GridProperties gridProperties) {
        return new GridPlayground(gridProperties.getRows(), gridProperties.getColumns());
    }
}
