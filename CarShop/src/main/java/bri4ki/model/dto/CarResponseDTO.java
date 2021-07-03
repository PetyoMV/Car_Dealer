package bri4ki.model.dto;

import bri4ki.model.pojo.Car;
import bri4ki.model.pojo.CarImage;
import bri4ki.model.pojo.Discount;
import bri4ki.model.pojo.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@NoArgsConstructor
public class CarResponseDTO {

    private int id;
    private String model;
    private String color;
    private int year;
    private int km;
    private String register;
    private List<CarImage> carImages;
    private List<User> likers;
    private int price;
    private Discount discount;

    public CarResponseDTO(Car car){
        this.id = car.getId();
        this.model = car.getModel();
        this.color = car.getColor();
        this.year = car.getYear();
        this.register = car.getRegister();
        this.carImages = car.getCarImages();
        this.likers = car.getLikers();
        this.price = car.getPrice();
        this.discount = car.getDiscounts();
    }
}
