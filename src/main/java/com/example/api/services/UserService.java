package com.example.api.services;

import com.example.api.entity.Users;
import com.example.api.entity.UsersDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



/**
 * @author s2526158
 *
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    com.example.api.repository.UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {

        Users users = repo.findUser(user);

        if (users != null) {
            return new UsersDetail(users);
        } else {
            throw new UsernameNotFoundException("User Not found : " + user);
        }
    }

}
