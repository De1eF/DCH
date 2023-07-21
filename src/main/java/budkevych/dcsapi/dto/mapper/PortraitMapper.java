package budkevych.dcsapi.dto.mapper;

import budkevych.dcsapi.dto.request.PortraitRequestDto;
import budkevych.dcsapi.dto.response.PortraitResponseDto;
import budkevych.dcsapi.model.Portrait;
import org.springframework.stereotype.Component;

@Component
public class PortraitMapper {
    public Portrait toModel(PortraitRequestDto portraitRequestDto) {
        Portrait portrait = new Portrait();
        portrait.setData(portraitRequestDto.getData());
        return portrait;
    }

    public PortraitResponseDto toDto(Portrait portrait) {
        PortraitResponseDto dto = new PortraitResponseDto();
        dto.setId(portrait.getId());
        dto.setData(portrait.getData());
        return dto;
    }
}
