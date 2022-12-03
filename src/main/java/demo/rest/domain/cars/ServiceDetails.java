package demo.rest.domain.cars;

import java.time.LocalDateTime;

public record ServiceDetails(String garage, LocalDateTime lastService, LocalDateTime nextService) {
}
