package ma.hariti.asmaa.wrm.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.AssertTrue;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
public class ScheduleConfigDTO {

    @NotNull(message = "Weekday cannot be null")
    private DayOfWeek weekday;

    @NotNull(message = "Opening time cannot be null")
    private LocalTime openingTime;

    @NotNull(message = "Closing time cannot be null")
    private LocalTime closingTime;

    @AssertTrue(message = "Opening time must be before closing time")
    public boolean isOpeningTimeBeforeClosingTime() {
        if (openingTime != null && closingTime != null) {
            return openingTime.isBefore(closingTime);
        }
        return true;
    }
}
