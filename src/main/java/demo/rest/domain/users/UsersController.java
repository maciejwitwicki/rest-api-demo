package demo.rest.domain.users;

import demo.rest.domain.cars.Car;
import demo.rest.domain.cars.CarModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

import static demo.rest.security.SecurityRoles.PERFORMANCE_ADMIN;
import static demo.rest.security.SecurityRoles.PERFORMANCE_VIEWER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UsersController {

    private final UsersService usersService;
    private final PerformanceService performanceService;

    @GetMapping(produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    @ApiOperation(value = "List existing users")
    public Collection<User> getUsers(@RequestParam(required = false) CarModel car) {
        return usersService.getUsers(car);
    }

    @GetMapping(path = "{id}/cars")
    @ApiOperation(value = "List cars of user")
    public Collection<Car> getUserCars(@PathVariable String id) {
        return usersService.getUserCars(id);
    }

    @PostMapping
    @ApiOperation(value = "Create new user")
    public User create(@Valid @RequestBody User user) {
        return usersService.save(user);
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Update user by id")
    public User update(@PathVariable String id, @Valid @RequestBody User user) {
        return usersService.updateById(id, user);
    }

    @PatchMapping(path = "/{id}")
    @ApiOperation(value = "Update some fields of user by id")
    public User patch(@PathVariable String id, @RequestBody User user) {
        return usersService.patchUpdateById(id, user);
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Remove user by id")
    public User deleteById(@PathVariable String id) {
        return usersService.deleteById(id);
    }

    @RolesAllowed(PERFORMANCE_ADMIN)
    @PostMapping(path = "/{id}/performances/driving-ratings")
    @ApiOperation(value = "Set user's performance in driving")
    public Performance createDrivingPerformance(@PathVariable("id") String userId, @RequestBody PerformanceRequest request) {
        usersService.getById(userId);
        return performanceService.createPerformance("driving", userId, request.period(), request.rating());
    }

    @RolesAllowed(PERFORMANCE_ADMIN)
    @PostMapping(path = "/{id}/performances/navigation-ratings")
    @ApiOperation(value = "Set user's performance in navigation")
    public Performance createNavigationPerformance(@PathVariable("id") String userId, @RequestBody PerformanceRequest request) {
        usersService.getById(userId);
        return performanceService.createPerformance("navigation", userId, request.period(), request.rating());
    }

    @RolesAllowed({PERFORMANCE_ADMIN, PERFORMANCE_VIEWER})
    @GetMapping(path = "/{id}/performances/driving-ratings")
    @ApiOperation(value = "Get user's performance in navigation")
    @Authorization("performance_manager")
    public List<Performance> getDrivingPerformance(@PathVariable("id") String userId) {
        usersService.getById(userId);
        return performanceService.getPerformances(userId, "driving");
    }

    @RolesAllowed({PERFORMANCE_ADMIN, PERFORMANCE_VIEWER})
    @GetMapping(path = "/{id}/performances/navigation-ratings")
    @ApiOperation(value = "Get user's performance in navigation")
    @Authorization("performance_manager")
    public List<Performance> getNavigationPerformance(@PathVariable("id") String userId) {
        usersService.getById(userId);
        return performanceService.getPerformances(userId, "navigation");
    }

}
