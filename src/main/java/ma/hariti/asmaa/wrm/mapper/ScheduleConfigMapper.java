package ma.hariti.asmaa.wrm.mapper;

import ma.hariti.asmaa.wrm.config.SchedulingConfig;
import ma.hariti.asmaa.wrm.dto.ScheduleConfigDTO;
import org.mapstruct.factory.Mappers;

public interface ScheduleConfigMapper {
    ScheduleConfigMapper INSTANCE = Mappers.getMapper(ScheduleConfigMapper.class);
    SchedulingConfig toEntity(ScheduleConfigDTO scheduleConfigDTO);
    ScheduleConfigDTO toDTO(SchedulingConfig schedulingConfig);
}
