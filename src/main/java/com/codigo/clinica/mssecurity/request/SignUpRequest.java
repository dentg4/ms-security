package com.codigo.clinica.mssecurity.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
}
