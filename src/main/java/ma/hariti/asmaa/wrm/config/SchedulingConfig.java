package ma.hariti.asmaa.wrm.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
@Configuration
@ConfigurationProperties(prefix = "scheduling")
@Getter
@Setter
public class SchedulingConfig {
    private List<ScheduleConfig> schedules;
    private List<CapacityConfig> capacities;

    @Getter
    @Setter
    public static class ScheduleConfig {
        private DayOfWeek weekday;
        private LocalTime openingTime;
        private LocalTime closingTime;
    }

    @Getter
    @Setter
    public static class CapacityConfig {
        private DayOfWeek weekday;
        private Integer maxCapacity;
    }
}
