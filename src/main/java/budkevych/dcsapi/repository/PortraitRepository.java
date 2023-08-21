package budkevych.dcsapi.repository;

import budkevych.dcsapi.model.Portrait;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PortraitRepository extends JpaRepository<Portrait, Long> {
}
