package bri4ki.service;

import bri4ki.exceptions.BadRequestException;
import bri4ki.exceptions.NotFoundException;
import bri4ki.model.dto.AddDiscountRequestDTO;
import bri4ki.model.dto.DiscountResponseDTO;
import bri4ki.model.dto.UserWithoutPasswordDTO;
import bri4ki.model.pojo.Car;
import bri4ki.model.pojo.Discount;
import bri4ki.model.pojo.User;
import bri4ki.model.repository.CarRepository;
import bri4ki.model.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CarRepository carRepository;




    @Transactional
    public Discount addDiscount(AddDiscountRequestDTO discountDTO) {
        LocalDate start = discountDTO.getStartAt();
        LocalDate end = discountDTO.getEndAt();
        if (end.isBefore(start)){
            throw new BadRequestException("Start date must be before end date!");
        }
        if (discountDTO.getDiscountPercent() < 0){
            throw  new BadRequestException("Percentage of the discount must be more than 0!");
        }
        if(discountRepository.findByDiscountPercent(discountDTO.getDiscountPercent()) != null){
            throw new BadRequestException("discount percent already exists");
        }
        Discount discount = new Discount(discountDTO);
//        Thread emailGenerator = new Thread(()->{
//            emailService.sendMessage(discount);
//        });
//        emailGenerator.start();

        return discountRepository.save(discount);
    }


    public DiscountResponseDTO putDiscount(int carId, int discount_id) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        Optional<Discount> optionalDiscount = discountRepository.findById(discount_id);

        if(!optionalCar.isPresent()){
            throw new NotFoundException("Car not found");
        }
        if(!optionalDiscount.isPresent()){
            throw new NotFoundException("discount not found");
        }
        Car car = optionalCar.get();
        Discount discount = optionalDiscount.get();
        if(car.getDiscounts() != null){
            throw new BadRequestException("Car already reduced");
        }
        car.setDiscounts(discount);
        int carDiscount = (carRepository.getById(carId).getPrice() * discountRepository.getById(discount_id).getDiscountPercent())/100;
        int carP = carRepository.getById(carId).getPrice() - carDiscount;
        carRepository.getById(carId).setPrice(carP);
        carRepository.save(car);

        return new DiscountResponseDTO(discountRepository.findById(discount_id).get());
    }

    @Transactional
    public void deleteDiscount(int id){
        if(!discountRepository.findById(id).isPresent()){
            throw new NotFoundException("Discount not exist");
        }
        Discount discount = discountRepository.getById(id);
        for (Car c : discount.getCars()) {
            carRepository.getById(c.getId()).setDiscounts(null);
            carRepository.getById(c.getId()).setPrice(c.getBasePrice());
           carRepository.save(c);
        }
        discountRepository.delete(discount);
    }

    public Discount getDiscount(int id) {
        Optional<Discount> discount = discountRepository.findById(id);
        if (discount.isPresent()){
            return discount.get();
        }else {
            throw new NotFoundException("Discount not found!");
        }
    }

    public Discount edit(AddDiscountRequestDTO requestDiscount, int id) {
        Discount discount = getDiscount(id);
        LocalDate start = requestDiscount.getStartAt();
        LocalDate end = requestDiscount.getEndAt();
        if (end.isBefore(start)){
            throw new BadRequestException("Start date must be before end date!");
        }
        if (requestDiscount.getDiscountPercent() < 0){
            throw  new BadRequestException("Percentage of the discount must be more than 0!");
        }
        discount.setTitle(requestDiscount.getTitle());
        discount.setDiscountPercent(requestDiscount.getDiscountPercent());
        discount.setStartAt(requestDiscount.getStartAt());
        discount.setEndAt(requestDiscount.getEndAt());
        discountRepository.save(discount);
        return  discount;
    }

    public List<Discount> getAll() {
        return discountRepository.findAll();
    }
}
