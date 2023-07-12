package budkevych.dcsapi.repository;

import budkevych.dcsapi.model.GameCharacter;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameCharacterRepository extends JpaRepository<GameCharacter, Long> {
    List<GameCharacter> findAllByUserIdAndIsDeleted(Long userId, Short isDeleted);

    List<GameCharacter> findAllByIsDeleted(
            Short isDeleted);

    Optional<GameCharacter> findByIdAndIsDeleted(
            Long id,
            Short isDeleted
    );

    Long countAllByUserId(Long userId);

    @Query("SELECT c FROM GameCharacter c LEFT JOIN FETCH c.paramMap "
            + "WHERE c.id = :id AND c.isDeleted = :isDeleted")
    Optional<GameCharacter> findByIdAndIsDeletedWithParamMap(Long id, Short isDeleted);
}
