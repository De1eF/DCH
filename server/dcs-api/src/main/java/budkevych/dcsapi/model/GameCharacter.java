package budkevych.dcsapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "characters")
@Getter
@Setter
@EqualsAndHashCode
public class GameCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "last_update")
    private Long lastUpdate;
    @Column(name = "user_id")
    private Long userId;
    private String name;
    @Column(name = "param_map")
    private String paramMap;
    @Column(name = "is_deleted")
    private Short isDeleted;
}
