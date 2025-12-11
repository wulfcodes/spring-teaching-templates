package io.wulfcodes.library.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.wulfcodes.library.model.dto.UserData;
import io.wulfcodes.library.model.po.User;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "fullName", expression = "java(String.format(\"%s %s\", user.getFirstName(), user.getLastName()))")
    @Mapping(target = "password", ignore = true)
    UserData toData(User user);

    List<UserData> toDataList(Iterable<User> users);

}
