package ma.hariti.asmaa.wrm.repository;

import ma.hariti.asmaa.wrm.entity.WaitingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaitingListRepository extends JpaRepository<WaitingList, Long> {
}
