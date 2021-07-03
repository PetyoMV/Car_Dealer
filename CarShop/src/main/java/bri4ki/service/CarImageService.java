package bri4ki.service;

import bri4ki.exceptions.NotFoundException;
import bri4ki.model.dto.CarWithoutOwnerDTO;
import bri4ki.model.pojo.Car;
import bri4ki.model.pojo.CarImage;
import bri4ki.model.repository.CarImageRepository;
import bri4ki.model.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class CarImageService {

    private static final String IMG_DIR = "C:\\Users\\ASUS\\Documents\\img";


    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarImageRepository carImageRepository;

    public ResponseEntity download(int id) {
        CarImage carImage = getById(id);
        Path path = Paths.get(carImage.getUrl());
        Resource resource = null;
        try {
            resource =  new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
           throw new NotFoundException(e.getMessage());
        }
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource
                        .getFilename() + "\"")
                .body(resource);
    }

    public CarImage getById(int id) {
        Optional<CarImage> car = carImageRepository.findById(id);
        if (!car.isPresent()){
            throw  new NotFoundException("Car image - id, not found");
        }else {
            return car.get();
        }
    }

    public CarImage upload(@PathVariable int id, @RequestPart MultipartFile file){
        Optional<Car> optionalCar = carRepository.findById(id);
        if(!optionalCar.isPresent()){
            throw new NotFoundException("Car not found");
        }
        File pFile = new File(IMG_DIR + File.separator + id+"_"+System.nanoTime()+".png");
        try (OutputStream os = new FileOutputStream(pFile)){
            os.write(file.getBytes());
            CarImage carImage = new CarImage();
            carImage.setUrl(pFile.getAbsolutePath());
            carImage.setCar(carRepository.findById(id).get());
            carImageRepository.save(carImage);
            return carImage;
        } catch (FileNotFoundException e) {
            throw new bri4ki.exceptions.FileNotFoundException("File not found");
        } catch (IOException e) {
            throw new bri4ki.exceptions.FileNotFoundException("File not found or path is forbidden");
        }
    }

}
