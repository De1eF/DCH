package budkevych.squareapi.repository;

import budkevych.squareapi.model.UserRole;
import budkevych.squareapi.model.UserRole.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByRoleName(RoleName roleName);
}
