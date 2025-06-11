package com.example.demo.repository;

import java.util.List;
import com.example.demo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategory(String category);
    List<Post> findByUserId(Long userId);
    Optional<Post> findById(Long id);

    List<Post> findByCategory(String category, Sort sort);  // 支援排序

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.user WHERE p.category = :category")
    List<Post> findByCategoryWithUser(@Param("category") String category);

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.user WHERE p.category = :category ORDER BY " +
            "CASE WHEN :sort = 'likes' THEN p.likes END DESC, " +
            "CASE WHEN :sort = 'comments' THEN p.commentCount END DESC, " +
            "CASE WHEN :sort = 'newest' THEN p.createdAt END DESC")
    List<Post> findByCategoryWithUserSorted(@Param("category") String category, @Param("sort") String sort);

    @Query("SELECT p FROM Post p WHERE p.title LIKE :keyword OR p.content LIKE :keyword")
    List<Post> searchPostsByKeyword(@Param("keyword") String keyword);

//    List<Post> findByCategoryOrderByCreatedAtDesc(String category);
//    List<Post> findByCategoryOrderByLikesDesc(String category);
//    List<Post> findByCategoryOrderByCommentsDesc(String category); // comments 欄位必須存在！

    List<Post> findByCategoryOrderByCreatedAtDesc(String category);
    List<Post> findByCategoryOrderByLikesDesc(String category);


}

