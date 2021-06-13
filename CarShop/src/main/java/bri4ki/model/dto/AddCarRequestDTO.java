package bri4ki.model.dto;

import bri4ki.model.pojo.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

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

}
