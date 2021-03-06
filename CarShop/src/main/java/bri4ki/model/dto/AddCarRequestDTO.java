package bri4ki.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;



@NoArgsConstructor
@Getter
@Setter
@Component
public class AddCarRequestDTO {

    private String model;
    private String color;
    private int year;
    private int km;
    private String register;
    private int price;

}
