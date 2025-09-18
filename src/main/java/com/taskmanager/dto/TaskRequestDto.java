package com.taskmanager.dto;

import com.taskmanager.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDto {
    
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    
    private TaskStatus status = TaskStatus.PENDING;
    
    @NotNull(message = "Assigned user ID is required")
    private Long assignedToId;
}
