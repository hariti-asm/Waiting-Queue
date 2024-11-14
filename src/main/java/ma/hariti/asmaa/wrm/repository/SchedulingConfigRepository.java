package ma.hariti.asmaa.wrm.repository;

import lombok.Getter;
import ma.hariti.asmaa.wrm.config.SchedulingConfig;
import org.springframework.stereotype.Repository;

@Getter
@Repository
public class SchedulingConfigRepository {
    private final SchedulingConfig schedulingConfig;

    public SchedulingConfigRepository(SchedulingConfig schedulingConfig) {
        this.schedulingConfig = schedulingConfig;
    }

}