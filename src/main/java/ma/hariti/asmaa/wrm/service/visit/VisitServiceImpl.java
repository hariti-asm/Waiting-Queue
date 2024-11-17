package ma.hariti.asmaa.wrm.service.visit;

import jakarta.persistence.EntityNotFoundException;
import ma.hariti.asmaa.wrm.dto.VisitDTO;
import ma.hariti.asmaa.wrm.embeddedable.VisitId;
import ma.hariti.asmaa.wrm.entity.Visit;
import ma.hariti.asmaa.wrm.entity.Visitor;
import ma.hariti.asmaa.wrm.entity.WaitingList;
import ma.hariti.asmaa.wrm.mapper.VisitMapper;
import ma.hariti.asmaa.wrm.repository.VisitRepository;
import ma.hariti.asmaa.wrm.util.GenericDtoServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class VisitServiceImpl extends GenericDtoServiceImpl<VisitDTO, Visit, VisitId> implements VisitService {

    private final VisitMapper visitMapper;
    private final VisitRepository visitRepository;

    public VisitServiceImpl(VisitRepository visitRepository, VisitMapper visitMapper) {
        super(visitRepository);
        this.visitMapper = visitMapper;
        this.visitRepository = visitRepository;
    }

    @Override
    protected VisitDTO toDto(Visit entity) {
        return visitMapper.toDto(entity);
    }

    @Override
    protected Visit toEntity(VisitDTO dto) {
        Visit visit = visitMapper.toEntity(dto);

        if (dto.getVisitorId() != null && dto.getWaitingListId() != null) {
            // Create and set composite key using visitorId and waitingListId from the DTO
            visit.setId(new VisitId(dto.getVisitorId(), dto.getWaitingListId()));

            // Set Visitor and WaitingList entities using their IDs
            Visitor visitor = new Visitor();
            visitor.setId(dto.getVisitorId());
            visit.setVisitor(visitor);

            WaitingList waitingList = new WaitingList();
            waitingList.setId(dto.getWaitingListId());
            visit.setWaitingList(waitingList);
        }

        return visit;
    }


    @Override
    @Transactional
    public VisitDTO create(VisitDTO dto) {
        validateVisitDto(dto);
        Visit visit = toEntity(dto);
        Visit savedVisit = visitRepository.save(visit);
        return toDto(savedVisit);
    }

    @Override
    @Transactional
    public VisitDTO update(VisitId id, VisitDTO dto) {
        validateVisitDto(dto);
        Visit existingVisit = visitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Visit not found with id: " + id));

        updateEntity(existingVisit, dto);
        Visit updatedVisit = visitRepository.save(existingVisit);
        return toDto(updatedVisit);
    }

    @Override
    protected void updateEntity(Visit entity, VisitDTO dto) {
        // Only update non-null fields from DTO
        if (dto.getArrivalTime() != null) {
            entity.setArrivalTime(dto.getArrivalTime());
        }
        if (dto.getStartTime() != null) {
            entity.setStartTime(dto.getStartTime());
        }

        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getPriority() != null) {
            entity.setPriority(dto.getPriority());
        }
        if (dto.getEstimatedProcessingTime() != null) {
            entity.setEstimatedProcessingTime(dto.getEstimatedProcessingTime());
        }
        // Don't update visitorId and waitingListId as they are part of the composite key
    }

    private void validateVisitDto(VisitDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Visit DTO cannot be null");
        }
        if (dto.getVisitorId() == null) {
            throw new IllegalArgumentException("Visitor ID cannot be null");
        }
        if (dto.getWaitingListId() == null) {
            throw new IllegalArgumentException("Waiting List ID cannot be null");
        }
        // Add additional validation as needed
    }

    // Add any additional custom methods specific to Visit entity here
}