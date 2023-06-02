package budkevych.squareapi.repository;

import budkevych.squareapi.model.GameCharacter;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameCharacterRepository extends JpaRepository<GameCharacter, Long> {
    List<GameCharacter> findAllByUserId(Long userId);

    Long countAllByUserId(Long userId);
}
