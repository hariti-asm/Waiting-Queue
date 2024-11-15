package ma.hariti.asmaa.wrm.mapper;

import ma.hariti.asmaa.wrm.dto.VisitDTO;
import ma.hariti.asmaa.wrm.entity.Visit;
import ma.hariti.asmaa.wrm.entity.Visitor;
import ma.hariti.asmaa.wrm.entity.WaitingList;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {VisitorMapper.class})
public interface VisitMapper {

    @Mapping(source = "visitor.id", target = "visitorId")
    @Mapping(source = "waitingList.id", target = "waitingListId")
    VisitDTO toDto(Visit visit);

    @Mapping(source = "visitorId", target = "visitor", qualifiedByName = "mapVisitor")
    @Mapping(source = "waitingListId", target = "waitingList", qualifiedByName = "mapWaitingList")
    Visit toEntity(VisitDTO visitDTO);

    List<VisitDTO> toDtoList(List<Visit> visits);

    List<Visit> toEntityList(List<VisitDTO> visitDTOs);

    @Named("mapVisitor")
    default Visitor mapVisitor(Long id) {
        if (id == null) {
            return null;
        }
        Visitor visitor = new Visitor();
        visitor.setId(id);
        return visitor;
    }

    @Named("mapWaitingList")
    default WaitingList mapWaitingList(Long id) {
        if (id == null) {
            return null;
        }
        WaitingList waitingList = new WaitingList();
        waitingList.setId(id);
        return waitingList;
    }

    @AfterMapping
    default void setRelations(@MappingTarget Visit visit) {
        if (visit.getVisitor() != null) {
            // Ensure proper bidirectional relationship
            if (visit.getVisitor().getVisits() != null) {
                visit.getVisitor().getVisits().add(visit);
            }
        }
    }
}
