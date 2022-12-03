package demo.rest.domain.cars;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface CarRepository extends CrudRepository<CarEntity, String> {

    Stream<CarEntity> findByModel(CarModel model);

    List<CarEntity> findAll();

    Optional<CarEntity> findByRegistration(String registration);

    CarEntity deleteByRegistration(String registration);
}
