package com.example.alijavapta.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController()
@RequestMapping(path = "/shop/")
public class ShopController {
    @GetMapping(value = "index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("index");
        Subject currUser = SecurityUtils.getSubject();
//        mv.addObject("currUser", currUser.getPrincipals());
        return new ModelAndView("shop/index");
    }
}
