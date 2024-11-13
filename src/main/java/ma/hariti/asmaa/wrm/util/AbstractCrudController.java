package ma.hariti.asmaa.survey.survey.util;

import lombok.RequiredArgsConstructor;
import ma.hariti.asmaa.survey.survey.dto.api.ApiResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


/**
 * Optional base class for CRUD operations - controllers can inherit if needed
 */
@RequiredArgsConstructor
public abstract class AbstractCrudController<C, U, R> implements GenericController<C, U, R> {

    protected final GenericService<?, Long, C, U, R> service;

    @Override
    public ResponseEntity<C> create(C createDTO) {
        C created = service.create(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Override
    public ResponseEntity<R> update(Long id, U updateDTO) {
        R updated = service.update(id, updateDTO);
        return ResponseEntity.ok(updated);
    }

    @Override
    public ResponseEntity<ApiResponseDTO<Page<C>>> getAll(
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {
        Pageable pageable = createPageable(page, size, sortBy, sortDirection);
        Page<C> resultPage = service.getAll(pageable);
        return ResponseEntity.ok(
                ApiResponseDTO.success(resultPage, (int) resultPage.getTotalElements())
        );
    }

    @Override
    public ResponseEntity<C> getById(Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    protected Pageable createPageable(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        return PageRequest.of(page, size, sort);
    }
}
