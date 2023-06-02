package budkevych.squareapi.dto.mapper;

import budkevych.squareapi.dto.request.GameCharacterRequestDto;
import budkevych.squareapi.dto.response.GameCharacterResponseDto;
import budkevych.squareapi.model.GameCharacter;
import org.springframework.stereotype.Component;

@Component
public class GameCharacterMapper {
    public GameCharacterResponseDto toDto(GameCharacter character) {
        GameCharacterResponseDto dto = new GameCharacterResponseDto();
        dto.setId(character.getId());
        dto.setTimestamp(character.getLastUpdate());
        dto.setUserId(character.getUserId());
        dto.setName(character.getName());
        dto.setParamMap(character.getParamMap());
        return dto;
    }

    public GameCharacter toModel(GameCharacterRequestDto dto) {
        GameCharacter gameCharacter = new GameCharacter();
        gameCharacter.setUserId(dto.getUserId());
        gameCharacter.setName(dto.getName());
        gameCharacter.setParamMap(dto.getParamMap());
        return gameCharacter;
    }
}
