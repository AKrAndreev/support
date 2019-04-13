package org.isiktir.isupport.service;

import org.isiktir.isupport.domain.models.service.CauseTeamServiceModel;

import java.math.BigDecimal;
import java.util.List;

public interface CauseTeamService {

    CauseTeamServiceModel create(CauseTeamServiceModel causeTeamServiceModel);
    List<CauseTeamServiceModel> findForHome();
    CauseTeamServiceModel findById(String id);
    CauseTeamServiceModel donate(String id, BigDecimal money);
}
