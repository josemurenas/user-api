package com.exercise.userapi.mapper;

import com.exercise.userapi.config.MapstructConfig;
import com.exercise.userapi.entity.Phone;
import com.exercise.userapi.model.PhoneDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = MapstructConfig.class)
public interface PhoneMapper {

    PhoneDto map(Phone phone);

    Phone map(PhoneDto phoneDto);
}
