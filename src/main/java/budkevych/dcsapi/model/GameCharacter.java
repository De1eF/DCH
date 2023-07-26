package budkevych.dcsapi.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Set;
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
    @ManyToMany(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "characters_users",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> owners;
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
                         Set<User> owners,
                         String name,
                         Long portraitId,
                         Short isDeleted,
                         String data) {
        this.id = id;
        this.lastUpdate = lastUpdate;
        this.owners = owners;
        this.name = name;
        this.portraitId = portraitId;
        this.isDeleted = isDeleted;
        this.data = data;
    }
}
