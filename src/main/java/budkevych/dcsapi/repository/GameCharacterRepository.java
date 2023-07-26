package budkevych.dcsapi.repository;

import budkevych.dcsapi.model.GameCharacter;
import budkevych.dcsapi.model.ParamMap;
import budkevych.dcsapi.model.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameCharacterRepository extends JpaRepository<GameCharacter, Long> {
    @Query("SELECT c "
            + "FROM GameCharacter c "
            + "JOIN FETCH c.owners o "
            + "WHERE o.id = :userId AND c.isDeleted = :isDeleted")
    List<GameCharacter> findAllByUserIdAndIsDeleted(Long userId, Short isDeleted);

    List<GameCharacter> findAllByIsDeleted(
            Short isDeleted);

    @Query("SELECT c FROM GameCharacter c LEFT JOIN FETCH c.owners "
            + "WHERE c.id = :id AND c.isDeleted = :isDeleted")
    Optional<GameCharacter> findByIdAndIsDeleted(
            Long id,
            Short isDeleted
    );

    @Query("SELECT COUNT(c) FROM GameCharacter c JOIN c.owners o WHERE o.id = :userId")
    Long countAllByOwners(Long userId);

    @Query("SELECT c.paramMap FROM GameCharacter c "
            + "LEFT JOIN c.paramMap "
            + "WHERE c.id = :id AND c.isDeleted = :isDeleted")
    ParamMap findByIdAndIsDeletedParamMap(Long id, Short isDeleted);

    @Query("SELECT c.owners FROM GameCharacter c "
            + "LEFT JOIN c.owners "
            + "WHERE c.id = :id AND c.isDeleted = :isDeleted")
    Set<User> findByIdAndIsDeletedOwners(Long id, Short isDeleted);
}
