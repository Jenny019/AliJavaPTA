package com.example.alijavapta.controller;

import com.example.alijavapta.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ThymeleafController {
    @RequestMapping("/")
    public ModelAndView index() {
        // 返回数据
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        List<User> userList = new ArrayList<>();
        userList.add(new User("张三", "12345"));
        userList.add(new User("李四", "12345"));
        userList.add(new User("王五", "12345"));
        userList.add(new User("赵六", "12345"));
        mv.addObject("users", userList);
        // 指定返回模板
        return mv;
    }
}
