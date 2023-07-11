package budkevych.dcsapi.controller;

import budkevych.dcsapi.dto.mapper.PortraitMapper;
import budkevych.dcsapi.dto.request.PortraitRequestDto;
import budkevych.dcsapi.dto.response.PortraitResponseDto;
import budkevych.dcsapi.service.PortraitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public void addPortrait(@RequestBody PortraitRequestDto portraitRequestDto) {
        portraitService.addPortrait(portraitMapper.toModel(portraitRequestDto));
    }

    @PutMapping("/{id}")
    public void updatePortrait(@PathVariable Long id,
            @RequestBody PortraitRequestDto portraitRequestDto) {
        portraitService.updatePortrait(id, portraitMapper.toModel(portraitRequestDto));
    }

    @DeleteMapping("/{id}")
    public void deletePortrait(@PathVariable Long id) {
        portraitService.deletePortrait(id);
    }
}
