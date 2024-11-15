package ma.hariti.asmaa.wrm.mapper;

import ma.hariti.asmaa.wrm.dto.WaitingListDto;
import ma.hariti.asmaa.wrm.entity.WaitingList;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {VisitMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WaitingListMapper {

    @Mapping(target = "visits", source = "visits")
    WaitingListDto toDto(WaitingList entity);

    @Mapping(target = "visits", source = "visits")
    WaitingList toEntity(WaitingListDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "visits", source = "visits")
    void updateEntityFromDto(WaitingListDto dto, @MappingTarget WaitingList entity);

    @AfterMapping
    default void linkVisits(@MappingTarget WaitingList waitingList) {
        if (waitingList.getVisits() != null) {
            waitingList.getVisits().forEach(visit -> visit.setWaitingList(waitingList));
        }
    }

    @Named("withoutVisits")
    @Mapping(target = "visits", ignore = true)
    WaitingListDto toDtoWithoutVisits(WaitingList entity);

    @Named("withoutVisits")
    @Mapping(target = "visits", ignore = true)
    WaitingList toEntityWithoutVisits(WaitingListDto dto);
}