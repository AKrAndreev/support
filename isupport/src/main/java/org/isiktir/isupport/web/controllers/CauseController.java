package org.isiktir.isupport.web.controllers;

import org.isiktir.isupport.domain.entities.Category;
import org.isiktir.isupport.domain.entities.Level;
import org.isiktir.isupport.domain.models.binding.CauseIndBindingModel;
import org.isiktir.isupport.domain.models.binding.CauseLevelBindingModel;
import org.isiktir.isupport.domain.models.binding.CauseTeamBindingModel;
import org.isiktir.isupport.domain.models.service.CauseIndividualServiceModel;
import org.isiktir.isupport.domain.models.service.CauseTeamServiceModel;
import org.isiktir.isupport.domain.models.view.CategoryViewModel;
import org.isiktir.isupport.service.CauseIndividualService;
import org.isiktir.isupport.service.CauseTeamService;
import org.isiktir.isupport.service.CloudinaryService;
import org.isiktir.isupport.web.utils.CategoryFinder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/causes")
public class CauseController extends BaseController {


    private final CategoryFinder finder;
    private final CloudinaryService cloudinaryService;
    private final CauseTeamService service;
    private final ModelMapper mapper;
    private final CauseIndividualService causeIndividualService;


    @Autowired
    public CauseController(
            CategoryFinder finder,
            CloudinaryService cloudinaryService,
            CauseTeamService service,
            ModelMapper mapper,
            CauseIndividualService causeIndividualService)
    {

        this.finder = finder;
        this.cloudinaryService = cloudinaryService;
        this.service = service;
        this.mapper = mapper;
        this.causeIndividualService = causeIndividualService;
    }


    @GetMapping("/create-level")
    @PreAuthorize("hasRole('ROLE_SUPPORTED')")
    public ModelAndView defineLevel(ModelAndView modelAndView,
                                   @ModelAttribute("bindingModel") CauseLevelBindingModel bindingModel){
        modelAndView.addObject("bindingModel",bindingModel);
        return super.view("create-cause-level",modelAndView);
    }

    @PostMapping("/create-level")
    @PreAuthorize("hasRole('ROLE_SUPPORTED')")
    public ModelAndView defineLevelConfirmed(ModelAndView modelAndView,
                                             @ModelAttribute CauseLevelBindingModel model){

        if (model.getLevel().equalsIgnoreCase(Level.AMATEUR.getValue())){
        return     super.redirect("/causes/create-amateur");
        }else {
         return    super.redirect("/causes/create-pro");
        }
    }

    @GetMapping("/create-pro")
    @PreAuthorize("hasRole('ROLE_SUPPORTED')")
    public ModelAndView createTeamPro(ModelAndView modelAndView,
                                   @ModelAttribute("bindingModel")CauseTeamBindingModel bindingModel){

        modelAndView.addObject("bindingModel",bindingModel);
        return super.view("create-cause-pro",modelAndView);
    }

    @PostMapping("/create-team-pro")
    public ModelAndView addTeamCause(@ModelAttribute CauseTeamBindingModel model) throws IOException {

        CauseTeamServiceModel causeTeamServiceModel = mapper.map(model,CauseTeamServiceModel.class);
        causeTeamServiceModel.setLevel(Level.PROFESSIONAL);
        causeTeamServiceModel.setCategory(selectCategory(Level.PROFESSIONAL.getValue(),model.getCategory()));
        causeTeamServiceModel.setImgUrl(cloudinaryService.uploadImage(model.getImage()));
        service.create(causeTeamServiceModel);

        return super.redirect("/");
    }




    @GetMapping("/create-amateur")
    @PreAuthorize("hasRole('ROLE_SUPPORTED')")
    public ModelAndView createTeamAmateur(ModelAndView modelAndView,
                                   @ModelAttribute("bindingModel")CauseTeamBindingModel bindingModel){

        modelAndView.addObject("bindingModel",bindingModel);
        return super.view("create-cause-am",modelAndView);
    }

    @PostMapping("/create-team-am")
    public ModelAndView addTeamCauseAm(@ModelAttribute CauseTeamBindingModel model) throws IOException {

        CauseTeamServiceModel causeTeamServiceModel = mapper.map(model,CauseTeamServiceModel.class);
        causeTeamServiceModel.setLevel(Level.AMATEUR);
        causeTeamServiceModel.setCategory(selectCategory(Level.AMATEUR.getValue(),model.getCategory()));
        causeTeamServiceModel.setImgUrl(cloudinaryService.uploadImage(model.getImage()));
        service.create(causeTeamServiceModel);

        return super.redirect("/");
    }


