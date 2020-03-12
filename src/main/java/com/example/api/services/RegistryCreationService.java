package com.example.api.services;


import com.example.api.domain.RegisterUser;
import com.example.api.exceptions.handleException;

/**
 *
 * @author s2526158
 *
 */
public interface RegistryCreationService {

    public Object create(RegisterUser registerUser) throws handleException;

}
