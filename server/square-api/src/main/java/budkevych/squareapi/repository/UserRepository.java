package budkevych.squareapi.repository;

import budkevych.squareapi.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email = :email")
    Optional<User> findUserByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username = :username")
    List<User> findAllByUsername(String username);
}
