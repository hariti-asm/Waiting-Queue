package ma.hariti.asmaa.wrm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "waiting_list")
@Data
public class WaitingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Date is required")
    @Temporal(TemporalType.DATE)
    private Date date;

    @NotBlank(message = "Algorithm name is required")
    @Size(max = 100, message = "Algorithm name must not exceed 100 characters")
    private String algorithm;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    @Max(value = 1000, message = "Capacity must not exceed 1000")
    private Integer capacity;

    @NotBlank(message = "Mode is required")
    @Size(max = 50, message = "Mode must not exceed 50 characters")
    private String mode;
    @OneToMany(mappedBy = "waitingList")
    private List<Visit> visits;
}
