package com.tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;

import com.tss.components.SessionComponent;
import org.springframework.core.SpringVersion;

@Controller
public class MainController {
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
    public String showUserList(Model model) {
                // Odczyt danych z buildProperties .. aktualnie go nie ma bo nie skonfigurowano pom.xml, ale bedzie pojawiac sie w webpages/META-INF/
        String artifactApp = buildProperties.getArtifact();
        String versionApp = buildProperties.getVersion();
        
        // Odczyt wersji spring przez klase SpringVersion!
        String springVersion = SpringVersion.getVersion();
        model.addAttribute("springVersion", springVersion);
        
        // Parametry z application.properties .. wstrzykniete wczesniej za pomoca @Value
        model.addAttribute("jdkVersion", myjdkversion);
        model.addAttribute("springBootVersion", springbootversion);
        model.addAttribute("applicationName", applicationName);
        model.addAttribute("buildVersion", buildVersion);
        model.addAttribute("buildTimestamp", buildTimestamp);
        
        sessionComponent.increaseCounter();
        model.addAttribute("counterValue", sessionComponent.getCounter());
        return "index.html";
    }
}
