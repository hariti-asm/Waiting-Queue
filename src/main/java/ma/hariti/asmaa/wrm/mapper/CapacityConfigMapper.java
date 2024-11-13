package ma.hariti.asmaa.wrm.mapper;

import ma.hariti.asmaa.wrm.config.SchedulingConfig;
import ma.hariti.asmaa.wrm.dto.CapacityConfigDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CapacityConfigMapper {
    CapacityConfigMapper INSTANCE = Mappers.getMapper(CapacityConfigMapper.class);
    CapacityConfigDTO toDto(SchedulingConfig.CapacityConfig capacityConfig);
SchedulingConfig.CapacityConfig toEntity(CapacityConfigDTO capacityConfigDTO);

}
