package demo.rest.domain.users;

import demo.rest.domain.cars.Car;
import lombok.Builder;
import lombok.With;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
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
        List<Car> cars) {
    public static User create(String name, String job, double salary) {
        return new User(UUID.randomUUID(), name, job, salary, List.of());
    }
}
