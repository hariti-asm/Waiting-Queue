package ma.hariti.asmaa.wrm.repository;

import ma.hariti.asmaa.wrm.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {
}
