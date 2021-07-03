package bri4ki.service;

import bri4ki.exceptions.BadRequestException;
import bri4ki.exceptions.NotFoundException;
import bri4ki.model.dto.AddCarRequestDTO;
import bri4ki.model.dto.CarWithoutOwnerDTO;
import bri4ki.model.dto.UserWithoutPasswordDTO;
import bri4ki.model.pojo.Car;
import bri4ki.model.pojo.User;
import bri4ki.model.repository.CarRepository;
import bri4ki.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;


    public CarWithoutOwnerDTO addCar(AddCarRequestDTO carDTO) {
        if(carRepository.findByRegister(carDTO.getRegister()) != null){
            throw new BadRequestException("Register number already exist");
        }
        Car car = new Car(carDTO);
        car = carRepository.save(car);
        return  new CarWithoutOwnerDTO(car);
    }


    public CarWithoutOwnerDTO getById(int id) {
        Optional<Car> car = carRepository.findById(id);
        if(car.isPresent()) {
            return new CarWithoutOwnerDTO(car.get());
        }
        else {
            throw new NotFoundException("car not found");
        }
    }

    public CarWithoutOwnerDTO liked(int id, User user){
        if (carRepository.getById(id) == null){
            throw new NotFoundException("car not found");
        }
        Car car = carRepository.getById(id);
        if (car.getLikers().contains(user)){
            throw new BadRequestException("You already like this car!");
        }
        user.getLikedCars().add(car);
        car.getLikers().add(user);
        user = userRepository.save(user);
        car = carRepository.save(car);
        return new CarWithoutOwnerDTO(carRepository.findById(id).get());
    }

    public List<CarWithoutOwnerDTO> getAllCars() {
        List<Car> cars = carRepository.findAll();
        List<CarWithoutOwnerDTO> response = new ArrayList<>();
        for(Car c : cars){
            response.add(new CarWithoutOwnerDTO(c));
        }
        return response;
    }
}
