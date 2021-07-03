package bri4ki.model.dto;

import bri4ki.model.pojo.Discount;
import bri4ki.util.ValidationUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;


@NoArgsConstructor
@Getter
@Setter
@Component
@AllArgsConstructor
public class AddDiscountRequestDTO {

    private int id;
    @NotBlank(message = "Title" + ValidationUtil.NOR_NULL_OR_EMPTY)
    private String title;
    @JsonProperty("discount_percent")
    @Min(value = 0, message = "Discount Percent" + ValidationUtil.POSITIVE_NUMBER)
    private int discountPercent;
    @JsonProperty("start_at")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate startAt;
    @JsonProperty("end_at")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate endAt;


    public AddDiscountRequestDTO(Discount discount) {
        id = discount.getId();
        title = discount.getTitle();
        discountPercent = discount.getDiscountPercent();
        startAt = discount.getStartAt();
        endAt = discount.getEndAt();
    }
}
