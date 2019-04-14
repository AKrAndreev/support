package org.isiktir.isupport.service;

import org.isiktir.isupport.domain.entities.CauseIndividual;
import org.isiktir.isupport.domain.entities.Status;
import org.isiktir.isupport.domain.entities.User;
import org.isiktir.isupport.domain.models.service.CauseIndividualServiceModel;
import org.isiktir.isupport.repositories.CauseIndividualRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<CauseIndividualServiceModel> findForHome() {
        return causeIndividualRepository.findAll()
                .stream()
                .filter(c->c.getStatus().getValue().equalsIgnoreCase("approved"))
                .sorted(Comparator.comparing(CauseIndividual::getNeededMoney))
                .limit(3)
                .map(ci->mapper.map(ci,CauseIndividualServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CauseIndividualServiceModel findById(String id) {
        CauseIndividual causeIndividual = causeIndividualRepository.findById(id)
                .orElse(null);

        //TODO exception handling
        return mapper.map(causeIndividual,CauseIndividualServiceModel.class);
    }

    @Override
    public CauseIndividualServiceModel donate(String id, BigDecimal money) {
        CauseIndividual causeIndividual = causeIndividualRepository.findById(id).orElse(null);
        //TODO
        BigDecimal neededMoney = causeIndividual.getCollectedMoney();
        if (neededMoney == null){
            neededMoney = BigDecimal.ZERO;
        }
        BigDecimal updated = neededMoney.add(money);
        causeIndividual.setCollectedMoney(updated);
        if (causeIndividual.getNeededMoney().compareTo(causeIndividual.getCollectedMoney())>=0){
            causeIndividual.setStatus(Status.FINISHED);
        }
        return mapper.map(causeIndividualRepository.save(causeIndividual),CauseIndividualServiceModel.class);
    }

    @Override
    public List<CauseIndividualServiceModel> findForReview() {
        return causeIndividualRepository
                .findAll()
                .stream()
                .filter(c->c.getStatus().equals(Status.UNDER_REVIEW))
                .map(c->mapper.map(c,CauseIndividualServiceModel.class))
                .collect(Collectors.toList());
    }


    @Override
    public CauseIndividualServiceModel updateStatus(String id, String status) {

        CauseIndividual causeIndividual = causeIndividualRepository.findById(id).orElse(null);
        causeIndividual.setStatus(Status.valueOf(status));
        return mapper.map(causeIndividualRepository.save(causeIndividual),CauseIndividualServiceModel.class);
    }

    @Override
    public List<CauseIndividualServiceModel> findAll() {
        return causeIndividualRepository.findAll()
                .stream()
                .map(c->mapper.map(c,CauseIndividualServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CauseIndividualServiceModel edit(CauseIndividualServiceModel serviceModel) {
        CauseIndividual individual = mapper.map(serviceModel,CauseIndividual.class);
        CauseIndividual forUpdate = causeIndividualRepository.findById(individual.getId()).orElse(null);

        if (forUpdate.getCollectedMoney()==null){
            forUpdate.setCollectedMoney(BigDecimal.ZERO);
        }
        individual.setCollectedMoney(forUpdate.getCollectedMoney());
        if (individual.getImgUrl()==null || individual.getImgUrl().equalsIgnoreCase("") ){
            individual.setImgUrl(forUpdate.getImgUrl());
        }

        individual.setUser(forUpdate.getUser());
        individual.setStatus(Status.UNDER_REVIEW);
        CauseIndividual updated = causeIndividualRepository.save(individual);

        return mapper.map(updated,CauseIndividualServiceModel.class);
    }
}
