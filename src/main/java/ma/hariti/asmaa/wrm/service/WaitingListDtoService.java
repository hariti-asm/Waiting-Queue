package ma.hariti.asmaa.wrm.service;

import jakarta.persistence.EntityNotFoundException;
import ma.hariti.asmaa.wrm.dto.VisitDTO;
import ma.hariti.asmaa.wrm.dto.WaitingListDto;
import ma.hariti.asmaa.wrm.entity.Visit;
import ma.hariti.asmaa.wrm.entity.WaitingList;
import ma.hariti.asmaa.wrm.mapper.VisitMapper;
import ma.hariti.asmaa.wrm.mapper.WaitingListMapper;
import ma.hariti.asmaa.wrm.repository.SchedulingStrategy;
import ma.hariti.asmaa.wrm.util.GenericDtoServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WaitingListDtoService extends GenericDtoServiceImpl<WaitingListDto, WaitingList, Long> {
    private final SchedulingStrategyFactory strategyFactory;
    private final WaitingListMapper waitingListMapper;
    private final VisitMapper visitMapper;

    protected WaitingListDtoService(JpaRepository<WaitingList, Long> repository,
                                    SchedulingStrategyFactory strategyFactory,
                                    WaitingListMapper waitingListMapper,
                                    VisitMapper visitMapper) {
        super(repository);
        this.strategyFactory = strategyFactory;
        this.waitingListMapper = waitingListMapper;
        this.visitMapper = visitMapper;
    }

    @Override
    protected WaitingListDto toDto(WaitingList entity) {
        return waitingListMapper.toDto(entity);
    }

    @Override
    protected WaitingList toEntity(WaitingListDto dto) {
        return waitingListMapper.toEntity(dto);
    }

    @Override
    protected void updateEntity(WaitingList entity, WaitingListDto dto) {
        waitingListMapper.updateEntityFromDto(dto, entity);
    }

    public List<VisitDTO> scheduleVisits(Long waitingListId) {
        WaitingList waitingList = repository.findById(waitingListId)
                .orElseThrow(() -> new EntityNotFoundException("WaitingList not found"));

        SchedulingStrategy strategy = strategyFactory.getStrategy(waitingList.getAlgorithm());
        List<Visit> scheduledVisits = strategy.schedule(waitingList.getVisits());

        return scheduledVisits.stream()
                .map(visitMapper::toDto)
                .collect(Collectors.toList());
    }
}