package ma.hariti.asmaa.wrm.service;

import ma.hariti.asmaa.wrm.config.SchedulingConfig;
import ma.hariti.asmaa.wrm.dto.ScheduleConfigDTO;
import ma.hariti.asmaa.wrm.entity.Visit;
import ma.hariti.asmaa.wrm.mapper.ScheduleConfigMapper;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    private final SchedulingConfig schedulingConfig;
    private final ScheduleConfigMapper scheduleConfigMapper;

    public ScheduleService(SchedulingConfig schedulingConfig, ScheduleConfigMapper scheduleConfigMapper) {
        this.schedulingConfig = schedulingConfig;
        this.scheduleConfigMapper = scheduleConfigMapper;
    }

    public ScheduleConfigDTO getScheduleConfigForVisit(Visit visit) {
        SchedulingConfig.ScheduleConfig scheduleConfig = schedulingConfig.getSchedules().stream().filter(s -> s.getWeekday().equals(visit.getArrivalTime().getDayOfWeek())).findFirst().orElse(null);
        if (scheduleConfig != null) {
            return scheduleConfigMapper.toDto(scheduleConfig);
        } else {
            return null;
        }
    }

}
