package bri4ki.service;

import bri4ki.controller.SessionManager;
import bri4ki.exceptions.AuthenticationException;
import bri4ki.exceptions.BadRequestException;
import bri4ki.exceptions.NotFoundException;
import bri4ki.model.dto.LoginUserDTO;
import bri4ki.model.dto.RegisterRequestUserDTO;
import bri4ki.model.dto.RegisterResponseUserDTO;
import bri4ki.model.dto.UserWithoutPasswordDTO;
import bri4ki.model.pojo.Car;
import bri4ki.model.pojo.User;
import bri4ki.model.repository.CarRepository;
import bri4ki.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    //userRepo is interface(but with autowire -> singleton implementation on it)
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;



    public RegisterResponseUserDTO addUser(RegisterRequestUserDTO userDTO) {
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new BadRequestException("The 'confirm password' - confirmation does not match");
        }
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new BadRequestException("Email already exists");
        }
        //before create user hash pass
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));

        User user = new User(userDTO);
        //serialize into db
        user = userRepository.save(user);
        //now user has generated id
        //return dto from the obj. with generated id
        return new RegisterResponseUserDTO(user);

    }

    public List<UserWithoutPasswordDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserWithoutPasswordDTO> respUsers = new ArrayList<>();
        for (User u : users) {
            respUsers.add(new UserWithoutPasswordDTO(u));
        }
        return respUsers;
    }

    public UserWithoutPasswordDTO getUserById(int id) {
        Optional<User> hasOrNotUser = userRepository.findById(id);
        if (hasOrNotUser.isPresent()) {
            return new UserWithoutPasswordDTO(hasOrNotUser.get());
        } else {
            throw new NotFoundException("User not found");
        }
    }

    public UserWithoutPasswordDTO login(LoginUserDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail());
        if (user == null) {
            throw new AuthenticationException("Wrong credentials");
        } else {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(dto.getPassword(), user.getPassword())) {
                return new UserWithoutPasswordDTO(user);
            } else {
                throw new AuthenticationException("Wrong credentials");
            }
        }
    }

    public UserWithoutPasswordDTO buyCar(int userId, int carId) {

        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Car> optionalCar = carRepository.findById(carId);

        if (!optionalUser.isPresent()) {
            throw new NotFoundException("User not found");
        }
        if (!optionalCar.isPresent()) {
            throw new NotFoundException("Car not found");
        }

        Car car = optionalCar.get();
        User user = optionalUser.get();

        if (car.getOwner() != null) {
            throw new BadRequestException("Car already bought");
        }
        car.setOwner(user);
        carRepository.save(car); //update
        //add new car in user cars
        return new UserWithoutPasswordDTO(userRepository.findById(userId).get());
    }
}
