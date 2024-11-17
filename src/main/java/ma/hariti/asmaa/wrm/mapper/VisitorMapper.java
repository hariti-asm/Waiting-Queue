package ma.hariti.asmaa.wrm.mapper;

import ma.hariti.asmaa.wrm.dto.VisitorDTO;
import ma.hariti.asmaa.wrm.embeddedable.VisitId;
import ma.hariti.asmaa.wrm.entity.Visit;
import ma.hariti.asmaa.wrm.entity.Visitor;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface VisitorMapper {

    @Mapping(source = "visits", target = "visitIds", qualifiedByName = "visitsToVisitIds")
    VisitorDTO toDto(Visitor visitor);

    @Mapping(source = "visitIds", target = "visits", qualifiedByName = "visitIdsToVisits")
    Visitor toEntity(VisitorDTO visitorDTO);

    List<VisitorDTO> toDtoList(List<Visitor> visitors);

    List<Visitor> toEntityList(List<VisitorDTO> visitorDTOs);

    /**
     * Converts a list of Visit entities to a list of Visitor IDs.
     */
    @Named("visitsToVisitIds")
    default List<Long> visitsToVisitIds(List<Visit> visits) {
        if (visits == null) {
            return null;
        }
        return visits.stream()
                .map(visit -> {
                    VisitId visitId = visit.getId();
                    return (visitId != null) ? visitId.getVisitorId() : null;
                })
                .filter(id -> id != null) // Exclude null IDs
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of Visitor IDs to a list of Visit entities.
     * Creates new Visit objects with the Visitor ID and initializes other fields to default values.
     */
    @Named("visitIdsToVisits")
    default List<Visit> visitIdsToVisits(List<Long> visitIds) {
        if (visitIds == null) {
            return null;
        }
        return visitIds.stream()
                .map(id -> {
                    Visit visit = new Visit();
                    visit.setId(new VisitId(id, null)); // Assuming the waitingListId is not set here
                    return visit;
                })
                .collect(Collectors.toList());
    }

    /**
     * Ensures that the visits list in the Visitor entity is never null.
     */
    @AfterMapping
    default void handleNullLists(@MappingTarget Visitor visitor) {
        if (visitor.getVisits() == null) {
            visitor.setVisits(List.of());
        }
    }
}
