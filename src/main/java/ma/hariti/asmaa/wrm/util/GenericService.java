package ma.hariti.asmaa.survey.survey.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenericService<T, ID, C, U, R> {

    C create(C createDTO);

    C getById(ID id);

    Page<C> getAll(Pageable pageable);

    R update(ID id, U updateDTO);

    void delete(ID id);

    boolean exists(ID id);
}

