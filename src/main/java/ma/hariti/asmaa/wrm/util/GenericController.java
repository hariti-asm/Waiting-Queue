package ma.hariti.asmaa.survey.survey.util;

import ma.hariti.asmaa.survey.survey.dto.api.ApiResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface GenericController<C, U, R> {
    ResponseEntity<C> create(C createDTO);

    ResponseEntity<R> update(Long id, U updateDTO);

    ResponseEntity<ApiResponseDTO<Page<C>>> getAll(int page, int size, String sortBy, String sortDirection);

    ResponseEntity<C> getById(Long id);
}