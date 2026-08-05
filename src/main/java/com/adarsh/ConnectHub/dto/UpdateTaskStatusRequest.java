package com.adarsh.ConnectHub.dto;


import com.adarsh.ConnectHub.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskStatusRequest {
    private TaskStatus status;
}
