package carregistry.service;

import carregistry.dto.CarDTO;
import carregistry.exception.CustomException;
import carregistry.model.Car;
import carregistry.model.User;
import carregistry.repository.CarRepository;
import carregistry.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CarService carService;

    public Car registerCar(Car car) {
        if (!carRepository.existsByNumPlate(car.getNumPlate())) {
            carRepository.save(car);
            return car;
        } else {
            throw new CustomException("Car is already registered", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public List<Car> getCarByOwnerName(String ownerName) {
        return this.carRepository.findByOwner_Username(ownerName);
    }

    public Car getCarByNumPlate(String numPlate) {
        if (carRepository.existsByNumPlate(numPlate)) {
            return carRepository.findByNumPlate(numPlate);
        } else {
            throw new CustomException("Car doesn't exist", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void deleteRegistrationByNumPlate(String numPlate) {
        if (carRepository.existsByNumPlate(numPlate)) {
            carRepository.deleteByNumPlate(numPlate);
        } else {
            throw new CustomException("Car doesn't exist", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public Car updateCar(CarDTO carDTO) {
        Car car = carRepository.findByNumPlate(carDTO.getNumPlate());

        car.setName(carDTO.getName());
        car.setType(carDTO.getType());
        car.setWeight(carDTO.getWeight());
        car.setNumPlate(carDTO.getNumPlate());
        car.setOwner(userRepository.findByUsername(carDTO.getOwnerName()));

        return carRepository.save(car);
    }

    public Car mapToCar(CarDTO carDTO) {
        if (userRepository.existsByUsername(carDTO.getOwnerName())) {
            User user = userRepository.findByUsername(carDTO.getOwnerName());

            return Car.builder()
                    .name(carDTO.getName())
                    .numPlate(carDTO.getNumPlate())
                    .type(carDTO.getType())
                    .owner(user)
                    .weight(carDTO.getWeight())
                    .build();
        } else {
            throw new CustomException("User doesn't exist", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public CarDTO mapToCarDTO(Car car) {
        if (userRepository.existsByUsername(car.getOwner().getUsername())) {
            return CarDTO.builder()
                    .name(car.getName())
                    .numPlate(car.getNumPlate())
                    .type(car.getType())
                    .ownerName(car.getOwner().getUsername())
                    .weight(car.getWeight())
                    .build();
        } else {
            throw new CustomException("User doesn't exist", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
