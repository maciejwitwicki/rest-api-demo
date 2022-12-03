package demo.rest.domain.users;

import lombok.Builder;
import lombok.With;

import java.util.UUID;

@With
@Builder(toBuilder = true)
record Performance(
        UUID id,
        String type,
        String period,
        double rating,
        String userId
) {

    public static Performance create(String type, String period, double rating, String userId) {
        return new Performance(UUID.randomUUID(), type, period, rating, userId);
    }
}
