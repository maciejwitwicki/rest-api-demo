package demo.rest.domain.users;

import demo.rest.domain.cars.Car;
import lombok.Builder;
import lombok.With;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@With
@Builder(toBuilder = true)
record User(
        UUID id,
        @NotBlank(message = "Name is mandatory")
        String name,
        @NotBlank(message = "Job is mandatory")
        String job,
        @Positive(message = "Salary can't be negative")
        Double salary,
        LocalDateTime hiredOn,
        List<Car> cars) {
    public static User create(String name, String job, double salary, LocalDateTime hiredOn, List<Car> cars) {
        return new User(UUID.randomUUID(), name, job, salary, hiredOn, cars);
    }
}
