package demo.rest.domain;

import lombok.Builder;
import lombok.With;

import javax.validation.constraints.Min;
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
        @NotBlank(message = "Designation is mandatory")
        String designation,
        @Positive(message = "Salary can't be negative")
        Double salary,
        List<Car> cars) {
    public static User create(String name, String role, double salary) {
        return new User(UUID.randomUUID(), name, role, salary, List.of());
    }
}
