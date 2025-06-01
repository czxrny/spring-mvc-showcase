package com.tss.dtos;

public class TaskDTO {
    private Long id;
    private String name;
    private String description;
    private String createdAt;
    private String dueTo;
    private String priority;

    public TaskDTO(Long id, String name, String description, String createdAt, String dueTo, String priority) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.dueTo = dueTo;
        this.priority = priority;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getDueTo() { return dueTo; }
    public void setDueTo(String dueTo) { this.dueTo = dueTo; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
}
