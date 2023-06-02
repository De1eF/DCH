package budkevych.squareapi.repository;

import budkevych.squareapi.model.GameCharacter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameCharacterRepository extends MongoRepository<GameCharacter, String> {
}
