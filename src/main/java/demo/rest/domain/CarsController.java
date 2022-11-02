package demo.rest.domain;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarsController {

    private final CarsService carsService;

    @GetMapping(produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    @ApiOperation(value = "List existing cars")
    public Collection<Car> getCars(@RequestParam(required = false) CarModel carModel) {
        return carsService.getCars(carModel);
    }

    @PostMapping(produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE}, consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    @ApiOperation(value = "Create new car")
    public Car create(@RequestBody Car car) {
        return carsService.save(car);
    }

    @PutMapping(path = "/{registration}", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE}, consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    @ApiOperation(value = "Update car by registration")
    public Car update(@PathVariable String registration, @RequestBody Car car) {
        return carsService.updateById(registration, car);
    }

    @PatchMapping(path = "/{registration}", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE}, consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    @ApiOperation(value = "Update some fields of car by registration")
    public Car patch(@PathVariable String registration, @RequestBody Car car) {
        return carsService.patchUpdateByRegistration(registration, car);
    }

    @DeleteMapping(path = "/{registration}", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    @ApiOperation(value = "Remove car by registration")
    public Car deleteById(@PathVariable String registration) {
        return carsService.deleteByRegistration(registration);
    }
}
