package ma.hariti.asmaa.wrm.mapper;

import ma.hariti.asmaa.wrm.dto.VisitorDTO;
import ma.hariti.asmaa.wrm.entity.Visit;
import ma.hariti.asmaa.wrm.entity.Visitor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ma.hariti.asmaa.wrm.embeddedable.VisitId;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface VisitorMapper {

    @Mapping(source = "visits", target = "visitIds", qualifiedByName = "visitsToVisitIds")
    VisitorDTO toDto(Visitor visitor);

    @Mapping(target = "visits", ignore = true)
    Visitor toEntity(VisitorDTO visitorDTO);

    List<VisitorDTO> toDtoList(List<Visitor> visitors);

    List<Visitor> toEntityList(List<VisitorDTO> visitorDTOs);

    @Named("visitsToVisitIds")
    default List<VisitId> visitsToVisitIds(List<Visit> visits) {
        if (visits == null) {
            return null;
        }
        return visits.stream()
                .map(Visit::getId)
                .collect(Collectors.toList());
    }
}
