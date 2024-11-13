package ma.hariti.asmaa.wrm.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "scheduling.algorithms")
@Validated
@Getter
@Setter
public class SchedulingAlgorithmsConfig {

    private FifoConfig fifo;
    private PriorityConfig priority;
    private ShortestJobFirstConfig shortestJobFirst;

    @Getter
    @Setter
    public static class FifoConfig {
        @NotBlank(message = "FIFO name is required")
        private String name;

        @NotBlank(message = "FIFO description is required")
        private String description;
    }

    @Getter
    @Setter
    public static class PriorityConfig {
        @NotBlank(message = "Priority algorithm name is required")
        private String name;

        @NotBlank(message = "Priority algorithm description is required")
        private String description;

        @NotEmpty(message = "Priority factors list cannot be empty")
        private List<String> priorityFactors;
    }

    @Getter
    @Setter
    public static class ShortestJobFirstConfig {
        @NotBlank(message = "SJF name is required")
        private String name;

        @NotBlank(message = "SJF description is required")
        private String description;
    }

    public String getAlgorithm(String algorithmName) {
        if (algorithmName == null) {
            throw new IllegalArgumentException("Algorithm name cannot be null");
        }

        return switch (algorithmName.toLowerCase()) {
            case "fifo" -> fifo.getName();
            case "priority" -> priority.getName();
            case "shortest_job_first" -> shortestJobFirst.getName();
            default -> throw new IllegalArgumentException("Unknown algorithm: " + algorithmName);
        };
    }
}