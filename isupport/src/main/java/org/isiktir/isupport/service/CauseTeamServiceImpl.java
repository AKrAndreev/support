package org.isiktir.isupport.service;

import org.isiktir.isupport.domain.entities.CauseTeam;
import org.isiktir.isupport.domain.entities.Status;
import org.isiktir.isupport.domain.entities.User;
import org.isiktir.isupport.domain.models.service.CauseTeamServiceModel;
import org.isiktir.isupport.repositories.CauseTeamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        causeTeam.setUser(mapper.map(userService.findByName(causeTeamServiceModel.getUser()), User.class));
        causeTeam.setStatus(Status.UNDER_REVIEW);
        return mapper.map(repository.save(causeTeam),CauseTeamServiceModel.class);
    }
}
