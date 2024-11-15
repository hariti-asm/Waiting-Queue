package ma.hariti.asmaa.wrm.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.hariti.asmaa.wrm.embeddedable.VisitId;
import ma.hariti.asmaa.wrm.enumeration.Status;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class VisitDTO {
    private VisitId id;

    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End date is required")
    private LocalTime endDate;

    @NotNull(message = "Status is required")
    private Status status;

    @NotNull(message = "Priority is required")
    @Min(value = 1, message = "Priority must be at least 1")
    @Max(value = 10, message = "Priority must not exceed 10")
    private Byte priority;

    @NotNull(message = "Estimated processing time is required")
    private Duration estimatedProcessingTime;

    @NotNull(message = "Visitor ID is required")
    private Long visitorId;

    @NotNull(message = "Waiting list ID is required")
    private Long waitingListId;
}
