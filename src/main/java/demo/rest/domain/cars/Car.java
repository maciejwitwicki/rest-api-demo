package demo.rest.domain.cars;

import lombok.Builder;
import lombok.With;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@With
@Builder(toBuilder = true)
public record Car(
        @NotBlank(message = "registration is mandatory")
        String registration,
        @NotNull(message = "Model is mandatory")
        CarModel model) {
}
