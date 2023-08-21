package budkevych.dcsapi.dto.mapper;

import budkevych.dcsapi.dto.request.GameSessionRequestDto;
import budkevych.dcsapi.dto.response.GameCharacterResponseDto;
import budkevych.dcsapi.dto.response.GameSessionResponseDto;
import budkevych.dcsapi.dto.response.UserResponseDto;
import budkevych.dcsapi.model.GameSession;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameSessionMapper {
    private final GameCharacterMapper gameCharacterMapper;
    private final UserMapper userMapper;

    public GameSessionResponseDto toDto(GameSession session) {
        GameSessionResponseDto dto = new GameSessionResponseDto();
        dto.setId(session.getId());
        dto.setName(session.getName());
        dto.setTimestamp(session.getLastUpdate());

        Set<GameCharacterResponseDto> gameCharacterResponseDtoSet =
                session.getCharactersInSession()
                        .stream()
                        .map(gameCharacterMapper::toDto).collect(Collectors.toSet());
        dto.setGameCharacters(gameCharacterResponseDtoSet);

        Set<UserResponseDto> userResponseDtoSet =
                session.getUsers()
                        .stream()
                        .map(userMapper::mapToDto).collect(Collectors.toSet());

        dto.setUsers(userResponseDtoSet);
        dto.setCreatedOn(session.getStartedAt());
        return dto;
    }

    public GameSession toModel(GameSessionRequestDto dto) {
        GameSession gameSession = new GameSession();
        gameSession.setName(dto.getName());
        return gameSession;
    }
}
