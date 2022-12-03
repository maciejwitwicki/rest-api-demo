package demo.rest.domain.users;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PerformanceMapper {

    Performance map(PerformanceEntity entity);

    PerformanceEntity mapToEntity(Performance user);
}
