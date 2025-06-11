package com.example.demo.service;

import com.example.demo.entity.Favorite;
import com.example.demo.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
//public class FavoriteService {
//    @Autowired
//    private FavoriteRepository favoriteRepository;
//
//    public List<Favorite> findByUserId(Long userId) {
//        return favoriteRepository.findByUserId(userId);
//    }
//}


@Service
public class FavoriteService {
    @Autowired
    private FavoriteRepository favRepo;

    public List<Favorite> findByUserId(Long userId) {
        return favRepo.findByUserId(userId);
    }
}