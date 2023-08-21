package budkevych.dcsapi.repository;

import budkevych.dcsapi.model.GameSession;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
    @Query("SELECT s FROM GameSession s JOIN FETCH s.users u WHERE u.id = :userId")
    List<GameSession> findAllByUser(Long userId);

    @Query("SELECT session"
            + " FROM GameSession session"
            + " LEFT JOIN FETCH session.users u"
            + " LEFT JOIN FETCH u.roles r"
            + " LEFT JOIN FETCH session.charactersInSession cs"
            + " WHERE session.id = :id")
    Optional<GameSession> findByIdFetched(Long id);
}
