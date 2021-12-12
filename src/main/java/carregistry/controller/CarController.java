package carregistry.controller;

import carregistry.dto.CarDTO;
import carregistry.model.Car;
import carregistry.model.Result;
import carregistry.repository.CarRepository;
import carregistry.service.CarService;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/cars")
@Api(tags = "cars")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${CarController.register}")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 422, message = "Username is already in use"), //
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public CarDTO register(@ApiParam("Register car") @RequestBody CarDTO car) {
        return carService.mapToCarDTO(carService.registerCar(carService.mapToCar(car)));
    }

    @GetMapping(value = "/getCarByNumPlate")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${CarController.getCarByNumPlate}", response = CarDTO.class)
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public CarDTO getCarByNumPlate(@ApiParam("Number plate") @RequestParam String numPlate) {
        return carService.mapToCarDTO(carService.getCarByNumPlate(numPlate));
    }

    @GetMapping(value = "/getOwnCars")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public List<CarDTO> getOwnCars(@RequestParam String ownerName) {
        return carService.getCarByOwnerName(ownerName).stream()
                .map(c -> carService.mapToCarDTO(c))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/all")
    public List<CarDTO> getAllRegisteredCars() {
        return carRepository.findAll().stream()
                .map(c -> carService.mapToCarDTO(c))
                .collect(Collectors.toList());
    }

    @PutMapping("/updateCar")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public CarDTO updateCar(@RequestBody CarDTO carDTO) {
        return carService.mapToCarDTO(carService.updateCar(carDTO));
    }

    @DeleteMapping(value = "/{numPlate}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${UserController.delete}")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 404, message = "The user doesn't exist"), //
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public String delete(@ApiParam("Number plate") @PathVariable String numPlate) {
        carService.deleteRegistrationByNumPlate(numPlate);
        return numPlate;
    }
}
