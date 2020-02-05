package com.example.h5api.service;

import com.example.h5api.builders.Transformer;
import com.example.h5api.dao.IUserAppDao;
import com.example.h5api.dto.UserDto;
import com.example.h5api.dto.UserDtoIdName;
import com.example.h5api.entity.UserApp;
import com.example.h5api.exceptions.ValidationException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log
public class UserAppService extends Transformer implements IGenericService<UserDto> {

    @Autowired
    private IUserAppDao userAppDao;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        List<UserApp> userList = new ArrayList<>();
        userAppDao.findAll().forEach(userList::add);
        List<UserDto> userListAsDTO = userList.stream()
                .map(this::transformFromUserAppToUserDto).collect(Collectors.toList());
        return userListAsDTO;
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAllNotDeleted() {
        List<UserApp> userList = new ArrayList<>(userAppDao.findAllNotDeleted());
        List<UserDto> userListAsDTO = userList.stream()
                .map(this::transformFromUserAppToUserDto).collect(Collectors.toList());
        return userListAsDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findById(Integer id) {
        UserApp user = userAppDao.findById(id).orElse(null);
        return transformFromUserAppToUserDto(user);
    }

    @Override
    @Transactional
    public UserDto save(UserDto userDto) {
        if (userAppDao.existsByEmail(userDto.getEmail())){
            String msg = "{\"message\":\"User already existed\"}";
            throw new ValidationException(msg);
        }
        //int lastId = userAppDao.getLastId();
        //log.info("Last Id: "+lastId);
        String password = userDto.getPassword();
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        userDto.setPassword(encodedPassword);
       // userDto.setId(lastId+1);
        userAppDao.save(transformFromUserDtoToUserApp(userDto));
        return userDto;
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        UserApp user = userAppDao.findById(id).orElse(null);
        if (user != null) {
            user.setDeleteAt(new Date());
            user.setStatus(false);
            userAppDao.save(user);
        }
    }

    @Override
    @Transactional
    public Boolean existById(Integer id) {
        return userAppDao.existsById(id);
    }

    @Transactional(readOnly = true)
    public List<UserDtoIdName> findAllUserIdName() {
        List<UserApp> userList = new ArrayList<>();
        userAppDao.findAll().forEach(userList::add);
        List<UserDtoIdName> userListAsDTO = userList.stream()
                .map(this::transformFromUserAppToUserDtoIdName).collect(Collectors.toList());
        return userListAsDTO;
    }

    /**
     * Return true if save all the items of the array
     * */
    @Transactional
    public boolean saveList(ArrayList<UserDto> userList){
        userList.forEach(item -> {
            if (!(save(item) instanceof UserDto))
            {
                throw new ValidationException("We had a problem saving user: " + item.getName());
            }
        });
        return true;
    }
}
