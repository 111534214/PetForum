package com.example.demo.service;

import com.example.demo.entity.Like;
import com.example.demo.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
//public class LikeService {
//    @Autowired
//    private LikeRepository likeRepository;
//    public List<Like> findByUserId(Long userId) {
//        return likeRepository.findByUserId(userId);
//    }
//
//    // 其他按讚相關方法...
//}


@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepo;

    public List<Like> findByUserId(Long userId) {
        return likeRepo.findByUserId(userId);
    }


}
