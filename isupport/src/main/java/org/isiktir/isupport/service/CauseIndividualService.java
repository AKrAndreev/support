package org.isiktir.isupport.service;

import org.isiktir.isupport.domain.models.service.CauseIndividualServiceModel;

import java.math.BigDecimal;
import java.util.List;

public interface CauseIndividualService {

CauseIndividualServiceModel create(CauseIndividualServiceModel serviceModel);
    List<CauseIndividualServiceModel> findForHome();
    CauseIndividualServiceModel findById(String id);
    CauseIndividualServiceModel donate(String id, BigDecimal money);
}
