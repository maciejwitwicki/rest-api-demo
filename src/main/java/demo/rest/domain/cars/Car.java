package demo.rest.domain.cars;

import lombok.Builder;
import lombok.With;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@With
@Builder(toBuilder = true)
public record Car(
        @Id
        String id,
        @NotBlank(message = "registration is mandatory")
        String registration,
        @NotNull(message = "Model is mandatory")
        CarModel model) {

    public static Car create(String registration, CarModel model) {
        return new Car(UUID.randomUUID().toString(), registration, model);
    }

}
