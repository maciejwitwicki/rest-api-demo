package demo.rest.domain;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    @GetMapping(produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    @ApiOperation(value = "List existing users")
    public Collection<User> getUsers(@RequestParam(required = false) CarModel car) {
        return usersService.getUsers(car);
    }

    @GetMapping(path = "{id}/cars", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    @ApiOperation(value = "List cars of user")
    public Collection<Car> getUserCars(@PathVariable String id) {
        return usersService.getUserCars(id);
    }

    @PostMapping(produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE}, consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    @ApiOperation(value = "Create new user")
    public User create(@Valid @RequestBody User user) {
        var toBeCreated = User.create(user.name(), user.designation(), user.salary());
        return usersService.save(toBeCreated);
    }

    @PutMapping(path = "/{id}", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE}, consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    @ApiOperation(value = "Update user by id")
    public User update(@PathVariable String id, @Valid @RequestBody User user) {
        return usersService.updateById(id, user);
    }

    @PatchMapping(path = "/{id}", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE}, consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    @ApiOperation(value = "Update some fields of user by id")
    public User patch(@PathVariable String id, @RequestBody User user) {
        return usersService.patchUpdateById(id, user);
    }

    @DeleteMapping(path = "/{id}", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    @ApiOperation(value = "Remove user by id")
    public User deleteById(@PathVariable String id) {
        return usersService.deleteById(id);
    }
}
