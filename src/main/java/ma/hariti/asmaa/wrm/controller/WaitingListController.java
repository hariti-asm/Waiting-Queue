package ma.hariti.asmaa.wrm.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import ma.hariti.asmaa.wrm.dto.ApiResponseDTO;
import ma.hariti.asmaa.wrm.dto.VisitDTO;
import ma.hariti.asmaa.wrm.dto.WaitingListDto;
import ma.hariti.asmaa.wrm.service.waitingList.WaitingListDtoServiceImpl;
import ma.hariti.asmaa.wrm.util.GenericDtoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/waiting-lists")
public class WaitingListController {
    private final WaitingListDtoServiceImpl waitingListService;
    private static final Logger log = LoggerFactory.getLogger(GenericDtoServiceImpl.class);

    public WaitingListController(WaitingListDtoServiceImpl waitingListService) {
        this.waitingListService = waitingListService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<WaitingListDto>> createWaitingList(@Valid @RequestBody WaitingListDto waitingListDto) {
        log.info("Received request to create waiting list: {}", waitingListDto);
        try {
            WaitingListDto savedWaitingList = waitingListService.create(waitingListDto);
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
    public ResponseEntity<ApiResponseDTO<WaitingListDto>> getWaitingListById(@PathVariable Long id) {
        return waitingListService.findById(id)
                .map(waitingList -> ResponseEntity.ok(ApiResponseDTO.success(waitingList)))
                .orElse(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDTO.error("Waiting list not found", HttpStatus.NOT_FOUND.value())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Object>> deleteWaitingList(@PathVariable Long id) {
        return waitingListService.findById(id)
                .map(waitingList -> {
                    waitingListService.deleteById(id);
                    return ResponseEntity.ok(ApiResponseDTO.success(null));
                })
                .orElse(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDTO.error("Waiting list not found", HttpStatus.NOT_FOUND.value())));
    }

    @PostMapping("/{id}/schedule")
    public ResponseEntity<ApiResponseDTO<List<VisitDTO>>> scheduleVisits(@PathVariable Long id) {
        try {
            List<VisitDTO> scheduledVisits = waitingListService.scheduleVisits(id);
            return ResponseEntity.ok(ApiResponseDTO.success(scheduledVisits));
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.error(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        } catch (Exception e) {
            log.error("Error scheduling visits", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<WaitingListDto>>> getAllWaitingLists() {
        try {
            List<WaitingListDto> waitingLists = waitingListService.findAll();
            log.info("Fetched {} waiting lists", waitingLists.size());
            return ResponseEntity.ok(ApiResponseDTO.success(waitingLists));
        } catch (Exception e) {
            log.error("Error fetching waiting lists", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Error fetching waiting lists", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}