//package com.example.h5api.service;
//
//import com.example.h5api.dao.IUserAppDao;
//import com.example.h5api.entity.UserApp;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//
//@Component
//public class JwtUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private IUserAppDao userInfoRepository;
//
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//
//        UserApp user = userInfoRepository.findByEmail(email);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found with email: " + email);
//        }
//        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
//                new ArrayList<>());
//    }
//
//}
