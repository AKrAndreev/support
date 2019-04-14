package org.isiktir.isupport.service;

import org.isiktir.isupport.domain.entities.CauseTeam;
import org.isiktir.isupport.domain.entities.Status;
import org.isiktir.isupport.domain.entities.User;
import org.isiktir.isupport.domain.models.service.CauseTeamServiceModel;
import org.isiktir.isupport.domain.models.service.UserServiceModel;
import org.isiktir.isupport.repositories.CauseTeamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CauseTeamServiceImpl implements CauseTeamService {

    private final ModelMapper mapper;
    private final CauseTeamRepository repository;
    private final UserService userService;

    @Autowired
    public CauseTeamServiceImpl(ModelMapper mapper, CauseTeamRepository repository, UserService userService) {
        this.mapper = mapper;
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public CauseTeamServiceModel create(CauseTeamServiceModel causeTeamServiceModel) {

        CauseTeam causeTeam = mapper.map(causeTeamServiceModel,CauseTeam.class);
        UserServiceModel userServiceModel = userService.findByName(causeTeamServiceModel.getUser());
        User user = mapper.map(userServiceModel,User.class);
        causeTeam.setUser(user);
        causeTeam.setStatus(Status.UNDER_REVIEW);
        return mapper.map(repository.save(causeTeam),CauseTeamServiceModel.class);
    }

    @Override
    public List<CauseTeamServiceModel> findForHome() {
        return repository.findAll()
                .stream()
                .filter(c->c.getStatus().getValue().equalsIgnoreCase("approved"))
                .sorted(Comparator.comparing(CauseTeam::getNeededMoney))
                .limit(3)
                .map(ct->mapper.map(ct,CauseTeamServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CauseTeamServiceModel findById(String id) {

        CauseTeam causeTeam = repository.findById(id).orElse(null);
        //TODO
        return mapper.map(causeTeam,CauseTeamServiceModel.class);
    }

    @Override
    public CauseTeamServiceModel donate(String id, BigDecimal money) {
        //TODO
        CauseTeam causeTeam = repository.findById(id).orElse(null);
        BigDecimal collectedMoney  = causeTeam.getCollectedMoney();

        if (collectedMoney == null){
            collectedMoney=BigDecimal.ZERO;
        }
        BigDecimal updatedMoney = collectedMoney.add(money);
        causeTeam.setCollectedMoney(updatedMoney);

        if (causeTeam.getCollectedMoney().compareTo(causeTeam.getNeededMoney())>=0){
            causeTeam.setStatus(Status.FINISHED);
        }
        return mapper.map(repository.save(causeTeam),CauseTeamServiceModel.class);
    }

    @Override
    public List<CauseTeamServiceModel> findForReview() {
        return repository.findAll().stream()
                .filter(c->c.getStatus().getValue().equalsIgnoreCase(Status.UNDER_REVIEW.getValue()))
                .map(c->mapper.map(c,CauseTeamServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CauseTeamServiceModel updateStatus(String id,String status) {
        CauseTeam causeTeam = repository.findById(id).orElse(null);
        causeTeam.setStatus(Status.valueOf(status));

        return mapper.map(repository.save(causeTeam),CauseTeamServiceModel.class);
    }

    @Override
    public List<CauseTeamServiceModel> findAll() {
        return repository.findAll()
                .stream()
                .map(c->mapper.map(c,CauseTeamServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CauseTeamServiceModel edit(CauseTeamServiceModel causeTeamServiceModel) {
        CauseTeam team = mapper.map(causeTeamServiceModel,CauseTeam.class);
        CauseTeam teamFroUpdate = repository.getOne(team.getId());

        team.setUser(teamFroUpdate.getUser());
        if (team.getCollectedMoney() == null){
            team.setCollectedMoney(BigDecimal.ZERO);
        }
        if (team.getImgUrl() == null || team.getImgUrl().equalsIgnoreCase("")){
            team.setImgUrl(teamFroUpdate.getImgUrl());
        }
        team.setStatus(Status.UNDER_REVIEW);
        CauseTeam updatedTeam =  repository.saveAndFlush(team);

        return mapper.map(updatedTeam,CauseTeamServiceModel.class);
    }
}
