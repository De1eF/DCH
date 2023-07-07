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

    @Query("SELECT c.id,"
            + " c.lastUpdate,"
            + " c.userId,"
            + " c.name,"
            + " c.isDeleted"
            + " FROM GameCharacter c "
            + " WHERE c.isDeleted = :isDeleted"
            + " AND c.userId = :userId")
    List<Object[]> findAllByUserIdAndIsDeletedNotLoadingParamMap(
            Long userId,
            Short isDeleted);

    Long countAllByUserId(Long userId);

    List<GameCharacter> findAllByIsDeleted(Short isDeleted);

    Optional<GameCharacter> findByIdAndIsDeleted(Long id, Short isDeleted);
}
