package com.taskmanager.config;

import com.taskmanager.model.Role;
import com.taskmanager.model.Task;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.model.User;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!test") // Don't run in test environment
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            log.info("Initializing sample data...");
            initializeSampleData();
            log.info("Sample data initialization completed!");
        } else {
            log.info("Database already contains data, skipping initialization.");
        }
    }

    private void initializeSampleData() {
        // Create sample users
        User admin = new User();
        admin.setName("Admin User");
        admin.setEmail("admin@taskmanager.com");
        admin.setRole(Role.ADMIN);
        admin = userRepository.save(admin);

        User user1 = new User();
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");
        user1.setRole(Role.USER);
        user1 = userRepository.save(user1);

        User user2 = new User();
        user2.setName("Jane Smith");
        user2.setEmail("jane.smith@example.com");
        user2.setRole(Role.USER);
        user2 = userRepository.save(user2);

        // Create sample tasks
        Task task1 = new Task();
        task1.setTitle("Setup Development Environment");
        task1.setDescription("Install and configure all necessary development tools and dependencies");
        task1.setStatus(TaskStatus.COMPLETED);
        task1.setAssignedTo(user1);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Design Database Schema");
        task2.setDescription("Create ERD and design the database schema for the application");
        task2.setStatus(TaskStatus.IN_PROGRESS);
        task2.setAssignedTo(user1);
        taskRepository.save(task2);

        Task task3 = new Task();
        task3.setTitle("Implement User Authentication");
        task3.setDescription("Develop user registration, login, and authentication system");
        task3.setStatus(TaskStatus.PENDING);
        task3.setAssignedTo(user2);
        taskRepository.save(task3);

        Task task4 = new Task();
        task4.setTitle("Create API Documentation");
        task4.setDescription("Write comprehensive API documentation using Swagger/OpenAPI");
        task4.setStatus(TaskStatus.PENDING);
        task4.setAssignedTo(user2);
        taskRepository.save(task4);

        Task task5 = new Task();
        task5.setTitle("Code Review and Testing");
        task5.setDescription("Review all code changes and implement comprehensive test cases");
        task5.setStatus(TaskStatus.PENDING);
        task5.setAssignedTo(admin);
        taskRepository.save(task5);

        log.info("Created {} users and {} tasks", userRepository.count(), taskRepository.count());
    }
}
