package budkevych.dcsapi.dto.mapper;

import budkevych.dcsapi.dto.request.GameSessionRequestDto;
import budkevych.dcsapi.dto.response.GameCharacterResponseDto;
import budkevych.dcsapi.dto.response.GameSessionResponseDto;
import budkevych.dcsapi.dto.response.UserResponseDto;
import budkevych.dcsapi.model.GameSession;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class GameSessionMapper {
    public GameSessionResponseDto toDto(GameSession session) {
        GameSessionResponseDto dto = new GameSessionResponseDto();
        dto.setId(session.getId());
        dto.setName(session.getSessionName());
        dto.setTimestamp(session.getLastUpdate());

        Set<GameCharacterResponseDto> gameCharacterResponseDtoSet =
                session.getCharactersInSession()
                        .stream()
                        .map(c -> {
                            GameCharacterResponseDto gameCharacterResponseDto =
                                    new GameCharacterResponseDto();
                            gameCharacterResponseDto.setTimestamp(c.getLastUpdate());
                            gameCharacterResponseDto.setName(c.getName());
                            gameCharacterResponseDto.setId(c.getId());
                            gameCharacterResponseDto.setUserId(c.getUserId());
                            gameCharacterResponseDto.setParamMap(Map.of());
                            return gameCharacterResponseDto;
                        }).collect(Collectors.toSet());
        dto.setGameCharacters(gameCharacterResponseDtoSet);

        Set<UserResponseDto> userResponseDtoSet =
                session.getUsers()
                        .stream()
                        .map(u -> {
                            UserResponseDto userResponseDto =
                                    new UserResponseDto();
                            userResponseDto.setId(u.getId());
                            userResponseDto.setEmail(u.getEmail());
                            userResponseDto.setUsername(u.getUsername());
                            userResponseDto.setRoles(List.of());
                            return userResponseDto;
                        }).collect(Collectors.toSet());

        dto.setUsers(userResponseDtoSet);
        dto.setCreatedOn(session.getSessionStartedAt());
        return dto;
    }

    public GameSession toModel(GameSessionRequestDto dto) {
        GameSession gameSession = new GameSession();
        gameSession.setSessionName(dto.getName());
        return gameSession;
    }
}
