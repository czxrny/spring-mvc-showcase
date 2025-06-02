package com.tss.controllers;

import com.tss.dtos.TaskDTO;
import com.tss.services.TaskService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/tasks")
public class TaskRestController {
    @Autowired
    TaskService taskService;

    @GetMapping("/")
    public List<TaskDTO> getAllProducts() {
        return taskService.getAllTasks();
    }
}
