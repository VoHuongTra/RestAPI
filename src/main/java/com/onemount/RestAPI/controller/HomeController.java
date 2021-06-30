package com.onemount.RestAPI.controller;

import com.onemount.RestAPI.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private PersonRepository personRepo;
    @GetMapping("/")
    public String showHome() {
        return "index";
    }
}
