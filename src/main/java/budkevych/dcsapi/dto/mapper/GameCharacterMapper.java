package budkevych.dcsapi.dto.mapper;

import budkevych.dcsapi.dto.request.GameCharacterRequestDto;
import budkevych.dcsapi.dto.response.GameCharacterResponseDto;
import budkevych.dcsapi.model.GameCharacter;
import budkevych.dcsapi.model.ParamMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameCharacterMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final UserMapper userMapper;

    public GameCharacterResponseDto toDto(GameCharacter character) {
        GameCharacterResponseDto dto = new GameCharacterResponseDto();
        dto.setId(character.getId());
        dto.setTimestamp(character.getLastUpdate());
        dto.setOwners(character.getOwners().stream().map(userMapper::mapToDto).toList());
        dto.setName(character.getName());
        try {
            dto.setData(objectMapper.readValue(
                    character.getData(),
                    HashMap.class));
            dto.setParamMap(objectMapper.readValue(
                    character.getParamMap().getData(),
                    HashMap.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to parse paramMap or data to json ", e);
        }
        return dto;
    }

    public GameCharacter toModel(GameCharacterRequestDto dto) {
        GameCharacter gameCharacter = new GameCharacter();
        gameCharacter.setName(dto.getName());
        gameCharacter.setIsDeleted((short) 0);
        gameCharacter.setPortraitId(dto.getPortraitId());
        gameCharacter.setOwners(new HashSet<>());
        try {
            gameCharacter.setData(objectMapper.writeValueAsString(dto.getData()));
            ParamMap paramMap = new ParamMap();
            paramMap.setData(objectMapper.writeValueAsString(dto.getParamMap()));
            gameCharacter.setParamMap(paramMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to parse paramMap from json ", e);
        }
        return gameCharacter;
    }
}
