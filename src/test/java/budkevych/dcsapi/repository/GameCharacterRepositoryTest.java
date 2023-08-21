package budkevych.dcsapi.repository;

import budkevych.dcsapi.model.GameCharacter;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;
/*
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GameCharacterRepositoryTest {
    static MySQLContainer<?> database = new MySQLContainer<>("mysql:8")
            .withDatabaseName("square_db")
            .withPassword("1234")
            .withUsername("root");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        propertyRegistry.add("spring.datasource.password", database::getPassword);
        propertyRegistry.add("spring.datasource.username", database::getUsername);
    }

    @Autowired
    private GameCharacterRepository productRepository;

    @Test
    @Sql("/scripts/init_10_characters.sql")
    void findAllByUserIdAndIsDeleted() {
        int pageLength = 5;
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "lastUpdate");
        Sort sort = Sort.by(order);
        PageRequest pageRequest = PageRequest.of(
                0,
                pageLength,
                sort);
        List<GameCharacter> actual =
                productRepository.findAllByUserIdAndIsDeleted(
                0L,
                (short) 0,
                pageRequest);
        Assertions.assertEquals(pageLength, actual.size());
    }

    @Test
    void countAllByOwners() {
    }

    @Test
    void findByIdAndIsDeleted() {
    }

    @Test
    void findByIdAndIsDeletedWithParamMap() {
    }

    @Test
    void findByIdAndIsDeletedWithOwners() {
    }

    @Test
    void findByIdAndIsDeletedWithParamMapAndOwners() {
    }
}
 */
