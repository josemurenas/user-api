package com.exercise.userapi.mapper;

import com.exercise.userapi.config.MapstructConfig;
import com.exercise.userapi.entity.Phone;
import com.exercise.userapi.model.PhoneDto;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", config = MapstructConfig.class)
public interface PhoneMapper {

    PhoneDto toEntity(Phone phone);

    Phone toEntity(PhoneDto phoneDto);

    Set<Phone> toEntityList(List<PhoneDto> phones);
}
