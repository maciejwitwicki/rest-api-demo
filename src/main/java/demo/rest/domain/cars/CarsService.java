package demo.rest.domain.cars;

import demo.rest.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarsService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Transactional(readOnly = true)
    public Collection<Car> getCars(CarModel carModel) {
        return Optional.ofNullable(carModel)
                .map(carRepository::findByModel)
                .orElseGet(() -> carRepository.findAll().stream())
                .map(carMapper::mapToCar)
                .toList();
    }

    public Car save(Car car) {
        var toBeCreated = Car.create(car.registration(), car.model());
        var entity = carMapper.mapToEntity(toBeCreated);
        var saved = carRepository.save(entity);
        return carMapper.mapToCar(saved);
    }

    public Car getByRegistration(String registration) {
        return carRepository.findByRegistration(registration)
                .map(carMapper::mapToCar)
                .orElseThrow(() -> new NotFoundException("Car with registration %s not found".formatted(registration)));
    }

    public Car deleteByRegistration(String registration) {
        var found = getByRegistration(registration);
        carRepository.deleteByRegistration(registration);
        return found;
    }

    public Car updateById(String registration, Car car) {
        getByRegistration(registration);
        var updated = car.withRegistration(registration);
        this.save(updated);
        return updated;
    }

    public Car patchUpdateByRegistration(String registration, Car car) {
        var newCar = getByRegistration(registration).toBuilder();
        Optional.ofNullable(car.model())
                .ifPresent(newCar::model);

        var toSave = newCar.build();
        return save(toSave);
    }
}
