package demo.rest.domain.cars;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapper {

    Car mapToCar(CarEntity entity);

    CarEntity mapToEntity(Car car);


}
