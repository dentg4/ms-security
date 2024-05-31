package com.codigo.clinica.mssecurity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    @GetMapping("/hello")
    public ResponseEntity<String> hiAdmin(){
        return ResponseEntity.ok("Hi, Admin");
    }
}