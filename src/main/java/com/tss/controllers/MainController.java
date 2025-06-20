package com.tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;

import com.tss.components.SessionComponent;
import org.springframework.core.SpringVersion;

import com.tss.services.TaskService;
import com.tss.dtos.TaskDTO;
import com.tss.entities.Task;
import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import java.time.LocalDateTime;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/userinfo")
    public String showUserInfo(Model model, Authentication authentication) {
        sessionComponent.increaseCounter();
        model.addAttribute("counterValue", sessionComponent.getCounter());
        model.addAttribute("username", authentication.getName());
        return "userinfo.html";
    }

    @Autowired
    private TaskService taskService;

    @RequestMapping("/showtasks")
    public String showAllTasks(Model model) {
        List<TaskDTO> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication != null &&
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);
        return "showtasks.html";
    }

    @GetMapping("/showAddTaskForm")
    public String showAddTaskForm(Task task) {
        return "addtaskform.html";
    }

    @PostMapping("/addtask")
    public String addTask(@ModelAttribute Task task, BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.err.println(result.getAllErrors());
            return "addtaskform.html";
        }
        task.setCreatedAt(LocalDateTime.now());
        taskService.saveTask(task);
        return "redirect:/showtasks";
    }

    @GetMapping("/showEditTaskForm/{id}")
    public String showEditTaskForm(@PathVariable("id") long id, Model model) {
        Task task = taskService.getTaskById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
        model.addAttribute("task", task);
        return "edittaskform.html";
    }

    @PostMapping("/edittask/{id}")
    public String editTask(@PathVariable("id") long id, Task task, BindingResult result) {
        if (result.hasErrors()) {
            task.setId(id);
            return "edittaskform.html";
        }
        taskService.saveTask(task);
        return "redirect:/showtasks";
    }

    @GetMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable("id") long id, Model model) {
        Task task = taskService.getTaskById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
        taskService.deleteTask(task);
        return "redirect:/showtasks";
    }

    @GetMapping("/webSocketJson")
    public String showWebSocketPage() {
        return "websocketjson.html";
    }
}