    @GetMapping("/create-level-ind")
    @PreAuthorize("hasRole('ROLE_SUPPORTED')")
    public ModelAndView defineLevelInd(ModelAndView modelAndView,
                                    @ModelAttribute("bindingModel") CauseLevelBindingModel bindingModel){
        modelAndView.addObject("bindingModel",bindingModel);
        return super.view("create-cause-level-ind",modelAndView);
    }

    @PostMapping("/create-level-ind")
    @PreAuthorize("hasRole('ROLE_SUPPORTED')")
    public ModelAndView defineLevelIndConfirmed(ModelAndView modelAndView,
                                             @ModelAttribute CauseLevelBindingModel model){
        if (model.getLevel().equalsIgnoreCase(Level.AMATEUR.getValue())){
            return     super.redirect("/causes/create-cause-am-ind");
        }else {
            return    super.redirect("/causes/create-cause-pro-ind");
        }
    }

    @GetMapping("/create-cause-pro-ind")
    @PreAuthorize("hasRole('ROLE_SUPPORTED')")
    public ModelAndView createProInd(ModelAndView modelAndView,
                                      @ModelAttribute("bindingModel") CauseIndBindingModel bindingModel){

        modelAndView.addObject("bindingModel",bindingModel);
        return super.view("create-cause-pro-ind",modelAndView);
    }


    @PostMapping("/create-cause-pro-ind")
    public ModelAndView addProCauseInd(@ModelAttribute CauseIndBindingModel model, Principal principal) throws IOException {

        CauseIndividualServiceModel serviceModel = mapper.map(model,CauseIndividualServiceModel.class);
        serviceModel.setLevel(Level.PROFESSIONAL);
        serviceModel.setCategory(selectCategory(Level.PROFESSIONAL.getValue(),model.getCategory()));
        serviceModel.setImgUrl(cloudinaryService.uploadImage(model.getImage()));
        serviceModel.setUser(principal.getName());
         causeIndividualService.create(serviceModel);

        return super.redirect("/");
    }


    @GetMapping("/create-cause-am-ind")
    @PreAuthorize("hasRole('ROLE_SUPPORTED')")
    public ModelAndView createAmInd(ModelAndView modelAndView,
                                         @ModelAttribute("bindingModel") CauseIndBindingModel bindingModel){

        modelAndView.addObject("bindingModel",bindingModel);
        return super.view("create-cause-am-ind",modelAndView);
    }


    @PostMapping("/create-cause-am-ind")
    public ModelAndView addICauseIndAm(@ModelAttribute CauseIndBindingModel model, Principal principal) throws IOException {

        CauseIndividualServiceModel serviceModel = mapper.map(model,CauseIndividualServiceModel.class);
        serviceModel.setLevel(Level.AMATEUR);
        serviceModel.setCategory(selectCategory(Level.AMATEUR.getValue(),model.getCategory()));
        serviceModel.setImgUrl(cloudinaryService.uploadImage(model.getImage()));
        serviceModel.setUser(principal.getName());
        causeIndividualService.create(serviceModel);

        return super.redirect("/");
    }

    @GetMapping("/fetch-pro")
    @ResponseBody
    public List<CategoryViewModel> fetchByCategoryPro( ) {
        return finder.findCategories(Level.PROFESSIONAL.getValue());

    }

    @GetMapping("/fetch-am")
    @ResponseBody
    public List<CategoryViewModel> fetchByCategoryAm( ) {
        return finder.findCategories(Level.AMATEUR.getValue());

    }


    @GetMapping("/fetch-pro-ind")
    @ResponseBody
    public List<CategoryViewModel> fetchByCategoryProInd( ) {
        return finder.findCategories(Level.PROFESSIONAL.getValue());

    }

    @GetMapping("/fetch-am-ind")
    @ResponseBody
    public List<CategoryViewModel> fetchByCategoryAmInd( ) {
        return finder.findCategories(Level.AMATEUR.getValue());

    }

    private Category selectCategory(String lvl, String category) {

        List<CategoryViewModel> allCategoriesInFlow = finder.findCategories(lvl)
                .stream().filter(c->c.getName().equals(category)).collect(Collectors.toList());
        return  mapper.map(allCategoriesInFlow.get(0),Category.class);
    }
}
