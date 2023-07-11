package budkevych.dcsapi.service;

import budkevych.dcsapi.model.Portrait;

public interface PortraitService {
    Portrait getPortraitById(Long id);

    void addPortrait(Portrait portrait);

    void updatePortrait(Long id, Portrait portrait);

    void deletePortrait(Long id);
}
