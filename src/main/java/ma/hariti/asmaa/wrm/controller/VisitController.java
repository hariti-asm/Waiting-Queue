package ma.hariti.asmaa.wrm.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.wrm.dto.VisitDTO;
import ma.hariti.asmaa.wrm.embeddedable.VisitId;
import ma.hariti.asmaa.wrm.service.visit.VisitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
public class VisitController {
    private final VisitService visitService;

    @PostMapping
    public ResponseEntity<VisitDTO> create(@Valid @RequestBody VisitDTO visitDTO) {
        return new ResponseEntity<>(visitService.create(visitDTO), HttpStatus.CREATED);
    }

    @GetMapping("/visitor/{visitorId}/waitingList/{waitingListId}")
    public ResponseEntity<VisitDTO> findById(@PathVariable Long visitorId,
                                             @PathVariable Long waitingListId) {
        VisitId visitId = new VisitId(visitorId, waitingListId);
        return visitService.findById(visitId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<VisitDTO>> findAll() {
        return ResponseEntity.ok(visitService.findAll());
    }

    @PutMapping("/visitor/{visitorId}/waitingList/{waitingListId}")
    public ResponseEntity<VisitDTO> update(@PathVariable Long visitorId,
                                           @PathVariable Long waitingListId,
                                           @Valid @RequestBody VisitDTO visitDTO) {
        VisitId visitId = new VisitId(visitorId, waitingListId);
        return ResponseEntity.ok(visitService.update(visitId, visitDTO));
    }

    @DeleteMapping("/visitor/{visitorId}/waitingList/{waitingListId}")
    public ResponseEntity<Void> delete(@PathVariable Long visitorId,
                                       @PathVariable Long waitingListId) {
        VisitId visitId = new VisitId(visitorId, waitingListId);
        visitService.deleteById(visitId);
        return ResponseEntity.noContent().build();
    }
}