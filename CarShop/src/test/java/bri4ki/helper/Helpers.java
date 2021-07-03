package bri4ki.helper;

import bri4ki.model.pojo.Discount;
import bri4ki.model.pojo.User;

import java.time.LocalDate;
import java.util.Optional;


public class Helpers {

    public static Discount createMockDiscount(){
        Discount mockDiscount = new Discount();
        mockDiscount.setId(1);
        mockDiscount.setTitle("15% discount");
        mockDiscount.setDiscountPercent(15);
        mockDiscount.setStartAt(LocalDate.parse("2021-06-30"));
        mockDiscount.setEndAt(LocalDate.parse("2021-07-01"));
        return mockDiscount;
    }

}
