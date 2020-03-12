package com.example.api.services;


import com.example.api.domain.Login;
import com.example.api.exceptions.handleException;

/**
 *
 * @author s2526158
 *
 */
public interface LoginService {

    public Object access(Login login) throws handleException;

}
