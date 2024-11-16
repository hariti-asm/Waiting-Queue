package ma.hariti.asmaa.wrm.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.wrm.dto.VisitorDTO;
import ma.hariti.asmaa.wrm.service.visitor.VisitorDtoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/visitors")
@RequiredArgsConstructor
@Tag(name = "Visitor", description = "Visitor management APIs")
public class VisitorController {

    private final VisitorDtoService visitorService;

    @PostMapping
    @Operation(summary = "Create a new visitor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Visitor created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<VisitorDTO> createVisitor(
            @Valid @RequestBody VisitorDTO visitorDTO) {
        VisitorDTO createdVisitor = visitorService.create(visitorDTO);
        return new ResponseEntity<>(createdVisitor, HttpStatus.CREATED);
    }

    @PostMapping("/batch")
    @Operation(summary = "Create multiple visitors")
    public ResponseEntity<List<VisitorDTO>> createVisitors(
            @Valid @RequestBody List<VisitorDTO> visitors) {
        List<VisitorDTO> createdVisitors = visitorService.createAll(visitors);
        return new ResponseEntity<>(createdVisitors, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a visitor by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visitor found"),
            @ApiResponse(responseCode = "404", description = "Visitor not found")
    })
    public ResponseEntity<VisitorDTO> getVisitor(
            @Parameter(description = "Visitor ID", required = true)
            @PathVariable Long id) {
        return visitorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all visitors with pagination")
    public ResponseEntity<Page<VisitorDTO>> getAllVisitors(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<VisitorDTO> visitors = visitorService.findAll(pageable);
        return ResponseEntity.ok(visitors);
    }

    @GetMapping("/list")
    @Operation(summary = "Get all visitors as a list")
    public ResponseEntity<List<VisitorDTO>> getAllVisitorsList() {
        List<VisitorDTO> visitors = visitorService.findAll();
        return ResponseEntity.ok(visitors);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a visitor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visitor updated successfully"),
            @ApiResponse(responseCode = "404", description = "Visitor not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<VisitorDTO> updateVisitor(
            @Parameter(description = "Visitor ID", required = true)
            @PathVariable Long id,
            @Valid @RequestBody VisitorDTO visitorDTO) {
        VisitorDTO updatedVisitor = visitorService.update(id, visitorDTO);
        return ResponseEntity.ok(updatedVisitor);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a visitor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Visitor deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Visitor not found")
    })
    public ResponseEntity<Void> deleteVisitor(
            @Parameter(description = "Visitor ID", required = true)
            @PathVariable Long id) {
        visitorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/batch")
    @Operation(summary = "Delete multiple visitors")
    public ResponseEntity<Void> deleteVisitors(
            @RequestBody List<VisitorDTO> visitors) {
        visitorService.deleteAll(visitors);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/batch/{ids}")
    @Operation(summary = "Get multiple visitors by IDs")
    public ResponseEntity<List<VisitorDTO>> getVisitorsByIds(
            @Parameter(description = "Comma-separated visitor IDs", required = true)
            @PathVariable List<Long> ids) {
        List<VisitorDTO> visitors = visitorService.findAllById(ids);
        return ResponseEntity.ok(visitors);
    }

    @GetMapping("/count")
    @Operation(summary = "Get total count of visitors")
    public ResponseEntity<Long> getVisitorsCount() {
        return ResponseEntity.ok(visitorService.count());
    }
}