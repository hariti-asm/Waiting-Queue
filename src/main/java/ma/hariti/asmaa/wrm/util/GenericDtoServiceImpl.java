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
import java.util.stream.Collectors;

@Transactional
public abstract class GenericDtoServiceImpl<D, T extends BaseEntity, ID> implements GenericDtoService<D, ID> {
    private static final Logger log = LoggerFactory.getLogger(GenericDtoServiceImpl.class);

    protected final JpaRepository<T, ID> repository;
    protected final Class<D> dtoClass;
    protected final Class<T> entityClass;

    @SuppressWarnings("unchecked")
    protected GenericDtoServiceImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.dtoClass = (Class<D>) genericSuperclass.getActualTypeArguments()[0];
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[1];
    }

    // Abstract methods for DTO-Entity conversion
    protected abstract D toDto(T entity);
    protected abstract T toEntity(D dto);
    protected abstract void updateEntity(T entity, D dto);

    @Override
    @Transactional
    public D create(D dto) {
        log.debug("Creating {} : {}", dtoClass.getSimpleName(), dto);
        T entity = toEntity(dto);
        return toDto(repository.save(entity));
    }

    @Override
    @Transactional
    public List<D> createAll(List<D> dtos) {
        log.debug("Creating {} entities of type {}", dtos.size(), dtoClass.getSimpleName());
        List<T> entities = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        return repository.saveAll(entities).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<D> findById(ID id) {
        log.debug("Finding {} with id: {}", dtoClass.getSimpleName(), id);
        return repository.findById(id).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<D> findAll() {
        log.debug("Finding all {}", dtoClass.getSimpleName());
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<D> findAll(Pageable pageable) {
        log.debug("Finding {} page {} of size {}",
                dtoClass.getSimpleName(),
                pageable.getPageNumber(),
                pageable.getPageSize()
        );
        return repository.findAll(pageable).map(this::toDto);
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
        log.debug("Deleting {} with id: {}", dtoClass.getSimpleName(), id);
        if (!existsById(id)) {
            throw new EntityNotFoundException(
                    String.format("%s not found with id: %s", dtoClass.getSimpleName(), id)
            );
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(D dto) {
        log.debug("Deleting {}: {}", dtoClass.getSimpleName(), dto);
        T entity = toEntity(dto);
        repository.delete(entity);
    }

    @Override
    @Transactional
    public void deleteAll(List<D> dtos) {
        log.debug("Deleting {} entities of type {}", dtos.size(), dtoClass.getSimpleName());
        List<T> entities = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        repository.deleteAll(entities);
    }

    @Override
    @Transactional
    public void deleteAll() {
        log.debug("Deleting all entities of type {}", dtoClass.getSimpleName());
        repository.deleteAll();
    }

    @Override
    @Transactional
    public D update(ID id, D dto) {
        log.debug("Updating {} with id: {}", dtoClass.getSimpleName(), id);
        if (!existsById(id)) {
            throw new EntityNotFoundException(
                    String.format("%s not found with id: %s", dtoClass.getSimpleName(), id)
            );
        }

        T existingEntity = repository.findById(id).orElseThrow();
        updateEntity(existingEntity, dto);
        return toDto(repository.save(existingEntity));
    }

    @Transactional(readOnly = true)
    public List<D> findAllById(List<ID> ids) {
        log.debug("Finding {} entities by ids", dtoClass.getSimpleName());
        return repository.findAllById(ids).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}