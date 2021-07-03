package bri4ki.controller;

import bri4ki.model.dto.AddDiscountRequestDTO;
import bri4ki.model.dto.DiscountResponseDTO;
import bri4ki.model.dto.MessageResponseDTO;
import bri4ki.model.pojo.Discount;
import bri4ki.model.pojo.User;
import bri4ki.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DiscountController extends AbstractController{

    @Autowired
    private DiscountService discountService;

    @PutMapping("/discounts")
    public DiscountResponseDTO addDiscount(@Valid @RequestBody AddDiscountRequestDTO discountDTO, HttpSession session){
        User user = sessionManager.getLoggedUser(session);
        adminProtection(user);
        Discount discount = discountService.addDiscount(discountDTO);
        return new DiscountResponseDTO(discount);
    }


    @PutMapping("/car/{car_id}/discount/{discount_id}")
    public DiscountResponseDTO putDiscount(@PathVariable(name = "car_id")int carId, @PathVariable(name = "discount_id")int discount_id, HttpSession session){
        User user = sessionManager.getLoggedUser(session);
        adminProtection(user);
        return discountService.putDiscount(carId,discount_id);
    }

    @DeleteMapping("/discount/{id}")
    public MessageResponseDTO deleteDiscount(@PathVariable int id, HttpSession session){
        User user = sessionManager.getLoggedUser(session);
        adminProtection(user);
        discountService.deleteDiscount(id);
        return new MessageResponseDTO("Delete discount successful");
    }

    @GetMapping("/discount/{id}")
    public DiscountResponseDTO getDiscount(@PathVariable int id){
        Discount discount = discountService.getDiscount(id);
        return new DiscountResponseDTO(discount);
    }

    @PostMapping("/discount/{id}")
    public DiscountResponseDTO editDiscount(@PathVariable int id, @RequestBody AddDiscountRequestDTO discountDTO, HttpSession session){
        User user = sessionManager.getLoggedUser(session);
        adminProtection(user);
        Discount discount = discountService.edit(discountDTO, id);
        return new DiscountResponseDTO(discount);
    }

    @GetMapping("/discounts")
    public List<DiscountResponseDTO> getAllDiscounts(){
        List<Discount> discountList = discountService.getAll();
        List<DiscountResponseDTO> responseList = new ArrayList<>();
        for (Discount discount : discountList) {
            responseList.add(new DiscountResponseDTO(discount));
        }
        return responseList;
    }


}
