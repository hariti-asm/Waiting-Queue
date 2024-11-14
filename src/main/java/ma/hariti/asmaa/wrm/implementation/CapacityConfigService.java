package ma.hariti.asmaa.wrm.implementation;

import ma.hariti.asmaa.wrm.config.SchedulingConfig;
import ma.hariti.asmaa.wrm.dto.CapacityConfigDTO;
import ma.hariti.asmaa.wrm.entity.Visit;
import ma.hariti.asmaa.wrm.mapper.CapacityConfigMapper;

public class CapacityConfigService {
    private final SchedulingConfig schedulingConfig;
    private final CapacityConfigMapper capacityConfigMapper;

    public CapacityConfigService(SchedulingConfig schedulingConfig, CapacityConfigMapper capacityConfigMapper) {
        this.schedulingConfig = schedulingConfig;
        this.capacityConfigMapper = capacityConfigMapper;
    }

    public CapacityConfigDTO getCapacityConfigForVisit(Visit visit) {
        SchedulingConfig.CapacityConfig capacityConfig = schedulingConfig.getCapacities().stream()
                .filter(c -> c.getWeekday().equals(visit.getArrivalTime().getDayOfWeek()))
                .findFirst()
                .orElse(null);

        if (capacityConfig != null) {
            return capacityConfigMapper.toDto(capacityConfig);
        } else {
            return null;
        }
    }
}
