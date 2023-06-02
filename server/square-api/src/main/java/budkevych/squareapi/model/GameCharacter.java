package budkevych.squareapi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("character")
public class GameCharacter {
    @Id
    private String id;
    private Long lastUpdate;
    private String name;

}
