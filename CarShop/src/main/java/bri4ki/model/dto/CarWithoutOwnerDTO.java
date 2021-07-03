package bri4ki.model.dto;

import bri4ki.model.pojo.Car;
import bri4ki.model.pojo.CarImage;
import bri4ki.model.pojo.Discount;
import bri4ki.model.pojo.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Component
public class CarWithoutOwnerDTO {


    private int id;
    private String model;
    private String color;
    private int year;
    private int km;
    private String register;
    private List<CarImage> carImages;
    private int likers;
    private int price;
    private Discount discount;


    public CarWithoutOwnerDTO(Car car){
        id = car.getId();
        model = car.getModel();
        color = car.getColor();
        year = car.getYear();
        km = car.getKm();
        register = car.getRegister();
        carImages = car.getCarImages();
        likers = car.getLikers().size();
        price = car.getPrice();
        discount = car.getDiscounts();

    }

}
