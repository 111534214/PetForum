package com.example.demo.service;

import com.example.demo.entity.News;
import com.example.demo.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public void save(News news) {
        newsRepository.save(news);
    }

    public List<News> findAll() {
        return newsRepository.findAll();
    }

    public News findById(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    public List<News> findByCategory(String category) {
        return newsRepository.findByCategoryOrderByPublishDateDesc(category);
    }

    public List<News> findFeatured() {
        return newsRepository.findByIsFeaturedTrueOrderByPublishDateDesc();
    }

    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }
}