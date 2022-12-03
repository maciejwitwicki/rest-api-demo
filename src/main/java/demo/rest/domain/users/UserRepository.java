package demo.rest.domain.users;

import demo.rest.domain.cars.CarModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.stream.Stream;

public interface UserRepository extends CrudRepository<UserEntity, String> {

//    @Query("SELECT u from UserEntity u where u.cars.model = :model")
//    Stream<UserEntity> findByCarModel(CarModel model);

    Stream<UserEntity> findByCars_Model(CarModel model);

    List<UserEntity> findAll();
}
