package com.example.case4.controller;

import com.example.case4.service.customer.ICustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomeController {
    @GetMapping("")
    public String index(Model model) {
        return "index";
    }
}
