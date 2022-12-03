package demo.rest.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayDeque;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataGenerator {
    private static final ArrayDeque<String> registrations = new ArrayDeque<>();
    static {
        registrations.addAll(List.of(
                "DW 123AZ",
                "DL 99XX8",
                "DK 22LA2",
                "WA 741O5",
                "DJ 589R3",
                "DW 22TR3",
                "DL 65SD2",
                "DB 57MN1",
                "WA 110GA",
                "DJ 5441Z",
                "FZ 323NP",
                "ZG 58R31",
                "DL 7422D",
                "DK 441UF",
                "WA 27IUA",
                "DW 821GL",
                "DW 12A3M",
                "DR 4B56N",
                "WA 99C9O",
                "DW 78D9P",
                "DR 1E47S",
                "DR 9F63R",
                "DW 75G3T",
                "WA 35H7U",
                "DJ 95I1V",
                "DR 99J9W",
                "DW 88K8X",
                "DJ 85L2Y",
                "WA 25M8Z"
        ));
    }

    public static String uniqueRegistration() {
        return registrations.pop();
    }
}
