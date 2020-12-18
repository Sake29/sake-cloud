package com.sake.sakecloud.controller;

import com.sake.sakecloud.entity.User;
import com.sake.sakecloud.service.UserService;
import com.sake.sakecloud.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户控制器
 * 用于用户登录和注册
 *
 * @author WSY
 * @date 2020/12/14
 */
@RestController
@RequestMapping("/")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;



    @PostMapping("/login")
    public ModelAndView login(
            @RequestParam("username") String userName,
            @RequestParam("password")String password,
            HttpServletRequest request
    ){
        ModelAndView view = new ModelAndView();
        User user = userService.getUserBy(userName);
        if (user == null){
            log.info("用户："+userName+"未注册！");
            view.addObject("error","用户未注册");
            view.setViewName("login");
            return view;
        }
        else {
            if(! user.getPassword().equals(password)){
                log.info("密码错误");
                view.addObject("error","密码错误！");
                view.setViewName("login");
                return view;
            }
        }

        request.getSession().setAttribute("userName",userName);
        view.addObject("userName",userName);
        view.setViewName("index");
        return view;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request){
        request.getSession().removeAttribute("userName");
        ModelAndView view = new ModelAndView();
        view.setViewName("login");
        return view;
    }

    @PostMapping("/register")
    public ModelAndView register(@RequestParam("username") String userName,
                                 @RequestParam("password")String password,
                                 @RequestParam("repassword")String rePassword,
                                 HttpServletRequest request
                                 ){
        ModelAndView view = new ModelAndView();
        if (!password.equals(rePassword)){
            log.info("两次密码不一致！");
            view.setViewName("/register");
            return view;
        }
        User user = userService.getUserBy(userName);
        if (user != null){
            log.info("用户已存在！");
            view.setViewName("/login");
            return view;
        }
        //插入新用户
        user = new User();
        user.setUsername(userName);
        user.setPassword(password);
        userService.registerNewUser(user);
        log.info("用户:"+ userName +"注册成功！");
        request.getSession().setAttribute("userName",userName);
        view.addObject("userName",userName);
        view.setViewName("index");
        return view;
    }
}
