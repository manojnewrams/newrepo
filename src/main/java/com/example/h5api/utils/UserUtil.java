package com.example.h5api.utils;

import com.example.h5api.dto.UserDto;
import com.example.h5api.dto.UserDtoIdName;
import com.example.h5api.entity.UserApp;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {
    public UserApp transformFromUserDtoToUserApp(UserDto userDTO) {
        return UserApp.builder()
                .createAt(userDTO.getCreateAt())
                .deleteAt(userDTO.getDeleteAt())
                .updateAt(userDTO.getUpdateAt())
                .name(userDTO.getName())
                .company(userDTO.getCompany())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .status(userDTO.getStatus())
                .role(userDTO.getRole())
                .build();
    }

    public UserDtoIdName transformFromUserDtoToUserDtoIdName(UserDto userDto) {
        return UserDtoIdName.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .build();
    }

    public UserDto transformFromUserAppToUserDto(UserApp userApp) {
        return UserDto.builder()
                .id(userApp.getId())
                .createAt(userApp.getCreateAt())
                .deleteAt(userApp.getDeleteAt())
                .updateAt(userApp.getUpdateAt())
                .name(userApp.getName())
                .company(userApp.getCompany())
                .email(userApp.getEmail())
                .password(userApp.getPassword())
                .role(userApp.getRole())
                .status(userApp.getStatus())
                .build();
    }

    public UserDtoIdName transformFromUserAppToUserDtoIdName(UserApp userApp) {
        return UserDtoIdName.builder()
                .id(userApp.getId())
                .name(userApp.getName())
                .build();
    }

    public UserApp transformFromUserDtoIdNameToUserApp(UserDtoIdName userDtoIdName) {
        return UserApp.builder()
                .id(userDtoIdName.getId())
                .name(userDtoIdName.getName())
                .build();
    }

}
