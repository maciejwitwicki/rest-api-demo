package demo.rest.domain;

import lombok.Builder;
import lombok.With;

@With
@Builder(toBuilder = true)
record Car(String registration, CarModel model) {
}
