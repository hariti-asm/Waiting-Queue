package ma.hariti.asmaa.wrm.service.visitor;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ma.hariti.asmaa.wrm.dto.VisitorDTO;
import ma.hariti.asmaa.wrm.entity.Visitor;
import ma.hariti.asmaa.wrm.repository.VisitorRepository;
import ma.hariti.asmaa.wrm.util.GenericDtoServiceImpl;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
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

        if (entity.getVisits() != null) {
            dto.setVisitIds(entity.getVisits().stream()
                    .map(visit -> visit.getId())
                    .collect(Collectors.toList()));
        }

        return dto;
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

        return entity;
    }

    @Override
    protected void updateEntity(Visitor entity, VisitorDTO dto) {
        if (dto == null) {
            return;
        }

        if (dto.getFirstName() != null) {
            entity.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            entity.setLastName(dto.getLastName());
        }
    }
}