package ma.hariti.asmaa.wrm.controller;

import jakarta.validation.Valid;
import ma.hariti.asmaa.wrm.dto.ApiResponseDTO;
import ma.hariti.asmaa.wrm.entity.Visit;
import ma.hariti.asmaa.wrm.entity.WaitingList;
import ma.hariti.asmaa.wrm.service.WaitingListService;
import ma.hariti.asmaa.wrm.util.GenericServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/waiting-lists")
public class WaitingListController {
    private final WaitingListService waitingListService;
    private static final Logger log = LoggerFactory.getLogger(GenericServiceImpl.class);

    public WaitingListController(WaitingListService waitingListService) {
        this.waitingListService = waitingListService;
    }
    @PostMapping
    public ResponseEntity<ApiResponseDTO<WaitingList>> createWaitingList(@Valid @RequestBody WaitingList waitingList) {
        log.info("Received request to create waiting list: {}", waitingList);
        try {
            WaitingList savedWaitingList = waitingListService.save(waitingList);
            log.info("Successfully created waiting list: {}", savedWaitingList);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponseDTO.success(savedWaitingList));
        } catch (Exception e) {
            log.error("Error creating waiting list", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<WaitingList> getWaitingListById(@PathVariable Long id) {
        return waitingListService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWaitingList(@PathVariable Long id) {
        return waitingListService.findById(id)
                .map(waitingList -> {
                    waitingListService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/{id}/schedule")
    public ResponseEntity<List<Visit>> scheduleVisits(@PathVariable Long id) {
        List<Visit> scheduledVisits = waitingListService.scheduleVisits(id);
        return ResponseEntity.ok(scheduledVisits);
    }
}
