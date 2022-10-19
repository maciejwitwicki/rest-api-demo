package demo.rest.employee;

import demo.rest.exception.NotFoundException;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private Map<UUID, User> users = init();

    @GetMapping
    @ApiOperation(value = "List existing users")
    public Collection<User> getUsers() {
        return users.values();
    }

    @PostMapping
    @ApiOperation(value = "Create new user")
    public User create(@RequestBody User user) {
        var toBeCreated = User.create(user.name(), user.designation(), user.salary());
        users.put(toBeCreated.id(), toBeCreated);
        return toBeCreated;
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Remove user by id")
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
