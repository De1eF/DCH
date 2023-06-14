package budkevych.squareapi.repository;

import budkevych.squareapi.model.GameCharacter;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameCharacterRepository extends JpaRepository<GameCharacter, Long> {
    List<GameCharacter> findAllByUserIdAndIsDeleted(Long userId, Short isDeleted);

    Long countAllByUserId(Long userId);

    List<GameCharacter> findAllByIsDeleted(Short isDeleted);

    Optional<GameCharacter> findByIdAndIsDeleted(Long id, Short isDeleted);
}
