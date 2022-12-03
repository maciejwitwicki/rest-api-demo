package demo.rest.util;

import demo.rest.domain.cars.CarModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static java.time.ZoneOffset.UTC;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomDataGenerator {

    private static final Random random = new Random();
    private static final List<String> lastNames = List.of("Kowalski", "Nowak", "Komorowski", "Kwasniewski", "Walesa", "Duda");
    private static final List<String> firstNames = List.of("Jan", "Tadeusz", "Ignacy", "Adam", "Stanislaw", "Juliusz", "Mateusz", "Piotr", "Pawel", "Stefan", "Marian", "Filip");
    private static final List<String> jobs = List.of("Carpenter", "Driver", "Accountant", "Developer", "Cook", "Chef", "Analyst", "Manager");

    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    public static double randomDouble() {
        return random.nextDouble(100.00, 500.000);
    }

    public static double randomDouble(double bound) {
        return random.nextDouble(1, bound);
    }

    public static int randomInt(int range) {
        return random.nextInt(range);
    }

    public static <T> T randomItem(List<T> items) {
        return items.get(randomInt(items.size()));
    }

    public static String randomName() {
        var lnInd = random.nextInt(lastNames.size());
        var fnInd = random.nextInt(firstNames.size());
        return "%s %s".formatted(firstNames.get(fnInd), lastNames.get(lnInd));
    }

    public static String randomJob() {
        var ind = random.nextInt(jobs.size());
        return jobs.get(ind);
    }

    public static LocalDateTime randomLocalDateTime() {
        var startSecs = Instant.parse("2000-01-01T12:00:00.000Z").getEpochSecond();
        var endSecs = Instant.now().getEpochSecond();
        var randomSec = random.nextLong(startSecs, endSecs);
        var instant = Instant.ofEpochSecond(randomSec);
        return LocalDateTime.ofInstant(instant, UTC);
    }

    public static CarModel randomCarModel() {
        var models = Arrays.stream(CarModel.values()).toList();
        return models.get(randomInt(models.size()));
    }
}
