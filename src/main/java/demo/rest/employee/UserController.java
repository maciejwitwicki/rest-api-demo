package demo.rest.employee;

import demo.rest.exception.NotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController("/employees")
public class UserController {

    private Map<UUID, User> users = init();

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody User e) {
        var toBeCreated = User.create(e.name(), e.designation(), e.salary());
        users.put(toBeCreated.id(), toBeCreated);
        return toBeCreated;
    }

    @DeleteMapping("/{id}")
    public User deleteById(@PathVariable String id) {
        var found = users.get(UUID.fromString(id));
        if (found == null) {
            throw new NotFoundException();
        }
        return users.remove(found.id());
    }

    private Map<UUID, User> init() {
        var kowalski = User.create("Kowalski", "cook", 100_000.50);
        var nowak = User.create("Nowak", "chef", 200_050.80);
        var drzyzga = User.create("Drzyzga", "waiter", 80_190.00);
        return new HashMap<>(Map.of(kowalski.id(), kowalski, nowak.id(), nowak, drzyzga.id(), drzyzga));
    }

}
