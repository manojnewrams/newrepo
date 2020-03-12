package com.example.api.services;

import com.example.api.domain.Response;
import com.example.api.entity.Users;
import com.example.api.exceptions.handleException;
import com.example.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class ProfileImplementation implements ProfileService {

    @Autowired
    UserRepository repo;

    @Override
    public Object getProfile(String id) throws handleException {
        Users users;
        users = repo.getOne(id);
        if (users == null) {
            throw new handleException(HttpStatus.NO_CONTENT, "No user Exists");
        }

        Response data = new Response();
        data.setId(users.getId());
        data.setCreated(users.getCreated());
        data.setModified(users.getModified());
        data.setLastLogin(users.getLastLogin());
        data.setToken(users.getToken());
        return data;
    }

}
