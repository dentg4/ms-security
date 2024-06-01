package com.codigo.clinica.mssecurity.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {

    @NotBlank(message = "El campo email es necesario.")
    private String email;

    @NotBlank(message = "El campo password es necesario.")
    private String password;
}
