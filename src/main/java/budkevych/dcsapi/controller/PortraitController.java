package budkevych.dcsapi.controller;

import budkevych.dcsapi.dto.mapper.PortraitMapper;
import budkevych.dcsapi.dto.request.PortraitRequestDto;
import budkevych.dcsapi.dto.response.ActionResponseDto;
import budkevych.dcsapi.dto.response.PortraitResponseDto;
import budkevych.dcsapi.model.Portrait;
import budkevych.dcsapi.service.PortraitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portraits")
@CrossOrigin
@RequiredArgsConstructor
public class PortraitController {
    private final PortraitService portraitService;
    private final PortraitMapper portraitMapper;

    @GetMapping("/{id}")
    public PortraitResponseDto getPortrait(@PathVariable Long id) {
        return portraitMapper.toDto(portraitService.getPortraitById(id));
    }

    @PostMapping
    public PortraitResponseDto addPortrait(@RequestBody PortraitRequestDto portraitRequestDto) {
        Portrait portrait = portraitService.addPortrait(portraitMapper.toModel(portraitRequestDto));
        portrait.setData("");
        return portraitMapper.toDto(portrait);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePortrait(@PathVariable Long id) {
        portraitService.deletePortrait(id);
        return ResponseEntity
                .ok(ActionResponseDto.builder().message("Portrait deleted").build());
    }
}
