package com.sake.sakecloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 视图控制器
 * 用于控制视图的跳转
 *
 * @author WSY
 * @date 2020/12/16
 */
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
