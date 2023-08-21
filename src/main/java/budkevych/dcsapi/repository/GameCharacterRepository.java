package budkevych.dcsapi.repository;

import budkevych.dcsapi.model.GameCharacter;
import budkevych.dcsapi.model.ParamMap;
import budkevych.dcsapi.model.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameCharacterRepository extends JpaRepository<GameCharacter, Long> {
    @Query("SELECT c "
            + "FROM GameCharacter c "
            + "JOIN FETCH c.owners o "
            + "WHERE o.id = :userId AND c.isDeleted = :isDeleted")
    List<GameCharacter> findAllByUserIdAndIsDeleted(Long userId,
                                                    Short isDeleted,
                                                    Pageable pageable);
    @Query("SELECT COUNT(c) FROM GameCharacter c JOIN c.owners o WHERE o.id = :userId")
    Long countAllByOwners(Long userId);

    @Query("SELECT c FROM GameCharacter c "
            + "WHERE c.id = :id AND c.isDeleted = :isDeleted")
    Optional<GameCharacter> findByIdAndIsDeleted(
            Long id,
            Short isDeleted
    );

    @Query("SELECT c FROM GameCharacter c"
            + " LEFT JOIN FETCH c.paramMap "
            + "WHERE c.id = :id AND c.isDeleted = :isDeleted")
    Optional<GameCharacter> findByIdAndIsDeletedWithParamMap(
            Long id,
            Short isDeleted
    );

    @Query("SELECT c FROM GameCharacter c"
            + " LEFT JOIN FETCH c.owners "
            + "WHERE c.id = :id AND c.isDeleted = :isDeleted")
    Optional<GameCharacter> findByIdAndIsDeletedWithOwners(
            Long id,
            Short isDeleted
    );

    @Query("SELECT c FROM GameCharacter c"
            + " LEFT JOIN FETCH c.paramMap "
            + " LEFT JOIN FETCH c.owners "
            + "WHERE c.id = :id AND c.isDeleted = :isDeleted")
    Optional<GameCharacter> findByIdAndIsDeletedWithParamMapAndOwners(
            Long id,
            Short isDeleted
    );
}
