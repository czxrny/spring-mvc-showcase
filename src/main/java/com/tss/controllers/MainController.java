package com.tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;

import com.tss.components.SessionComponent;
import org.springframework.core.SpringVersion;

import com.tss.repositories.TaskRepository;

@Controller
public class MainController {
    private final TaskRepository taskRepository;

    @Autowired
    public MainController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    @Autowired
    SessionComponent sessionComponent;
    
    @Autowired
    BuildProperties buildProperties;
    
    @Value("${myparams.jdkversion}")
    String myjdkversion;
    
    @Value("${myparams.springbootversion}")
    String springbootversion;
    
    @Value("${spring.application.name}")
    String applicationName;
    
    @Value("${build.version}")
    String buildVersion;
    
    @Value("${build.timestamp}")
    String buildTimestamp;
    
    @RequestMapping("/")
    public String dashboard(Model model) {   
        return "index.html";
    }
    
    @RequestMapping("/buildinfo")
    public String showBuildInfo(Model model) {
        String artifactApp = buildProperties.getArtifact();
        String versionApp = buildProperties.getVersion();
        
        String springVersion = SpringVersion.getVersion();
        model.addAttribute("springVersion", springVersion);
        
        model.addAttribute("jdkVersion", myjdkversion);
        model.addAttribute("springBootVersion", springbootversion);
        model.addAttribute("applicationName", applicationName);
        model.addAttribute("buildVersion", buildVersion);
        model.addAttribute("buildTimestamp", buildTimestamp);   
        return "buildinfo.html";
    }
        
    @RequestMapping("/userinfo")
    public String showUserInfo(Model model) {
        sessionComponent.increaseCounter();
        model.addAttribute("counterValue", sessionComponent.getCounter());
        return "userinfo.html";
    }
    
    @RequestMapping("/showtasks")
    public String showAllTasks(Model model) {
        model.addAttribute("tasks", taskRepository.findAllByOrderByNameAsc());
        return "showtasks.html";
    }
}
