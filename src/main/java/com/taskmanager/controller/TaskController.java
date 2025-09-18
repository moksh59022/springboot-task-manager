package com.taskmanager.controller;

import com.taskmanager.dto.*;
import com.taskmanager.model.Role;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Tag(name = "Task Management", description = "APIs for managing tasks")
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Create a new task", description = "Creates a new task and assigns it to a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Assigned user not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(
            @Valid @RequestBody TaskRequestDto taskRequestDto) {
        TaskResponseDto createdTask = taskService.createTask(taskRequestDto);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all tasks", description = "Retrieves all tasks with optional filtering by status and user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getAllTasks(
            @Parameter(description = "Filter by task status")
            @RequestParam(required = false) TaskStatus status,
            @Parameter(description = "Filter by user ID")
            @RequestParam(required = false) Long userId) {
        List<TaskResponseDto> tasks = taskService.getAllTasks(status, userId);
        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "Get task by ID", description = "Retrieves a specific task by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(
            @Parameter(description = "Task ID", required = true)
            @PathVariable Long id) {
        TaskResponseDto task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @Operation(summary = "Update task", description = "Updates task details (title, description, assigned user)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "Access denied - can only update own tasks"),
            @ApiResponse(responseCode = "404", description = "Task or user not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @Parameter(description = "Task ID", required = true)
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateDto taskUpdateDto,
            @Parameter(description = "Current user ID", required = true)
            @RequestHeader("X-User-Id") Long currentUserId,
            @Parameter(description = "Current user role", required = true)
            @RequestHeader("X-User-Role") Role currentUserRole) {
        TaskResponseDto updatedTask = taskService.updateTask(id, taskUpdateDto, currentUserId, currentUserRole);
        return ResponseEntity.ok(updatedTask);
    }

    @Operation(summary = "Update task status", description = "Updates only the status of a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid status"),
            @ApiResponse(responseCode = "403", description = "Access denied - can only update own tasks"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponseDto> updateTaskStatus(
            @Parameter(description = "Task ID", required = true)
            @PathVariable Long id,
            @Valid @RequestBody TaskStatusUpdateDto statusUpdateDto,
            @Parameter(description = "Current user ID", required = true)
            @RequestHeader("X-User-Id") Long currentUserId,
            @Parameter(description = "Current user role", required = true)
            @RequestHeader("X-User-Role") Role currentUserRole) {
        TaskResponseDto updatedTask = taskService.updateTaskStatus(id, statusUpdateDto, currentUserId, currentUserRole);
        return ResponseEntity.ok(updatedTask);
    }

    @Operation(summary = "Delete task", description = "Deletes a task (ADMIN only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - only ADMIN can delete tasks"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @Parameter(description = "Task ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "Current user role", required = true)
            @RequestHeader("X-User-Role") Role currentUserRole) {
        taskService.deleteTask(id, currentUserRole);
        return ResponseEntity.noContent().build();
    }
}
