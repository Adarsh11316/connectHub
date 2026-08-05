package com.adarsh.ConnectHub.dto;


import com.adarsh.ConnectHub.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private Priority priority;

}

