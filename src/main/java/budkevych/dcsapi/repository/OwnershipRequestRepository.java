package budkevych.dcsapi.repository;

import budkevych.dcsapi.model.OwnershipRequest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnershipRequestRepository extends JpaRepository<OwnershipRequest, Long> {
    List<OwnershipRequest> findAllByOwnerId(Long ownerId);

    Long countByOwnerId(Long ownerId);
}
