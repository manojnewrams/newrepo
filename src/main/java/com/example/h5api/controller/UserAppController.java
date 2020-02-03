package com.example.h5api.controller;

import com.example.h5api.dto.UserDto;
import com.example.h5api.dto.UserDtoIdName;
import com.example.h5api.service.UserAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserAppController implements IController<UserDto> {

    @Autowired
    UserAppService userAppService;

    @Override
    public List<UserDto> list() {
        return userAppService.findAll();
    }

    @Override
    public UserDto findById(Integer id) {
        return userAppService.findById(id);
    }

    @Override
    public UserDto save(UserDto obj) {
        return userAppService.save(obj);
    }

    @Override
    public void delete(Integer id) {
        userAppService.deleteById(id);
    }

    @GetMapping("/listNotDeleted")
    public List<UserDto> deleteApp() {
        return userAppService.findAllNotDeleted();
    }

    @GetMapping("/list")
    public List<UserDtoIdName> getUserByIdName() {
        return userAppService.findAllUserIdName();
    }

    @PostMapping("/list")
    public boolean save(@RequestBody ArrayList<UserDto> body){
        return userAppService.saveList(body);
    }

}
