package com.taskmanager.dto;

import com.taskmanager.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    
    private Long id;
    private String name;
    private String email;
    private Role role;
    private int taskCount;
}
