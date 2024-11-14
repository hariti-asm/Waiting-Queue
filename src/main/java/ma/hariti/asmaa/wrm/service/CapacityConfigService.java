package ma.hariti.asmaa.wrm.service;

import ma.hariti.asmaa.wrm.config.SchedulingConfig;
import ma.hariti.asmaa.wrm.dto.CapacityConfigDTO;
import ma.hariti.asmaa.wrm.entity.Visit;
import ma.hariti.asmaa.wrm.mapper.CapacityConfigMapper;
import ma.hariti.asmaa.wrm.repository.SchedulingStrategy;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
