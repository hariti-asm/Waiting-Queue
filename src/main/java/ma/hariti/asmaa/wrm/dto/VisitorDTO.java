package ma.hariti.asmaa.wrm.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ma.hariti.asmaa.wrm.embeddedable.VisitId;

import java.util.List;

@Data
public class VisitorDTO {
    private long id;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;


    private List<VisitId> visitIds;}