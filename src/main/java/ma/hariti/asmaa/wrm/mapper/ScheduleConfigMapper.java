package ma.hariti.asmaa.wrm.mapper;

import ma.hariti.asmaa.wrm.config.SchedulingConfig;
import ma.hariti.asmaa.wrm.dto.ScheduleConfigDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleConfigMapper {
    ScheduleConfigDTO toDto(SchedulingConfig.ScheduleConfig scheduleConfig);

    SchedulingConfig.ScheduleConfig toEntity(ScheduleConfigDTO scheduleConfigDto);
}