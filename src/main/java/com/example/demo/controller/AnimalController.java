package com.example.demo.controller;

import com.example.demo.entity.Pet;
import com.example.demo.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnimalController {

    @Autowired
    private PetRepository petRepository;

    @GetMapping("/api/animals")
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }
}