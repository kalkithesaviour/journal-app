package com.vishal.journalbackend.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private String email;

    private Boolean sentimentAnalysis;

}
