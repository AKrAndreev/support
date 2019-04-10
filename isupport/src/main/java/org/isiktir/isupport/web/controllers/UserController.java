package org.isiktir.isupport.web.controllers;

import org.isiktir.isupport.domain.models.binding.UserBindingModel;
import org.isiktir.isupport.domain.models.service.UserServiceModel;
import org.isiktir.isupport.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController extends BaseController {

    private final UserService service;
    private final ModelMapper mapper;

    @Autowired
    public UserController(UserService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }


    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(){
        return super.view("register");

    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(@ModelAttribute UserBindingModel model) {
        if (!model.getPassword().equals(model.getConfirmPassword())) {
            return super.view("register");
        }

        this.service.register(this.mapper.map(model, UserServiceModel.class));

        return super.redirect("/login");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login() {
        return super.view("login");
    }
}
