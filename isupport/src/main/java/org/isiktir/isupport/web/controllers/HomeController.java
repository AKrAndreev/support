package org.isiktir.isupport.web.controllers;

import org.isiktir.isupport.domain.models.view.CategoryViewModel;
import org.isiktir.isupport.domain.models.view.CauseViewModel;
import org.isiktir.isupport.service.CauseIndividualService;
import org.isiktir.isupport.service.CauseTeamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {

    private static final String UNIT_INDIVIDUAL = "individual";
    private static final String UNIT_TEAM = "team";

   private final CauseIndividualService causeIndividualService;
   private final CauseTeamService causeTeamService;
   private final ModelMapper mapper;

   @Autowired
    public HomeController(CauseIndividualService causeIndividualService,
                          CauseTeamService causeTeamService, ModelMapper mapper) {
        this.causeIndividualService = causeIndividualService;
        this.causeTeamService = causeTeamService;
        this.mapper = mapper;
    }


    @GetMapping("/")
    public ModelAndView home(){

        return super.view("index");
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home(ModelAndView modelAndView) {

        List<CauseViewModel> individual = causeIndividualService.findForHome()
                .stream()
                .map(cai->  mapper.map(cai,CauseViewModel.class))
                .collect(Collectors.toList());

        individual.forEach(a->a.setUnit(UNIT_INDIVIDUAL));

        List<CauseViewModel> team = causeTeamService.findForHome()
                .stream()
                .map(cat->mapper.map(cat,CauseViewModel.class))
                .collect(Collectors.toList());
        team.forEach(t->t.setUnit(UNIT_TEAM));

        List<CauseViewModel> causes = new ArrayList<>(individual);

         causes.addAll(team);

        modelAndView.addObject("causes", causes);
        modelAndView.addObject("teams", team);
        modelAndView.addObject("individuals", individual);


        return super.view("home", modelAndView);
    }
}
