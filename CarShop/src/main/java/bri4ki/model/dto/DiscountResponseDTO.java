package bri4ki.model.dto;

import bri4ki.model.pojo.Car;
import bri4ki.model.pojo.Discount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountResponseDTO {

    private int id;
    private String title;
    private int discountPercent;
    private LocalDate startAt;
    private LocalDate endAt;
    private List<CarWithoutOwnerDTO> cars;

    public DiscountResponseDTO(Discount discount){
        this.id = discount.getId();
        this.title = discount.getTitle();
        this.discountPercent = discount.getDiscountPercent();
        this.startAt = discount.getStartAt();
        this.endAt = discount.getEndAt();
        cars = new ArrayList<>();
        for (Car c : discount.getCars()) {
            this.cars.add(new CarWithoutOwnerDTO(c));
        }
    }
}
