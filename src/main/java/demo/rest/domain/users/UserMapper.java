package demo.rest.domain.users;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapToUser(UserEntity entity);

    UserEntity mapToEntity(User user);
}
