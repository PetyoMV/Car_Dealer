package bri4ki.model.dto;

import bri4ki.model.pojo.Car;
import bri4ki.model.pojo.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
@Setter
@Getter
public class UserWithoutPasswordDTO {

    private int id;
    private String username;
    private String email;
    private String phone;
    private String address;
    List<CarWithoutOwnerDTO> cars;
    List<CarWithoutOwnerDTO> likedCars;

    public UserWithoutPasswordDTO(User u){
        id = u.getId();
        username = u.getUsername();
        email = u.getEmail();
        phone = u.getPhone();
        address = u.getAddress();
        cars = new ArrayList<>();
        for(Car c : u.getCars()){
            cars.add(new CarWithoutOwnerDTO(c));
        }
        likedCars = new ArrayList<>();
        for(Car c : u.getLikedCars()){
            likedCars.add(new CarWithoutOwnerDTO(c));
        }
    }

}
