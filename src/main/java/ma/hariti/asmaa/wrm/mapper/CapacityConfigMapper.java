package ma.hariti.asmaa.wrm.mapper;

import ma.hariti.asmaa.wrm.config.SchedulingConfig;
import ma.hariti.asmaa.wrm.dto.CapacityConfigDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CapacityConfigMapper {
    CapacityConfigDTO toDto(SchedulingConfig.CapacityConfig capacityConfig);
SchedulingConfig.CapacityConfig toEntity(CapacityConfigDTO capacityConfigDTO);

}
