package ma.hariti.asmaa.wrm.util;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
@Transactional
public abstract class GenericServiceImpl<T extends BaseEntity, ID> implements GenericService <T, ID> {
    private static final Logger log = LoggerFactory.getLogger(GenericServiceImpl.class);

    protected final JpaRepository<T, ID> repository;
    protected final Class<T> entityClass;

    @SuppressWarnings("unchecked")
    protected GenericServiceImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
        this.entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    @Transactional
    public T save(T entity) {
        log.debug("Saving {} : {}", entityClass.getSimpleName(), entity);
        return repository.save(entity);
    }

    @Override
    @Transactional
    public List<T> saveAll(List<T> entities) {
        log.debug("Saving {} entities of type {}", entities.size(), entityClass.getSimpleName());
        return repository.saveAll(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<T> findById(ID id) {
        log.debug("Finding {} with id: {}", entityClass.getSimpleName(), id);
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        log.debug("Finding all {}", entityClass.getSimpleName());
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> findAll(Pageable pageable) {
        log.debug("Finding {} page {} of size {}",
                entityClass.getSimpleName(),
                pageable.getPageNumber(),
                pageable.getPageSize()
        );
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }

    @Override
    @Transactional
    public void deleteById(ID id) {
        log.debug("Deleting {} with id: {}", entityClass.getSimpleName(), id);
        if (!existsById(id)) {
            throw new EntityNotFoundException(
                    String.format("%s not found with id: %s", entityClass.getSimpleName(), id)
            );
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        log.debug("Deleting {}: {}", entityClass.getSimpleName(), entity);
        repository.delete(entity);
    }

    @Override
    @Transactional
    public void deleteAll(List<T> entities) {
        log.debug("Deleting {} entities of type {}", entities.size(), entityClass.getSimpleName());
        repository.deleteAll(entities);
    }

    @Override
    @Transactional
    public void deleteAll() {
        log.debug("Deleting all entities of type {}", entityClass.getSimpleName());
        repository.deleteAll();
    }

    @Override
    @Transactional
    public T update(ID id, T entity) {
        log.debug("Updating {} with id: {}", entityClass.getSimpleName(), id);
        if (!existsById(id)) {
            throw new EntityNotFoundException(
                    String.format("%s not found with id: %s", entityClass.getSimpleName(), id)
            );
        }
        entity.setId((Long) id);
        return repository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<T> findAllById(List<ID> ids) {
        log.debug("Finding {} entities by ids", entityClass.getSimpleName());
        return repository.findAllById(ids);
    }
}