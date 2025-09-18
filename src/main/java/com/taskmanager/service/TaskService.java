package com.taskmanager.service;

import com.taskmanager.dto.*;
import com.taskmanager.exception.ForbiddenException;
import com.taskmanager.exception.ResourceNotFoundException;
import com.taskmanager.model.Role;
import com.taskmanager.model.Task;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.model.User;
import com.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {
        log.info("Creating task: {}", taskRequestDto.getTitle());
        
        User assignedUser = userService.findUserById(taskRequestDto.getAssignedToId());

        Task task = new Task();
        task.setTitle(taskRequestDto.getTitle());
        task.setDescription(taskRequestDto.getDescription());
        task.setStatus(taskRequestDto.getStatus());
        task.setAssignedTo(assignedUser);

        Task savedTask = taskRepository.save(task);
        log.info("Task created successfully with ID: {}", savedTask.getId());
        
        return mapToResponseDto(savedTask);
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDto> getAllTasks(TaskStatus status, Long userId) {
        log.info("Fetching tasks with filters - status: {}, userId: {}", status, userId);
        
        List<Task> tasks;
        if (status != null || userId != null) {
            tasks = taskRepository.findTasksWithFilters(status, userId);
        } else {
            tasks = taskRepository.findAllWithUsers();
        }
        
        return tasks.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TaskResponseDto getTaskById(Long id) {
        log.info("Fetching task with ID: {}", id);
        Task task = taskRepository.findByIdWithUser(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        return mapToResponseDto(task);
    }

    public TaskResponseDto updateTask(Long id, TaskUpdateDto taskUpdateDto, Long currentUserId, Role currentUserRole) {
        log.info("Updating task with ID: {}", id);
        
        Task task = taskRepository.findByIdWithUser(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));

        // Check permissions: only ADMIN or assigned user can update
        if (currentUserRole != Role.ADMIN && !task.getAssignedTo().getId().equals(currentUserId)) {
            throw new ForbiddenException("You can only update tasks assigned to you");
        }

        if (taskUpdateDto.getTitle() != null) {
            task.setTitle(taskUpdateDto.getTitle());
        }
        if (taskUpdateDto.getDescription() != null) {
            task.setDescription(taskUpdateDto.getDescription());
        }
        if (taskUpdateDto.getAssignedToId() != null) {
            User newAssignedUser = userService.findUserById(taskUpdateDto.getAssignedToId());
            task.setAssignedTo(newAssignedUser);
        }

        Task updatedTask = taskRepository.save(task);
        log.info("Task updated successfully with ID: {}", updatedTask.getId());
        
        return mapToResponseDto(updatedTask);
    }

    public TaskResponseDto updateTaskStatus(Long id, TaskStatusUpdateDto statusUpdateDto, Long currentUserId, Role currentUserRole) {
        log.info("Updating task status with ID: {}", id);
        
        Task task = taskRepository.findByIdWithUser(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));

        // Check permissions: only ADMIN or assigned user can update status
        if (currentUserRole != Role.ADMIN && !task.getAssignedTo().getId().equals(currentUserId)) {
            throw new ForbiddenException("You can only update status of tasks assigned to you");
        }

        task.setStatus(statusUpdateDto.getStatus());
        Task updatedTask = taskRepository.save(task);
        log.info("Task status updated successfully for ID: {}", updatedTask.getId());
        
        return mapToResponseDto(updatedTask);
    }

    public void deleteTask(Long id, Role currentUserRole) {
        log.info("Deleting task with ID: {}", id);
        
        if (currentUserRole != Role.ADMIN) {
            throw new ForbiddenException("Only ADMIN users can delete tasks");
        }

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));

        taskRepository.delete(task);
        log.info("Task deleted successfully with ID: {}", id);
    }

    private TaskResponseDto mapToResponseDto(Task task) {
        TaskResponseDto dto = new TaskResponseDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        
        if (task.getAssignedTo() != null) {
            UserResponseDto userDto = new UserResponseDto();
            userDto.setId(task.getAssignedTo().getId());
            userDto.setName(task.getAssignedTo().getName());
            userDto.setEmail(task.getAssignedTo().getEmail());
            userDto.setRole(task.getAssignedTo().getRole());
            dto.setAssignedTo(userDto);
        }
        
        return dto;
    }
}
