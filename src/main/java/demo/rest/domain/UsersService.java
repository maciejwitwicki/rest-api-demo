package demo.rest.domain;

import demo.rest.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final CarsService carsService;

    private Map<UUID, User> users = init();

    private Map<UUID, User> init() {
        var kowalski = User.create("Kowalski", "cook", 100_000.50).withCars(List.of(
                new Car("FZ 777", CarModel.BMW), new Car("ZG 321", CarModel.SKODA)
        ));
        var nowak = User.create("Nowak", "chef", 200_050.80);
        var drzyzga = User.create("Drzyzga", "waiter", 80_190.00);
        return new HashMap<>(Map.of(kowalski.id(), kowalski, nowak.id(), nowak, drzyzga.id(), drzyzga));
    }

    public Collection<User> getUsers(CarModel car) {
        if (car != null) {
            return users.values()
                    .stream().filter(u -> u.cars()
                            .stream()
                            .anyMatch(c -> c.model().equals(car)))
                    .toList();
        }
        return users.values();
    }

    public User save(User toBeCreated) {
        users.put(toBeCreated.id(), toBeCreated);
        return toBeCreated;
    }

    public User getById(String id) {
        return Optional.ofNullable(users.get(UUID.fromString(id)))
                .orElseThrow(() -> new NotFoundException("User with id %s not found".formatted(id)));
    }

    public User deleteById(String id) {
        var found = getById(id);
        users.remove(found.id());
        return found;
    }

    public User updateById(String id, User user) {
        getById(id);
        var updated = user.withId(UUID.fromString(id));
        this.save(updated);
        return updated;
    }

    public User patchUpdateById(String id, User user) {
        var newUserBuilder = getById(id).toBuilder();
        Optional.ofNullable(user.name())
                .ifPresent(newUserBuilder::name);

        Optional.ofNullable(user.designation())
                .ifPresent(newUserBuilder::designation);

        Optional.ofNullable(user.salary())
                .ifPresent(newUserBuilder::salary);

        Optional.ofNullable(user.cars())
                .ifPresent(newUserBuilder::cars);


        var newUser = newUserBuilder.build();
        this.save(newUser);
        return newUser;
    }

    public Collection<Car> getUserCars(String id) {
        return this.getById(id).cars();
    }
}
