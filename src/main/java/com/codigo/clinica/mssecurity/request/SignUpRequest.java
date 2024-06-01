package com.codigo.clinica.mssecurity.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

    @NotBlank(message = "El campo nombre es necesario.")
    private String name;

    @NotBlank(message = "El campo surname es necesario.")
    private String surname;

    @NotBlank(message = "El campo email es necesario.")
    private String email;

    @NotBlank(message = "El campo password es necesario.")
    private String password;
}
