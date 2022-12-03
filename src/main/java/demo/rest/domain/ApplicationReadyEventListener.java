package demo.rest.domain;

import demo.rest.domain.cars.CarEntity;
import demo.rest.domain.cars.CarRepository;
import demo.rest.domain.users.PerformanceEntity;
import demo.rest.domain.users.PerformanceRepository;
import demo.rest.domain.users.UserEntity;
import demo.rest.domain.users.UserRepository;
import demo.rest.util.DataGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

import static demo.rest.util.RandomDataGenerator.*;

@Component
@RequiredArgsConstructor
public class ApplicationReadyEventListener {

    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final PerformanceRepository performanceRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReadyEvent() {

        performanceRepository.deleteAll();
        carRepository.deleteAll();
        userRepository.deleteAll();

        var users = IntStream.range(0, 5)
                .mapToObj(i -> randomUser())
                .toList();

        users.forEach(userRepository::save);
        var userIds = users.stream().map(UserEntity::getId).toList();

        IntStream.range(0, 10)
                .mapToObj(i -> randomCar(userIds))
                .forEach(carRepository::save);

        users.forEach(
                u -> performanceRepository.save(randomPerformance(u.getId()))
        );
    }

    private UserEntity randomUser() {
        return UserEntity.builder()
                .id(randomString())
                .name(randomName())
                .salary(randomDouble())
                .job(randomJob())
                .hiredOn(randomLocalDateTime())
                .build();
    }

    private CarEntity randomCar(List<String> users) {
        var randomUserId = users.get(randomInt(users.size()));
        return CarEntity.builder()
                .id(randomString())
                .registration(DataGenerator.uniqueRegistration())
                .model(randomCarModel())
                .userId(randomUserId)
                .build();
    }

    private PerformanceEntity randomPerformance(String userId) {
        return PerformanceEntity.builder()
                .id(randomString())
                .period("2021")
                .rating(randomDouble(10))
                .type(randomItem(List.of("driving", "navigation")))
                .userId(userId)
                .build();
    }
}
