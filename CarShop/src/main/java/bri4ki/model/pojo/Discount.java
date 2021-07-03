package bri4ki.model.pojo;

import bri4ki.model.dto.AddDiscountRequestDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor

@Setter
@Getter
@Entity
@Table(name = "discounts")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @JsonProperty("discount_percent")
    private int discountPercent;
    @JsonProperty("start_at")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate startAt;
    @JsonProperty("end_at")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate endAt;
    @OneToMany(mappedBy = "discounts")
    @JsonBackReference
    private List<Car> cars;


    public Discount(AddDiscountRequestDTO requestDTO) {
        title = requestDTO.getTitle();
        discountPercent = requestDTO.getDiscountPercent();
        startAt = requestDTO.getStartAt();
        endAt = requestDTO.getEndAt();
        cars = new ArrayList<>();
    }
}
