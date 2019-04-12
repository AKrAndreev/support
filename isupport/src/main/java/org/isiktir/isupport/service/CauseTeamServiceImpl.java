package org.isiktir.isupport.service;

import org.isiktir.isupport.domain.entities.CauseTeam;
import org.isiktir.isupport.domain.models.service.CauseTeamServiceModel;
import org.isiktir.isupport.repositories.CauseTeamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CauseTeamServiceImpl implements CauseTeamService {

    private final ModelMapper mapper;
    private final CauseTeamRepository repository;

    @Autowired
    public CauseTeamServiceImpl(ModelMapper mapper, CauseTeamRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public CauseTeamServiceModel create(CauseTeamServiceModel causeTeamServiceModel) {

        CauseTeam causeTeam = mapper.map(causeTeamServiceModel,CauseTeam.class);
        return mapper.map(repository.save(causeTeam),CauseTeamServiceModel.class);
    }
}
