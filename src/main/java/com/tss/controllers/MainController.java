package com.tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tss.components.SessionComponent;

@Controller
public class MainController {
    @Autowired
    SessionComponent sessionComponent;

    @RequestMapping("/")
    public String showUserList(Model model) {
        sessionComponent.increaseCounter();
        model.addAttribute("counterValue", sessionComponent.getCounter());
        return "index.html";
    }
}
