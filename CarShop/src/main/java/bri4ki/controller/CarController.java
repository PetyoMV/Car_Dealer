package bri4ki.controller;

import bri4ki.exceptions.AuthenticationException;
import bri4ki.exceptions.BadRequestException;
import bri4ki.model.dto.AddCarRequestDTO;
import bri4ki.model.dto.CarWithoutOwnerDTO;
import bri4ki.model.pojo.Car;
import bri4ki.model.pojo.User;
import bri4ki.model.repository.UserRepository;
import bri4ki.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class CarController extends AbstractController{

    @Autowired
    private CarService carService;
    @Autowired
    private UserRepository userRepository;

    @PutMapping("/car")
    public CarWithoutOwnerDTO add(@RequestBody AddCarRequestDTO carDTO){
        return carService.addCar(carDTO);
    }
    
    @GetMapping("/car/{id}")
    public CarWithoutOwnerDTO getCarById(@PathVariable int id){
        return carService.getById(id);
    }

    @PostMapping("/car/{id}")
    public CarWithoutOwnerDTO like(@PathVariable int id, HttpSession session){
        carService.getById(id);
        User u;
        if(session.getAttribute("LoggedUser") == null){
            throw new AuthenticationException("You have to be logged in!");
        }else {
            int loggedId = (int) session.getAttribute("LoggedUser");
             u = userRepository.findById(loggedId).get();
            if(u.getLikedCars().contains(carService.getById(id))){
                throw new BadRequestException("Car already liked");
            }
            else {
              return carService.liked(id,u);
            }
        }
    }

}
