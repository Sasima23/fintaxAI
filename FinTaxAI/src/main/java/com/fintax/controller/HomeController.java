package com.fintax.controller;

import com.fintax.service.TaxCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @Autowired
    private TaxCalculationService taxCalculationService;
    
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @GetMapping("/calculator")
    public String calculator() {
        return "calculator";
    }
    
    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("calculations", taxCalculationService.getAllCalculations());
        return "history";
    }
    
    @GetMapping("/chatbot")
    public String chatbot() {
        return "chatbot";
    }
}
