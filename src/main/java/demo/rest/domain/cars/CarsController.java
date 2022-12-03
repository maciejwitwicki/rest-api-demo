package demo.rest.domain.cars;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collection;

import static demo.rest.util.RandomDataGenerator.randomLocalDateTime;
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

    @GetMapping(path = "/{registration}/service-details", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    @ApiOperation(value = "Get next service information for given car", notes = "Requires basic auth")
    public ServiceDetails getServiceDetailsByCar(@PathVariable String registration, @ApiIgnore @RequestHeader(value = "Authorization", required = false) String authorization) {
        if (authorization == null || !authorization.toLowerCase().startsWith("basic")) {
            throw HttpClientErrorException.create(HttpStatus.UNAUTHORIZED, "Unauthorized", HttpHeaders.EMPTY, null, StandardCharsets.UTF_8);
        }
        var decoded = new String(Base64.getDecoder().decode(authorization.substring(6))); //remove 'basic ' prefix
        var split = decoded.split(":");
        if (split[0].equals("user") && split[1].equals("password")) {
            LocalDateTime lastService = randomLocalDateTime();
            return new ServiceDetails("Auto Serwis Karkonoska", lastService, lastService.plusYears(1));
        }

        throw new AccessDeniedException("Forbidden");

        //throw HttpClientErrorException.create(HttpStatus.FORBIDDEN, "Forbidden", HttpHeaders.EMPTY, null, StandardCharsets.UTF_8);
    }
}
