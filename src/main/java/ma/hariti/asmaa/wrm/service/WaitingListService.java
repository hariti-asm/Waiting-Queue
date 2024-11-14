package ma.hariti.asmaa.wrm.service;

import jakarta.persistence.EntityNotFoundException;
import ma.hariti.asmaa.wrm.entity.Visit;
import ma.hariti.asmaa.wrm.entity.WaitingList;
import ma.hariti.asmaa.wrm.repository.SchedulingStrategy;
import ma.hariti.asmaa.wrm.util.GenericService;
import ma.hariti.asmaa.wrm.util.GenericServiceImpl;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WaitingListService extends GenericServiceImpl<WaitingList, Long> {
    private final SchedulingStrategyFactory strategyFactory;

    protected WaitingListService(JpaRepository<WaitingList, Long> repository, SchedulingStrategyFactory strategyFactory) {
        super(repository);
        this.strategyFactory = strategyFactory;
    }
    public List<Visit> scheduleVisits(Long waitingListId) {
        WaitingList waitingList = findById(waitingListId).orElseThrow(() -> new EntityNotFoundException("WaitingList not found"));
        SchedulingStrategy strategy = strategyFactory.getStrategy(waitingList.getAlgorithm());
        return strategy.schedule(waitingList.getVisits());
    }
}
