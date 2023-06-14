package budkevych.squareapi.dto.mapper;

import budkevych.squareapi.dto.request.GameCharacterRequestDto;
import budkevych.squareapi.dto.response.GameCharacterResponseDto;
import budkevych.squareapi.model.GameCharacter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import org.springframework.stereotype.Component;

@Component
public class GameCharacterMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public GameCharacterResponseDto toDto(GameCharacter character) {
        GameCharacterResponseDto dto = new GameCharacterResponseDto();
        dto.setId(character.getId());
        dto.setTimestamp(character.getLastUpdate());
        dto.setUserId(character.getUserId());
        dto.setName(character.getName());
        try {
            dto.setParamMap(objectMapper.readValue(character.getParamMap(), HashMap.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to parse paramMap to json ", e);
        }
        return dto;
    }

    public GameCharacter toModel(GameCharacterRequestDto dto) {
        GameCharacter gameCharacter = new GameCharacter();
        gameCharacter.setName(dto.getName());
        gameCharacter.setIsDeleted((short) 0);
        try {
            gameCharacter.setParamMap(objectMapper.writeValueAsString(dto.getParamMap()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to parse paramMap from json ", e);
        }
        return gameCharacter;
    }
}
