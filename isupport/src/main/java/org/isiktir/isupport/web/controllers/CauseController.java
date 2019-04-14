package org.isiktir.isupport.web.controllers;

import org.isiktir.isupport.domain.entities.Category;
import org.isiktir.isupport.domain.entities.Level;
import org.isiktir.isupport.domain.models.binding.*;
import org.isiktir.isupport.domain.models.service.CauseIndividualServiceModel;
import org.isiktir.isupport.domain.models.service.CauseTeamServiceModel;
import org.isiktir.isupport.domain.models.view.*;
import org.isiktir.isupport.service.CauseIndividualService;
import org.isiktir.isupport.service.CauseTeamService;
import org.isiktir.isupport.service.CloudinaryService;
import org.isiktir.isupport.web.utils.CategoryFinder;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/causes")
public class CauseController extends BaseController {


    private final CategoryFinder finder;
    private final CloudinaryService cloudinaryService;
    private final CauseTeamService service;
    private final ModelMapper mapper;
    private final CauseIndividualService causeIndividualService;
    private TypeMap<CauseTeamServiceModel,CauseViewModel> typeMap;
    private TypeMap <CauseIndividualServiceModel,CauseViewModel>typeMap2;

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


    @PostConstruct
    public void  init(){
         typeMap = mapper.createTypeMap(CauseTeamServiceModel.class,CauseViewModel.class)
                .addMapping(c->c.getCategory().getName(),CauseViewModel::setCategory);

         typeMap2 = mapper.createTypeMap(CauseIndividualServiceModel.class,CauseViewModel.class)
                .addMapping(c->c.getCategory().getName(),CauseViewModel::setCategory);
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
    @PreAuthorize("hasRole('ROLE_SUPPORTED')")
    public ModelAndView addTeamCause(@ModelAttribute CauseTeamBindingModel model,Principal principal) throws IOException {

        CauseTeamServiceModel causeTeamServiceModel = mapper.map(model,CauseTeamServiceModel.class);
        causeTeamServiceModel.setLevel(Level.PROFESSIONAL);
        causeTeamServiceModel.setCategory(selectCategory(Level.PROFESSIONAL.getValue(),model.getCategory()));
        causeTeamServiceModel.setImgUrl(cloudinaryService.uploadImage(model.getImage()));
        causeTeamServiceModel.setUser(principal.getName());
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
    public ModelAndView addTeamCauseAm(@ModelAttribute CauseTeamBindingModel model, Principal principal) throws IOException {

        CauseTeamServiceModel causeTeamServiceModel = mapper.map(model,CauseTeamServiceModel.class);
        causeTeamServiceModel.setLevel(Level.AMATEUR);
        causeTeamServiceModel.setCategory(selectCategory(Level.AMATEUR.getValue(),model.getCategory()));
        causeTeamServiceModel.setImgUrl(cloudinaryService.uploadImage(model.getImage()));
        causeTeamServiceModel.setUser(principal.getName());
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



    @GetMapping("/details/individual/{id}")
    public ModelAndView caueDetail(@PathVariable String id, ModelAndView modelAndView,
                                   @ModelAttribute(name = "bindingModel")  CauseStatusBindingModel causeStatusBindingModel){

        CauseIndividualViewModel viewModel = mapper.
                map(causeIndividualService.findById(id),CauseIndividualViewModel.class);
        viewModel.setUnit("individual");
        modelAndView.addObject("individual",viewModel);
        return super.view("cause-details-support",modelAndView);
    }

    @GetMapping("/details/team/{id}")
    public ModelAndView causeDetailTeam(@PathVariable String id,
                                        ModelAndView modelAndView,
                                        @ModelAttribute(name = "bindingModel") CauseStatusBindingModel causeStatusBindingModel){

        CauseTeamViewModel viewModel = mapper.
                map(service.findById(id),CauseTeamViewModel.class);
        viewModel.setUnit("team");
        modelAndView.addObject("team",viewModel);
        return super.view("cause-details-support-team",modelAndView);
    }

    @GetMapping("/support/team/{id}")
    public ModelAndView checkout(@PathVariable String id,
                                 ModelAndView modelAndView,
                                 @ModelAttribute("bindingModel")CheckoutBindingModel bindingModel){

        CheckoutModel checkoutModel = new CheckoutModel(id,"team");
        modelAndView.addObject("model",checkoutModel);
        return super.view("checkout-page",modelAndView);
    }


    @PostMapping("/support/team/{id}")
    public ModelAndView checkoutConfirm(@PathVariable String id,@ModelAttribute CheckoutBindingModel model ) {
        service.donate(id,model.getMoney());
        return super.redirect("/home");
    }

    @GetMapping("/support/individual/{id}")
    public ModelAndView checkoutInd(@PathVariable String id,
                                 ModelAndView modelAndView,
                                 @ModelAttribute("bindingModel")CheckoutBindingModel bindingModel){

        CheckoutModel checkoutModel = new CheckoutModel(id,"team");
        modelAndView.addObject("model",checkoutModel);
        return super.view("checkout-page",modelAndView);
    }


    @PostMapping("/support/individual/{id}")
    public ModelAndView checkoutIndConfirm(@PathVariable String id,@ModelAttribute CheckoutBindingModel model ) {
        service.donate(id,model.getMoney());
        return super.redirect("/home");
    }

    @GetMapping("/update")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView reviewCauses(ModelAndView modelAndView){
       List <CauseViewModel> team = service.findForReview()
                .stream()
                .map(typeMap::map)
                .collect(Collectors.toList());
       team.forEach(c->c.setUnit("team"));
       List<CauseViewModel> individual = causeIndividualService
               .findForReview()
               .stream()
               .map(typeMap2::map)
               .collect(Collectors.toList());

       individual.forEach(c->c.setUnit("individual"));
       modelAndView.addObject("team",team);
       modelAndView.addObject("individual",individual);
       return super.view("update-causes",modelAndView);
    }

    @PostMapping("/update/team/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView updateStatusIndTeam(@PathVariable String id,
                                    @ModelAttribute CauseStatusBindingModel causeStatusBindingModel){
        service.updateStatus(id,causeStatusBindingModel.getStatus());
        return super.redirect("/home");
    }
    @PostMapping("/update/individual/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView updateStatusInd(@PathVariable String id,
                                    @ModelAttribute CauseStatusBindingModel causeStatusBindingModel){
        causeIndividualService.updateStatus(id,causeStatusBindingModel.getStatus());
        return super.redirect("/home");
    }


    @GetMapping("/all-causes")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView allCauses(ModelAndView modelAndView){
        List <CauseViewModel> team = service.findAll()
                .stream()
                .map(typeMap::map)
                .collect(Collectors.toList());
        team.forEach(c->c.setUnit("team"));
        List<CauseViewModel> individual = causeIndividualService
                .findAll()
                .stream()
                .map(typeMap2::map)
                .collect(Collectors.toList());

        individual.forEach(c->c.setUnit("individual"));
        modelAndView.addObject("team",team);
        modelAndView.addObject("individual",individual);
        return super.view("all-causes",modelAndView);
    }


    @GetMapping("/edit/team/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editTeamCause(@PathVariable String id,
                                      ModelAndView modelAndView,
                                      @ModelAttribute(name = "viewModel") CauseTeamViewModel viewModel){

        CauseTeamViewModel causeTeamViewModel = mapper.map(service.findById(id),CauseTeamViewModel.class);

        modelAndView.addObject("model",causeTeamViewModel);
        return super.view("cause-edit-team",modelAndView);

    }


    @PostMapping("/edit/team/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editConfirmAmat(@PathVariable("id") String id,
                                        ModelAndView modelAndView,
                                        @ModelAttribute(name = "bindingModel") CauseTeamBindingModel causeTeamBindingModel,
                                        BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            modelAndView.addObject("bindingModel",causeTeamBindingModel);
            return super.view("cause-edit-team",modelAndView);
        }
        CauseTeamServiceModel serviceModel = mapper.map(causeTeamBindingModel,CauseTeamServiceModel.class);
        serviceModel.setId(id);
        if (causeTeamBindingModel.getLevel().equalsIgnoreCase(Level.AMATEUR.getValue())){
            serviceModel.setCategory(selectCategory(Level.AMATEUR.getValue(),causeTeamBindingModel.getCategory()));
        }else {

            serviceModel.setCategory(selectCategory(Level.PROFESSIONAL.getValue(),causeTeamBindingModel.getCategory()));
        }
        service.edit(serviceModel);
        return super.redirect("/home") ;
    }



    @GetMapping("/edit/individual/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editIndCause(@PathVariable String id,
                                      ModelAndView modelAndView,
                                      @ModelAttribute(name = "viewModel") CauseIndividualViewModel viewModel){

        CauseIndividualViewModel causeIndividualViewModel = mapper
                .map(causeIndividualService.findById(id),CauseIndividualViewModel.class);

        modelAndView.addObject("model",causeIndividualViewModel);
        return super.view("cause-edit-ind",modelAndView);

    }

    @PostMapping("/edit/individual/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editConfirmInd(@PathVariable("id") String id,
                                        ModelAndView modelAndView,
                                        @ModelAttribute(name = "bindingModel") CauseIndBindingModel causeIndBindingModel,
                                        BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            modelAndView.addObject("bindingModel",causeIndBindingModel);
            return super.view("cause-edit-ind",modelAndView);
        }
        CauseIndividualServiceModel serviceModel = mapper.map(causeIndBindingModel,CauseIndividualServiceModel.class);
        serviceModel.setId(id);

        if (causeIndBindingModel.getLevel().equalsIgnoreCase(Level.AMATEUR.getValue())){
            serviceModel.setCategory(selectCategory(Level.AMATEUR.getValue(),causeIndBindingModel.getCategory()));
        }else {

            serviceModel.setCategory(selectCategory(Level.PROFESSIONAL.getValue(),causeIndBindingModel.getCategory()));
        }
        causeIndividualService.edit(serviceModel);
        return super.redirect("/home") ;
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
