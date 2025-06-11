//package com.example.demo.controller;
//
//import com.example.demo.entity.Pet;
//import com.example.demo.repository.PetRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@RestController
//@RequestMapping("/api/pets")
//public class PetController {
//
//    @Autowired
//    private PetRepository petRepository;
//
//    // Directory where uploaded files will be saved
//    private static final String UPLOAD_DIR = "uploads/";
//
//    @PostMapping
//    public ResponseEntity<?> createPet(
//            @RequestParam String name,
//            @RequestParam Integer age,
//            @RequestParam String gender,
//            @RequestParam String breed,
//            @RequestParam String size,
//            @RequestParam String health,
//            @RequestParam Boolean vaccinated,
//            @RequestParam Boolean neutered,
//            @RequestParam(required = false) String description,
//            @RequestParam("image") MultipartFile image
//    ) {
//        String imagePath = null;
//        if (image != null && !image.isEmpty()) {
//            try {
//                // Ensure the upload directory exists
//                File uploadDir = new File(UPLOAD_DIR);
//                if (!uploadDir.exists()) {
//                    uploadDir.mkdirs();
//                }
//                // Generate a unique file name to avoid collision
//                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
//                Path filePath = Paths.get(UPLOAD_DIR, fileName);
//                Files.write(filePath, image.getBytes());
//                imagePath = "/" + UPLOAD_DIR + fileName;
//            } catch (IOException e) {
//                e.printStackTrace();
//                return ResponseEntity.status(500).body("圖片上傳失敗！");
//            }
//        }
//
//        Pet pet = new Pet();
//        pet.setName(name);
//        pet.setAge(age);
//        pet.setGender(gender);
//        pet.setBreed(breed);
//        pet.setSize(size);
//        pet.setHealth(health);
//        pet.setVaccinated(vaccinated);
//        pet.setNeutered(neutered);
//        pet.setDescription(description);
//        pet.setImage(imagePath);
//
//        petRepository.save(pet);
//
//        return ResponseEntity.ok("寵物新增成功！");
//    }
//}


package com.example.demo.controller;

import com.example.demo.entity.Pet;
import com.example.demo.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetRepository petRepository;

    // Directory where uploaded files will be saved
    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping
    public ResponseEntity<?> createPet(
            @RequestParam String name,
            @RequestParam Integer age,
            @RequestParam String gender,
            @RequestParam String breed,
            @RequestParam String size,
            @RequestParam String health,
            @RequestParam Boolean vaccinated,
            @RequestParam Boolean neutered,
            @RequestParam(required = false) String description,
            @RequestParam("image") MultipartFile image
    ) {
        String imagePath = null;
        if (image != null && !image.isEmpty()) {
            try {
                // Ensure the upload directory exists
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                // Generate a unique file name to avoid collision
                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR, fileName);
                Files.write(filePath, image.getBytes());
                imagePath = "/" + UPLOAD_DIR + fileName;
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("圖片上傳失敗！");
            }
        }

        Pet pet = new Pet();
        pet.setName(name);
        pet.setAge(age);
        pet.setGender(gender);
        pet.setBreed(breed);
        pet.setSize(size);
        pet.setHealth(health);
        pet.setVaccinated(vaccinated);
        pet.setNeutered(neutered);
        pet.setDescription(description);
        pet.setImage(imagePath);

        petRepository.save(pet);

        return ResponseEntity.ok("寵物新增成功！");
    }



}