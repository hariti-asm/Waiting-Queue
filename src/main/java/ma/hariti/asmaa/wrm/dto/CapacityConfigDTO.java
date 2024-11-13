package ma.hariti.asmaa.wrm.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import java.time.DayOfWeek;

@Getter
@Setter
public class CapacityConfigDTO {

    @NotNull(message = "Weekday cannot be null")
    private DayOfWeek weekday;

    @NotNull(message = "Max capacity cannot be null")
    @Min(value = 1, message = "Max capacity must be at least 1")
    private Integer maxCapacity;
}
