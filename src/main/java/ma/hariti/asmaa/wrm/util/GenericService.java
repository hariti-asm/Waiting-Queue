package ma.hariti.asmaa.wrm.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GenericService<T, ID> {

    T save(T entity);
    List<T> saveAll(List<T> entities);
    Optional<T> findById(ID id);
    List<T> findAll();
    Page<T> findAll(Pageable pageable);
    boolean existsById(ID id);
    long count();
    void deleteById(ID id);
    void delete(T entity);
    void deleteAll(List<T> entities);
    void deleteAll();

    T update(ID id, T entity);
}

