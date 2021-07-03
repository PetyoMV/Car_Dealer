package bri4ki.model.pojo;

import bri4ki.model.dto.AddCarRequestDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String model;
    private String color;
    private int year;
    private int km;
    private String register;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @OneToMany(mappedBy = "car")
    @JsonManagedReference
    private List<CarImage> carImages;
    @ManyToMany(mappedBy = "likedCars")
    @JsonBackReference
    private List<User> likers;
    private int price;
    @ManyToOne()
    @JoinColumn(name = "discount_id")
    @JsonBackReference
    private Discount discounts;
    @JsonProperty("base_price")
    private int basePrice;

    public Car(AddCarRequestDTO carDTO){
        model = carDTO.getModel();
        color = carDTO.getColor();
        year = carDTO.getYear();
        km = carDTO.getKm();
        register = carDTO.getRegister();
        likers = new ArrayList<>();
        price = carDTO.getPrice();
        basePrice = carDTO.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
