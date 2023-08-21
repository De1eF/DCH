package budkevych.dcsapi.service;

import budkevych.dcsapi.model.GameCharacter;
import budkevych.dcsapi.model.ParamMap;
import budkevych.dcsapi.model.User;
import budkevych.dcsapi.repository.GameCharacterRepository;
import budkevych.dcsapi.service.impl.CharacterServiceIml;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CharacterServiceImplTest {
    @InjectMocks
    private CharacterServiceIml charServ;

    private static final PageRequest pageable;

    static {
        pageable = PageRequest.of(0, 5);
    }

    @Mock
    private GameCharacterRepository charRep;

    @Test
    void findOk() {
        GameCharacter fromDb = new GameCharacter();
        fromDb.setId(0L);
        fromDb.setName("Vasylij");
        fromDb.setLastUpdate(10000000L);
        fromDb.setData("{param:1}");
        fromDb.setIsDeleted((short)0);
        fromDb.setPortraitId(0L);
        fromDb.setOwners(null);
        fromDb.setParamMap(null);
        Mockito.when(charRep.findByIdAndIsDeleted(0L, (short)0))
                .thenReturn(Optional.of(fromDb));

        GameCharacter expected = new GameCharacter();
        expected.setId(0L);
        expected.setName("Vasylij");
        expected.setLastUpdate(10000000L);
        expected.setData("{param:1}");
        expected.setIsDeleted((short)0);
        expected.setPortraitId(0L);
        expected.setOwners(new HashSet<>());
        expected.setParamMap(new ParamMap(0L, "{}"));

        GameCharacter actual = charServ.find(0L, (short)0, false, false);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findWithParamMapOk() {
        GameCharacter fromDbWithParamMap = new GameCharacter();
        fromDbWithParamMap.setId(0L);
        fromDbWithParamMap.setName("Vasylij");
        fromDbWithParamMap.setLastUpdate(10000000L);
        fromDbWithParamMap.setData("{param:1}");
        fromDbWithParamMap.setIsDeleted((short)0);
        fromDbWithParamMap.setPortraitId(0L);
        fromDbWithParamMap.setOwners(null);
        fromDbWithParamMap.setParamMap(new ParamMap(0L, "{param:1}"));
        Mockito.when(charRep.findByIdAndIsDeletedWithParamMap(0L, (short)0))
                .thenReturn(Optional.of(fromDbWithParamMap));

        GameCharacter expected = new GameCharacter();
        expected.setId(0L);
        expected.setName("Vasylij");
        expected.setLastUpdate(10000000L);
        expected.setData("{param:1}");
        expected.setIsDeleted((short)0);
        expected.setPortraitId(0L);
        expected.setOwners(new HashSet<>());
        expected.setParamMap(new ParamMap(0L, "{param:1}"));

        GameCharacter actual = charServ.find(0L, (short)0, true, false);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findWithOwnersOk() {
        User owner = new User();
        owner.setId(0L);
        owner.setUsername("Martin");
        owner.setUsername("$idp3904-g0jf");
        owner.setEmail("martin@mail.com");
        owner.setPortraitId(0L);
        owner.setRoles(new HashSet<>());
        GameCharacter fromDbWithOwners = new GameCharacter();
        fromDbWithOwners.setId(0L);
        fromDbWithOwners.setName("Vasylij");
        fromDbWithOwners.setLastUpdate(10000000L);
        fromDbWithOwners.setData("{param:1}");
        fromDbWithOwners.setIsDeleted((short)0);
        fromDbWithOwners.setPortraitId(0L);
        fromDbWithOwners.setOwners(Set.of(owner));
        fromDbWithOwners.setParamMap(null);
        Mockito.when(charRep.findByIdAndIsDeletedWithOwners(0L, (short)0))
                .thenReturn(Optional.of(fromDbWithOwners));

        GameCharacter expected = new GameCharacter();
        expected.setId(0L);
        expected.setName("Vasylij");
        expected.setLastUpdate(10000000L);
        expected.setData("{param:1}");
        expected.setIsDeleted((short)0);
        expected.setPortraitId(0L);
        expected.setOwners(Set.of(owner));
        expected.setParamMap(new ParamMap(0L, "{}"));

        GameCharacter actual = charServ.find(0L, (short)0, false, true);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findWithParamAndOwnersOk() {
        User owner2 = new User();
        owner2.setId(0L);
        owner2.setUsername("Martin");
        owner2.setUsername("$idp3904-g0jf");
        owner2.setEmail("martin@mail.com");
        owner2.setPortraitId(0L);
        owner2.setRoles(new HashSet<>());
        GameCharacter fromDbWithOwnersAndParamMap = new GameCharacter();
        fromDbWithOwnersAndParamMap.setId(0L);
        fromDbWithOwnersAndParamMap.setName("Vasylij");
        fromDbWithOwnersAndParamMap.setLastUpdate(10000000L);
        fromDbWithOwnersAndParamMap.setData("{param:1}");
        fromDbWithOwnersAndParamMap.setIsDeleted((short)0);
        fromDbWithOwnersAndParamMap.setPortraitId(0L);
        fromDbWithOwnersAndParamMap.setOwners(Set.of(owner2));
        fromDbWithOwnersAndParamMap.setParamMap(new ParamMap(0L, "{param:1}"));
        Mockito.when(charRep.findByIdAndIsDeletedWithParamMapAndOwners(0L, (short)0))
                .thenReturn(Optional.of(fromDbWithOwnersAndParamMap));

        GameCharacter expected = new GameCharacter();
        expected.setId(0L);
        expected.setName("Vasylij");
        expected.setLastUpdate(10000000L);
        expected.setData("{param:1}");
        expected.setIsDeleted((short)0);
        expected.setPortraitId(0L);
        expected.setOwners(Set.of(owner2));
        expected.setParamMap(new ParamMap(0L, "{param:1}"));

        GameCharacter actual = charServ.find(0L, (short)0, true, true);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllByUserId() {
        GameCharacter fromDbForUser = new GameCharacter();
        fromDbForUser.setId(0L);
        fromDbForUser.setName("Vasylij");
        fromDbForUser.setLastUpdate(10000000L);
        fromDbForUser.setData("{param:1}");
        fromDbForUser.setIsDeleted((short)0);
        fromDbForUser.setPortraitId(0L);
        fromDbForUser.setOwners(null);
        fromDbForUser.setParamMap(null);
        Mockito.when(charRep.findAllByUserIdAndIsDeleted(0L, (short)0, pageable))
                .thenReturn(List.of(fromDbForUser));

        GameCharacter expected = new GameCharacter();
        expected.setId(0L);
        expected.setName("Vasylij");
        expected.setLastUpdate(10000000L);
        expected.setData("{param:1}");
        expected.setIsDeleted((short)0);
        expected.setPortraitId(0L);
        expected.setOwners(new HashSet<>());
        expected.setParamMap(new ParamMap(0L, "{}"));

        List<GameCharacter> actual = charServ.findAllByUserId(0L, pageable);

        Assertions.assertEquals(List.of(expected), actual);
    }

    @Test
    void save() {
        GameCharacter expected = new GameCharacter();
        expected.setName("name");
        expected.setIsDeleted((short)0);
        expected.setOwners(new HashSet<>());
        expected.setPortraitId(1L);
        expected.setData("{}");
        expected.setParamMap(new ParamMap());
        expected.setLastUpdate(999L);

        GameCharacter toSave = new GameCharacter();
        toSave.setName("name");
        toSave.setIsDeleted((short)0);
        toSave.setOwners(new HashSet<>());
        toSave.setPortraitId(null);

        Mockito.when(charRep.save(any(GameCharacter.class))).thenReturn(null);

        GameCharacter actual = charServ.save(toSave);
        actual.setLastUpdate(999L); //got to find a way to mock System.currentTimeMillis()

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() {
    }

    @Test
    void countRequests() {
    }

    @Test
    void requestOwnership() {
    }

    @Test
    void acceptOwnership() {
    }

    @Test
    void denyOwnership() {
    }

    @Test
    void getOwnershipRequest() {
    }

    @Test
    void getOwnershipRequests() {
    }

    @Test
    void addOwner() {
    }

    @Test
    void removeOwner() {
    }

    @Test
    void delete() {
    }

    @Test
    void permanentDelete() {
    }

    @Test
    void recover() {
    }
}