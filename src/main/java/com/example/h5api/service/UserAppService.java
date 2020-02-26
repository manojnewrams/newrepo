package com.example.h5api.service;

import com.example.h5api.dto.UserDto;
import com.example.h5api.dto.UserDtoIdName;
import com.example.h5api.entity.UserApp;
import com.example.h5api.exceptions.GenericEmptyListException;
import com.example.h5api.exceptions.UserAlreadyExistException;
import com.example.h5api.exceptions.UserNotFoundException;
import com.example.h5api.repository.UserRepository;
import com.example.h5api.utils.UserUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Log
public class UserAppService implements GenericService<UserDto> {

    private final UserRepository userRepository;

    private final UserUtil userUtil;


    @Autowired
    public UserAppService(UserRepository userRepository, UserUtil userUtil) {
        this.userRepository = userRepository;
        this.userUtil = userUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        List<UserApp> userList = new ArrayList<>();
        userRepository.findAll().forEach(userList::add);
        if (userList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        return userList.stream()
                .map(userUtil::transformFromUserAppToUserDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAllNotDeleted() {
        List<UserApp> userList = new ArrayList<>(userRepository.findAllNotDeleted());
        List<UserDto> userListAsDTO = userList.stream()
                .map(userUtil::transformFromUserAppToUserDto).collect(Collectors.toList());
        if (userListAsDTO.isEmpty()) {
            throw new GenericEmptyListException();
        }
        return userListAsDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findById(Integer id) {
        UserApp user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return userUtil.transformFromUserAppToUserDto(user);
    }

    @Override
    @Transactional
    public UserDto save(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistException(userDto.getEmail());
        }
        String password = userDto.getPassword();
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        userDto.setPassword(encodedPassword);
        userRepository.save(userUtil.transformFromUserDtoToUserApp(userDto));
        return userDto;
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        UserApp user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setDeleteAt(new Date());
        user.setStatus(false);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public Boolean existById(Integer id) {
        return userRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public List<UserDtoIdName> findAllUserIdName() {
        List<UserApp> userList = new ArrayList<>();
        userRepository.findAll().forEach(userList::add);
        if (userList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        return userList.stream()
                .map(userUtil::transformFromUserAppToUserDtoIdName).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserDtoIdName> findAllAvailableCandidates(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        List<UserApp> userList = new ArrayList<>();
        userRepository.findAllAvailable().forEach(userList::add);
        if (userList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        userList.removeIf(user -> user.getId() == id);
        return userList
                .stream()
                .map(userUtil::transformFromUserAppToUserDtoIdName)
                .collect(Collectors.toList());
    }

    /**
     * Return true if save all the items of the array
     */
    @Transactional
    public boolean saveList(ArrayList<UserDto> userList) {
        userList.forEach(this::save);
        return true;
    }



    // **** Here comes the methods useful for Nomination ****

    public Set<UserDtoIdName> findAllUserList(Integer valueid) {
        List<UserApp> userList = new ArrayList<>();
        userRepository.findUserNameAndId(valueid).forEach(userList::add);
        if (userList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        Set<UserDtoIdName> userDtoList = userList.stream()
                .map(userUtil::transformFromUserAppToUserDtoIdName).collect(Collectors.toSet());
        return userDtoList;
    }

    public List<UserDtoIdName> findAllUserNominatorList(Integer valueid, Integer userid) {
        Set<UserApp> userList = new HashSet<>();
        userRepository.findUserNameAndIdForNominator(valueid, userid).forEach(userList::add);
        if (userList.isEmpty()) {
            throw new GenericEmptyListException();
        }
        List<UserDtoIdName> usernominationList = userList.stream()
                .map(userUtil::transformFromUserAppToUserDtoIdName).collect(Collectors.toList());
        return usernominationList;
    }


}
