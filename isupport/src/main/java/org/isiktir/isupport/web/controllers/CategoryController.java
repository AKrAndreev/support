package org.isiktir.isupport.web.controllers;

import org.isiktir.isupport.domain.models.binding.CategoryBindingModel;
import org.isiktir.isupport.domain.models.service.CategoryServiceModel;
import org.isiktir.isupport.domain.models.view.CategoryViewModel;
import org.isiktir.isupport.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController {

    private final ModelMapper mapper;
    private final CategoryService service;

    @Autowired
    public CategoryController(ModelMapper mapper, CategoryService service) {
        this.mapper = mapper;
        this.service = service;
    }


    @GetMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_MODERATOR')")
    public ModelAndView create(ModelAndView modelAndView,
                               @ModelAttribute(name = "bindingModel")CategoryBindingModel categoryBindingModel) {
        modelAndView.addObject("bindingModel",categoryBindingModel);

        List<CategoryViewModel> categoryViewModels = service.findAll()
                .stream()
                .filter(c-> c.isHasChild() && c.getPid()==null)
                .map(categoryServiceModel->mapper.map(categoryServiceModel,CategoryViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("categories",categoryViewModels);
        return super.view("create-category",modelAndView);
    }


    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addCategoryConfirm(@Valid @ModelAttribute(name = "bindingModel") CategoryBindingModel model,
                                           BindingResult bindingResult,
                                           ModelAndView modelAndView) {

        if (bindingResult.hasErrors()){
            modelAndView.addObject("bindingModel",bindingResult);
            return super.view("create-category",modelAndView);
        }

        if (model.getSubcategodires().size()!=0){
            model.getSubcategodires().forEach(c ->{
                if (c.getSubcategodires().size()==0){
                    c.setHasChild(false);
                }
            });
            model.setHasChild(true);
        }else {
            model.setHasChild(true);
        }


        service.createCategory(mapper.map(model, CategoryServiceModel.class));

        return super.redirect("/");
    }
}