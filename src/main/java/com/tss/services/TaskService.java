package com.tss.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.tss.repositories.TaskRepository;
import com.tss.dtos.TaskDTO;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAllByOrderByNameAsc()
            .stream()
            .map(task -> new TaskDTO(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getCreatedAt() != null ? task.getCreatedAt().format(formatter) : "",
                task.getDueTo() != null ? task.getDueTo().format(formatter) : "",
                task.getPriority().name()
            ))
            .collect(Collectors.toList());
    }
}
