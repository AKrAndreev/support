package org.isiktir.isupport.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BaseController {
    protected ModelAndView view (String view, ModelAndView modelAndView){
        modelAndView.setViewName(view);
        return modelAndView;
    }


    protected ModelAndView view(String view){
        return this.view(view, new ModelAndView());
    }

    protected ModelAndView redirect(String url){
        return this.view("redirect:"+url);
    }
}
