package demo.rest.domain.users;

import demo.rest.domain.cars.CarMapper;
import demo.rest.domain.config.DefaultMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = DefaultMapperConfig.class, uses = CarMapper.class)
public interface UserMapper {

    User mapToUser(UserEntity entity);

    UserEntity mapToEntity(User user);
}
