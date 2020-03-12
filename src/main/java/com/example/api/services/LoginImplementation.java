package com.example.api.services;


import com.example.api.domain.Login;
import com.example.api.domain.Response;
import com.example.api.entity.Users;
import com.example.api.exceptions.handleException;
import com.example.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class LoginImplementation implements LoginService {

    @Autowired
    UserRepository repo;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public  Object access(Login login) throws handleException {

        Users resp = repo.findUser(login.getUsername());

        if (resp == null || !encoder.matches(login.getPassword(), resp.getPassword())) {
            throw new handleException(HttpStatus.UNAUTHORIZED, "Usuario o Contraseña Invalido");
        }

        Users modified = repo.save(resp);

        Response data = new Response();
        data.setId(modified.getId());
        data.setCreated(modified.getCreated());
        data.setModified(modified.getModified());
        data.setLastLogin(modified.getLastLogin());
        data.setToken(modified.getToken());
        return data;
    }

}
