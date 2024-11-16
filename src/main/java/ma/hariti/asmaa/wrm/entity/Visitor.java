package ma.hariti.asmaa.wrm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ma.hariti.asmaa.wrm.util.BaseEntity;

import java.util.List;

@Entity
@Data
@Table(name = "visitor")
public class Visitor extends BaseEntity {



    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;
    @OneToMany(mappedBy = "visitor")
    private List<Visit> visits;


}
