package demo.rest.domain.users;

import demo.rest.domain.cars.Car;
import demo.rest.domain.cars.CarModel;
import demo.rest.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Collection<User> getUsers(CarModel car) {
        return Optional.ofNullable(car)
                .map(userRepository::findByCars_Model)
                .orElseGet(() -> userRepository.findAll().stream())
                .map(userMapper::mapToUser)
                .toList();
    }

    public User save(User user) {
        var cars = user.cars().stream().map(c -> Car.create(c.registration(), c.model())).toList();
        var toBeCreated = User.create(user.name(), user.job(), user.salary(), user.hiredOn(), cars);
        var entity = userMapper.mapToEntity(toBeCreated);
        var saved = userRepository.save(entity);
        return userMapper.mapToUser(saved);
    }

    public User getById(String id) {
        return userRepository.findById(id)
                .map(userMapper::mapToUser)
                .orElseThrow(() -> new NotFoundException("User with id %s not found".formatted(id)));
    }

    public User deleteById(String id) {
        var found = getById(id);
        userRepository.deleteById(id);
        return found;
    }

    @Transactional
    public User updateById(String id, User user) {
        getById(id);
        var updated = user.withId(UUID.fromString(id));
        var entity = userMapper.mapToEntity(updated);
        var saved = userRepository.save(entity);
        return userMapper.mapToUser(saved);
    }

    public User patchUpdateById(String id, User user) {
        var newUserBuilder = getById(id).toBuilder();
        Optional.ofNullable(user.name())
                .ifPresent(newUserBuilder::name);

        Optional.ofNullable(user.job())
                .ifPresent(newUserBuilder::job);

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
