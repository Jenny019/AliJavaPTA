package com.example.alijavapta;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@MapperScan(basePackages= {"com.example.alijavapta.mapper"})
public class AliJavaPtaApplication {
    public static void main(String[] args) {
        SpringApplication.run(AliJavaPtaApplication.class, args);
    }
}
