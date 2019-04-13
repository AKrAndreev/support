package org.isiktir.isupport.service;

import org.isiktir.isupport.domain.entities.CauseIndividual;
import org.isiktir.isupport.domain.entities.Status;
import org.isiktir.isupport.domain.entities.User;
import org.isiktir.isupport.domain.models.service.CauseIndividualServiceModel;
import org.isiktir.isupport.repositories.CauseIndividualRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CauseIndividualServiceImpl implements CauseIndividualService {

    private final ModelMapper mapper;
    private final CauseIndividualRepository causeIndividualRepository;
    private final UserService userService;

    @Autowired
    public CauseIndividualServiceImpl(ModelMapper mapper, CauseIndividualRepository causeIndividualRepository, UserService userService) {
        this.mapper = mapper;
        this.causeIndividualRepository = causeIndividualRepository;
        this.userService = userService;
    }


    @Override
    public CauseIndividualServiceModel create(CauseIndividualServiceModel serviceModel) {
        CauseIndividual causeIndividual = mapper.map(serviceModel,CauseIndividual.class);
        causeIndividual.setUser(mapper.map(userService.findByName(serviceModel.getUser()), User.class));
        causeIndividual.setStatus(Status.UNDER_REVIEW);
        return mapper.map(causeIndividualRepository.save(causeIndividual),CauseIndividualServiceModel.class);
    }
}
