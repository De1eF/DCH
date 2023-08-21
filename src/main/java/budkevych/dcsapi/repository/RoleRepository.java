package budkevych.dcsapi.repository;

import budkevych.dcsapi.model.UserRole;
import budkevych.dcsapi.model.UserRole.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByRoleName(RoleName roleName);
}
