package com.example.api.services;


import com.example.api.exceptions.handleException;

/**
 *
 * @author s2526158
 *
 */
public interface ProfileService {

    public Object getProfile(String id) throws handleException;
}
