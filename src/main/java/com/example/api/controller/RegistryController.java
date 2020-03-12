package com.example.api.controller;

import com.example.api.domain.Login;
import com.example.api.domain.RegisterUser;
import com.example.api.exceptions.handleException;
import com.example.api.services.LoginService;
import com.example.api.services.RegistryCreationService;
import com.example.api.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
public class RegistryController {

    @Autowired
    RegistryCreationService registryCreationService;

    @Autowired
    LoginService loginService;

    @Autowired
    ProfileService profileService;


    @PostMapping(value = "/app/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterUser register) throws handleException {
        try {
            return new ResponseEntity<Object>(registryCreationService.create(register), HttpStatus.OK);
        } catch (handleException e) {
            return new ResponseEntity<Object>(e.getMessage(), e.getStatus());
        }
    }



    @PostMapping(value = "/app/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> access(@RequestBody Login login) throws handleException {
        try {
            return new ResponseEntity<Object>(loginService.access(login), HttpStatus.OK);
        } catch (handleException e) {
            throw e;
        }
    }

    @GetMapping(value = "/app/profile/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> profile(@PathVariable String id) throws handleException {
        try {
            return new ResponseEntity<Object>(profileService.getProfile(id), HttpStatus.OK);
        } catch (handleException e) {
            throw e;
        }
    }


}
