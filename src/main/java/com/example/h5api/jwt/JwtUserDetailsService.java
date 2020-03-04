package com.example.h5api.jwt;

import com.example.h5api.entity.UserApp;
import com.example.h5api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userInfoRepository;

    @Autowired
    public JwtUserDetailsService(UserRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserApp user = userInfoRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        String role;
        Collection<GrantedAuthority> roles = new ArrayList<>();

        if (user.getRole()) {
            role = "ROLE_ADMIN";
            roles.add(new SimpleGrantedAuthority(role));
        } else {
            role = "ROLE_USER";
            roles.add(new SimpleGrantedAuthority(role));

        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles);
    }

}
