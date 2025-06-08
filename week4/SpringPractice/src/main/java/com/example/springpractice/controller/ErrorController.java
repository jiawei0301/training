package com.example.springpractice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Deprecated
public class ErrorController {
    @RequestMapping("/404")
    public String error404() {
        return "404";
    }
}
