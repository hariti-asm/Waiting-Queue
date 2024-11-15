package ma.hariti.asmaa.wrm.dto;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WaitingListDto {
    private Long id;

    @NotNull(message = "Date is required")
    @FutureOrPresent(message = "Date cannot be in the past")
    private Date date;

    @NotBlank(message = "Algorithm name is required")
    @Pattern(regexp = "^(fifo|priority|shortest_job_first)$",
            message = "Invalid algorithm name")
    @Size(max = 100, message = "Algorithm name must not exceed 100 characters")
    private String algorithm;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    @Max(value = 1000, message = "Capacity must not exceed 1000")
    private Integer capacity;

    @NotBlank(message = "Mode is required")
    @Size(max = 50, message = "Mode must not exceed 50 characters")
    private String mode;

private List<VisitDTO> visits = new ArrayList<>();
}