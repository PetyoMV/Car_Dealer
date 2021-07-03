package bri4ki.service;

import bri4ki.controller.SessionManager;
import bri4ki.exceptions.AuthenticationException;
import bri4ki.exceptions.BadRequestException;
import bri4ki.exceptions.NotFoundException;
import bri4ki.model.dto.*;
import bri4ki.model.pojo.Car;
import bri4ki.model.pojo.User;
import bri4ki.model.repository.CarRepository;
import bri4ki.model.repository.UserRepository;
import bri4ki.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    @Autowired
    private ValidationUtil validationUtil;

    @Transactional
    public RegisterResponseUserDTO addUser(RegisterRequestUserDTO userDTO) {
        validationUtil.checkUser(userDTO);
        if(userRepository.findByEmail(userDTO.getEmail()) != null){
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

    public User edit(EditUserRequestDTO requestDto, User user) {
        validationUtil.checkUser(requestDto);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(requestDto.getOldPassword(),user.getPassword())){
            throw new AuthenticationException("Wrong credentials");
        }else {
            if (requestDto.getNewPassword() != null){
                user.setPassword(encoder.encode(requestDto.getNewPassword()));
            }
            user.setUsername(requestDto.getUsername());
            if(!requestDto.getEmail().equals(user.getEmail())){
                if (userRepository.findByEmail(requestDto.getEmail()) != null) {
                    throw new BadRequestException("Email already exists");
                }
            }
            user.setEmail(requestDto.getEmail());
            user.setAddress(requestDto.getAddress());
            user.setPhone(requestDto.getPhone());
            userRepository.save(user);
            return user;
        }
    }

    public void delete(PasswordRequestDTO passwordDTO, User user) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(passwordDTO.getPassword(),user.getPassword())) {
            userRepository.delete(user);
        }else {
            throw new BadRequestException("Wrong password!");
        }
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

    public User login(LoginUserDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail());
        if (user == null){
            throw new AuthenticationException("Wrong credentials");
        }else {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            if (!encoder.matches(dto.getPassword(), user.getPassword())){
                throw  new AuthenticationException("Wrong credentials");
            }else {
                return user;
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

//    public List<User> getAllUserNotDto() {
//        return userRepository.getAllUsers();
//    }
//    public User getById(int x){
//        return userRepository.getById(x);
//    }
}
