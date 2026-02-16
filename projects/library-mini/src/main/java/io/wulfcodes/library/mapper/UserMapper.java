package io.wulfcodes.library.mapper;

import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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

    @Mapping(target = "id", source = "userId")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "phoneNo", ignore = true)
    @Mapping(target = "address", ignore = true)
    User toEntity(UserData userData);

    @AfterMapping
    default void setNames(@MappingTarget User user, UserData userData) {
        if (userData.getFullName() != null) {
            String[] parts = userData.getFullName().trim().split("\\s+", 2);
            user.setFirstName(parts.length > 0 ? parts[0] : "");
            user.setLastName(parts.length > 1 ? parts[1] : "");
        }
    }

}
