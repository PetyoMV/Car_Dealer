package bri4ki.model.repository;

import bri4ki.model.pojo.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount,Integer> {

    Discount getById(Integer id);
    Discount findByDiscountPercent(Integer percent);

}
