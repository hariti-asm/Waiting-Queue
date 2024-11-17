package ma.hariti.asmaa.wrm.mapper;

import ma.hariti.asmaa.wrm.dto.VisitDTO;
import ma.hariti.asmaa.wrm.entity.Visit;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {VisitorMapper.class})
public interface VisitMapper {

    @Mapping(source = "visitorId", target = "visitor.id")
    @Mapping(source = "waitingListId", target = "waitingList.id")
    @Mapping(target = "id", expression = "java(new VisitId(dto.getVisitorId(), dto.getWaitingListId()))")
    Visit toEntity(VisitDTO dto);

    @Mapping(source = "visitor.id", target = "visitorId")
    @Mapping(source = "id.waitingListId", target = "waitingListId")
    VisitDTO toDto(Visit visit);

    List<VisitDTO> toDtoList(List<Visit> visits);

    List<Visit> toEntityList(List<VisitDTO> visitDTOs);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateVisit(VisitDTO dto, @MappingTarget Visit entity);
}
