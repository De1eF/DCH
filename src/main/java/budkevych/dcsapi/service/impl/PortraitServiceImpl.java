package budkevych.dcsapi.service.impl;

import budkevych.dcsapi.exception.ResourceNotFoundException;
import budkevych.dcsapi.model.Portrait;
import budkevych.dcsapi.repository.PortraitRepository;
import budkevych.dcsapi.service.PortraitService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PortraitServiceImpl implements PortraitService {
    private final PortraitRepository portraitRepository;

    @Override
    public Portrait getPortraitById(Long id) {
        return portraitRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("portrait not found"));
    }

    @Override
    public Portrait addPortrait(Portrait portrait) {
        return portraitRepository.save(portrait);
    }

    @Override
    public void updatePortrait(Long id, Portrait portrait) {
        getPortraitById(id);
        portrait.setId(id);
        portraitRepository.save(portrait);
    }

    @Override
    public void deletePortrait(Long id) {
        portraitRepository.delete(getPortraitById(id));
    }
}
