package budkevych.dcsapi.repository;

import budkevych.dcsapi.model.GameCharacter;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameCharacterRepository extends JpaRepository<GameCharacter, Long> {
    @Query("SELECT NEW GameCharacter(c.id,"
            + " c.lastUpdate,"
            + " c.userId,"
            + " c.name,"
            + " c.portraitId,"
            + " c.isDeleted,"
            + " c.data) "
            + "FROM GameCharacter c "
            + "WHERE c.userId = :userId AND c.isDeleted = :isDeleted")
    List<GameCharacter> findAllByUserIdAndIsDeleted(Long userId, Short isDeleted);

    List<GameCharacter> findAllByIsDeleted(
            Short isDeleted);

    @Query("SELECT NEW GameCharacter(c.id,"
            + " c.lastUpdate,"
            + " c.userId, "
            + "c.name,"
            + " c.portraitId,"
            + " c.isDeleted,"
            + " c.data) "
            + "FROM GameCharacter c "
            + "WHERE c.id = :id AND c.isDeleted = :isDeleted")
    Optional<GameCharacter> findByIdAndIsDeleted(
            Long id,
            Short isDeleted
    );

    Long countAllByUserId(Long userId);

    @Query("SELECT c FROM GameCharacter c LEFT JOIN FETCH c.paramMap "
            + "WHERE c.id = :id AND c.isDeleted = :isDeleted")
    Optional<GameCharacter> findByIdAndIsDeletedWithParamMap(Long id, Short isDeleted);
}
