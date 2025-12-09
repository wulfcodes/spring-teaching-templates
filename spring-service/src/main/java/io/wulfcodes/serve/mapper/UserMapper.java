package io.wulfcodes.serve.mapper;

import java.util.List;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.wulfcodes.serve.model.dto.UserDto;
import io.wulfcodes.serve.model.po.Address;
import io.wulfcodes.serve.model.po.User;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "fullName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    @Mapping(target = "username", source = "account.username")
    @Mapping(target = "createdAt", ignore = true)
    UserDto toData(User user);

    @InheritConfiguration(name = "toData")
    @Mapping(target = "city", source = "address.city")
    @Mapping(target = "state", source = "address.state")
    @Mapping(target = "country", source = "address.country")
    UserDto toData(User user, Address address);

    @InheritInverseConfiguration(name = "toData")
    @Mapping(target = "createdAt", source = "createdAt") // overrides ignore
    User toEntity(UserDto dto);


    List<UserDto> toDataList(Iterable<User> users);


}
