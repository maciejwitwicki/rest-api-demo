package demo.rest.domain;

import demo.rest.exception.NotFoundException;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CarsService {

    @Getter
    private Map<String, Car> cars = init();

    private Map<String, Car> init() {
        return Stream.of(
                new Car("DW 123", CarModel.BMW),
                new Car("DSR 456", CarModel.BMW),
                new Car("WA 999", CarModel.VW),
                new Car("DW 789", CarModel.VW),
                new Car("DSR 147", CarModel.VW),
                new Car("DSR 963", CarModel.AUDI),
                new Car("DW 753", CarModel.AUDI),
                new Car("WA 357", CarModel.AUDI),
                new Car("DJ 951", CarModel.AUDI),
                new Car("DSR 999", CarModel.SKODA),
                new Car("DWL 888", CarModel.SKODA),
                new Car("DJ 852", CarModel.SKODA),
                new Car("WA 258", CarModel.SKODA)
        ).collect(Collectors.toMap(Car::registration, c -> c));
    }

    public Collection<Car> getCars(CarModel carModel) {
        if (carModel != null) {
            return cars.values()
                    .stream()
                    .filter(u -> u.model().equals(carModel))
                    .toList();
        }
        return cars.values();
    }

    public Car save(Car toBeCreated) {
        cars.put(toBeCreated.registration(), toBeCreated);
        return toBeCreated;
    }

    public Car getByRegistration(String registration) {
        return Optional.ofNullable(cars.get(registration))
                .orElseThrow(() -> new NotFoundException("Car with registration %s not found".formatted(registration)));
    }

    public Car deleteByRegistration(String registration) {
        var found = getByRegistration(registration);
        cars.remove(registration);
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

        return newCar.build();
    }
}
