package bri4ki.model.repository;

import bri4ki.model.pojo.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    public Car findByRegister(String register);
    public List<Car> findAllByOwner_Id(Integer ownerId);
    public List<Car> findAllByOwner_IdNotNull();
    public List<Car> findAllByOwner_IdNull();

}
