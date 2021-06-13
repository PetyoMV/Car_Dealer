package bri4ki.controller;


import bri4ki.model.pojo.CarImage;

import bri4ki.service.CarImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
public class ImageController extends AbstractController{

    @Autowired
    private CarImageService carImageService;

    @PutMapping("/car/{id}/image")
    public CarImage uploadImg(@PathVariable int id, @RequestPart MultipartFile file){
        return carImageService.upload(id,file);
    }



    @GetMapping(value = "/image/{id}", produces="image/*")
    public ResponseEntity downloadImg(@PathVariable int id) {
        return carImageService.download(id);
    }

}
