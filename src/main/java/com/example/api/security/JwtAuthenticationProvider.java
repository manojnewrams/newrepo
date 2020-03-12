package com.example.api.security;

import com.example.api.repository.UserRepository;
import com.example.api.security.JWTGenerator;
import com.example.api.security.JwtAuthenticationToken;
import com.example.api.entity.Users;
import com.example.api.entity.UsersDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;

/**
 * @author s2526158
 *
 */
@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    UserRepository repository;

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.authentication.dao.
     * AbstractUserDetailsAuthenticationProvider#additionalAuthenticationChecks(org.
     * springframework.security.core.userdetails.UserDetails,
     * org.springframework.security.authentication.
     * UsernamePasswordAuthenticationToken)
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails arg0, UsernamePasswordAuthenticationToken arg1)
            throws AuthenticationException {

    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.authentication.dao.
     * AbstractUserDetailsAuthenticationProvider#retrieveUser(java.lang.String,
     * org.springframework.security.authentication.
     * UsernamePasswordAuthenticationToken)
     */
    @Override
    protected UserDetails retrieveUser(String arg0,
                                       UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) usernamePasswordAuthenticationToken;
        String token = jwtAuthenticationToken.getToken();

        Claims jwtUser = JWTGenerator.parseJWT(token);

        if (jwtUser == null) {
            throw new RuntimeException("JWT Token is incorrect");
        }

        Users user = new Users();
        user.setToken(token);
        user.setUsername(jwtUser.getId());
        user.setPassword(jwtUser.getSubject());

        Users response = (Users) repository.findUser(user.getUsername());

        if (response != null) {
            UsersDetail users = new UsersDetail(response);
            return users;
        }

        throw new AuthenticationCredentialsNotFoundException("No token exists");
    }

}
