package com.example.h5api.service;

import com.example.h5api.utils.Transformer;
import com.example.h5api.repository.IUserAppDao;
import com.example.h5api.dto.UserDto;
import com.example.h5api.dto.UserDtoIdName;
import com.example.h5api.entity.UserApp;
import com.example.h5api.exceptions.GenericEmptyListException;
import com.example.h5api.exceptions.UserAlreadyExistException;
import com.example.h5api.exceptions.UserNotFoundException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@Log
public class UserAppService extends Transformer implements GenericService<UserDto> {

    @Autowired
    private IUserAppDao userAppDao;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        List<UserApp> userList = new ArrayList<>();
        userAppDao.findAll().forEach(userList::add);
        if (userList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        List<UserDto> userListAsDTO = userList.stream()
                .map(this::transformFromUserAppToUserDto).collect(Collectors.toList());
        return userListAsDTO;
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAllNotDeleted() {
        List<UserApp> userList = new ArrayList<>(userAppDao.findAllNotDeleted());
        List<UserDto> userListAsDTO = userList.stream()
                .map(this::transformFromUserAppToUserDto).collect(Collectors.toList());
        if (userListAsDTO.isEmpty()) {
            throw new GenericEmptyListException();
        }
        return userListAsDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findById(Integer id) {
        UserApp user = userAppDao.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return transformFromUserAppToUserDto(user);
    }

    @Override
    @Transactional
    public UserDto save(UserDto userDto) {
        if (userAppDao.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistException(userDto.getEmail());
        }
        String password = userDto.getPassword();
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        userDto.setPassword(encodedPassword);
        userAppDao.save(transformFromUserDtoToUserApp(userDto));
        return userDto;
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        UserApp user = userAppDao.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setDeleteAt(new Date());
        user.setStatus(false);
        userAppDao.save(user);
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
        if (userList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        List<UserDtoIdName> userListAsDTO = userList.stream()
                .map(this::transformFromUserAppToUserDtoIdName).collect(Collectors.toList());
        return userListAsDTO;
    }

    @Transactional(readOnly = true)
    public List<UserDtoIdName> findAllAvailableCandidates(Integer id) {
        if (!userAppDao.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        List<UserApp> userList = new ArrayList<>();
        userAppDao.findAllAvailable().forEach(userList::add);
        if (userList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        userList.removeIf(user -> user.getId() == id);
        List<UserDtoIdName> userListAsDTO = userList
                .stream()
                .map(this::transformFromUserAppToUserDtoIdName)
                .collect(Collectors.toList());
        return userListAsDTO;
    }

    /**
     * Return true if save all the items of the array
     */
    @Transactional
    public boolean saveList(ArrayList<UserDto> userList) {
        userList.forEach(this::save);
        return true;
    }

}
