package com.example.api.services;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;

import com.example.api.security.JWTGenerator;
import com.example.api.domain.RegisterUser;
import com.example.api.domain.Response;
import com.example.api.entity.Users;
import com.example.api.exceptions.handleException;
import com.example.api.utils.Util;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;



@Service
public class RegistryImplementation implements RegistryCreationService {

    @Autowired
    com.example.api.repository.UserRepository repo;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public Object create(RegisterUser registerUser) throws handleException {
        Response data = new Response();
        Users userValid = repo.findUser(registerUser.getEmail());

        if (userValid == null) {
            try {
                String uuid = Util.getUIID();
                Users user = new Users();
                user.setId(uuid);
                user.setUsername(registerUser.getEmail());
                user.setPassword(encoder.encode(registerUser.getPassword()));
                user.setToken(JWTGenerator.createJWT(registerUser.getEmail(), registerUser.getPassword()));

                byte[] json = Util.toJson(registerUser.getPhones());

                Blob datos = BlobProxy.generateProxy(json);
                user.setDatos(datos);
                Users response = repo.save(user);

                data.setId(response.getId());
                data.setCreated(response.getCreated());
                data.setModified(response.getModified());
                data.setLastLogin(response.getLastLogin());
                data.setToken(response.getToken());
                return data;
            } catch (JsonProcessingException | UnsupportedEncodingException e) {
                throw new handleException(HttpStatus.BAD_REQUEST, "Mail already registered");
            }
        }
        throw new handleException(HttpStatus.CONFLICT, "Mail already registered");
    }

}
