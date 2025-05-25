package com.exercise.userapi.mapper;

import com.exercise.userapi.config.MapstructConfig;
import com.exercise.userapi.entity.User;
import com.exercise.userapi.model.UserDto;
import lombok.experimental.Accessors;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",config = MapstructConfig.class)
@Accessors(fluent = false)
public interface UserMapper {

    UserDto map(User user);

    User toEntity(UserDto user);

}