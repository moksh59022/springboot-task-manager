package com.taskmanager.repository;

import com.taskmanager.model.Task;
import com.taskmanager.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    List<Task> findByStatus(TaskStatus status);
    
    List<Task> findByAssignedToId(Long userId);
    
    List<Task> findByStatusAndAssignedToId(TaskStatus status, Long userId);
    
    @Query("SELECT t FROM Task t JOIN FETCH t.assignedTo WHERE t.id = :id")
    Optional<Task> findByIdWithUser(@Param("id") Long id);
    
    @Query("SELECT t FROM Task t JOIN FETCH t.assignedTo")
    List<Task> findAllWithUsers();
    
    @Query("SELECT t FROM Task t JOIN FETCH t.assignedTo WHERE " +
           "(:status IS NULL OR t.status = :status) AND " +
           "(:userId IS NULL OR t.assignedTo.id = :userId)")
    List<Task> findTasksWithFilters(@Param("status") TaskStatus status, @Param("userId") Long userId);
}
