package com.adarsh.ConnectHub.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank
    @Size(max = 50)
    private String name;

    @Size(max=100)
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
}
