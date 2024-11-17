package ma.hariti.asmaa.wrm.service.visitor;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import ma.hariti.asmaa.wrm.dto.VisitorDTO;
import ma.hariti.asmaa.wrm.entity.Visitor;
import ma.hariti.asmaa.wrm.repository.VisitorRepository;
import ma.hariti.asmaa.wrm.util.GenericDtoServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class VisitorDtoServiceImpl extends GenericDtoServiceImpl<VisitorDTO, Visitor, Long> implements VisitorDtoService {

    private final VisitorRepository visitorRepository;

    public VisitorDtoServiceImpl(VisitorRepository visitorRepository) {
        super(visitorRepository);
        this.visitorRepository = visitorRepository;
    }

    @Override
    protected VisitorDTO toDto(Visitor entity) {
        if (entity == null) {
            return null;
        }

        VisitorDTO dto = new VisitorDTO();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());


        // Map visits to visitIds using the composite key's visitorId
        if (entity.getVisits() != null && !entity.getVisits().isEmpty()) {
            dto.setVisitIds(entity.getVisits().stream()
                    .map(visit -> visit.getId().getVisitorId())
                    .collect(Collectors.toList()));
        } else {
            dto.setVisitIds(new ArrayList<>());
        }

        return dto;
    }

    @Override
    @Transactional
    public VisitorDTO create(VisitorDTO dto) {
        validateVisitorDto(dto);
        Visitor visitor = toEntity(dto);
        Visitor savedVisitor = visitorRepository.save(visitor);
        return toDto(savedVisitor);
    }

    @Override
    @Transactional
    public VisitorDTO update(Long id, VisitorDTO dto) {
        validateVisitorDto(dto);
        Visitor existingVisitor = visitorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Visitor not found with id: " + id));

        updateEntity(existingVisitor, dto);
        Visitor updatedVisitor = visitorRepository.save(existingVisitor);
        return toDto(updatedVisitor);
    }

    @Override
    protected Visitor toEntity(VisitorDTO dto) {
        if (dto == null) {
            return null;
        }

        Visitor entity = new Visitor();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());

        // Don't set visits here as they should be managed by the Visit entity
        // and its relationships

        return entity;
    }

    @Override
    protected void updateEntity(Visitor entity, VisitorDTO dto) {
        if (dto == null || entity == null) {
            return;
        }

        // Update basic fields
        if (dto.getFirstName() != null) {
            entity.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            entity.setLastName(dto.getLastName());
        }

        // Don't update visits directly through the visitor
        // Visits should be managed through the Visit service
    }

    private void validateVisitorDto(VisitorDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Visitor DTO cannot be null");
        }
        if (dto.getFirstName() == null || dto.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (dto.getLastName() == null || dto.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
    }
}