package com.sake.sakecloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {
    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView view = new ModelAndView();
        view.setViewName("login");
        return view;
    }

    @GetMapping("/register")
    public ModelAndView register(){
        ModelAndView view = new ModelAndView();
        view.setViewName("register");
        return view;
    }

    @GetMapping("/index")
    public ModelAndView index(){
        ModelAndView view = new ModelAndView();
        view.setViewName("index");
        return view;
    }
}
