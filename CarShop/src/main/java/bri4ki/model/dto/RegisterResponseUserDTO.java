package bri4ki.model.dto;

import bri4ki.model.pojo.Car;
import bri4ki.model.pojo.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Component
public class RegisterResponseUserDTO {

    private int id;
    private String username;
    private String email;
    private String phone;
    private String address;
    List<Car> cars;

    public RegisterResponseUserDTO(User user){
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
        phone = user.getPhone();
        address = user.getAddress();
        cars = user.getCars();
    }
}
