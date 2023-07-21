package budkevych.dcsapi.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "characters")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class GameCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "last_update")
    private Long lastUpdate;
    @Column(name = "user_id")
    private Long userId;
    private String name;
    @Column(name = "data")
    private String data;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    @MapsId
    private ParamMap paramMap;
    @Column(name = "portrait_id")
    private Long portraitId = 0L;
    @Column(name = "is_deleted")
    private Short isDeleted;

    public GameCharacter() {

    }

    public GameCharacter(Long id,
                         Long lastUpdate,
                         Long userId,
                         String name,
                         Long portraitId,
                         Short isDeleted,
                         String data) {
        this.id = id;
        this.lastUpdate = lastUpdate;
        this.userId = userId;
        this.name = name;
        this.portraitId = portraitId;
        this.isDeleted = isDeleted;
        this.data = data;
    }
}
