package com.example.demo.repository;

import com.example.demo.dto.CommentDto;
import com.example.demo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // <-- 要加這個
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT new com.example.demo.dto.CommentDto(" +
            "c.id, c.post.id, c.user.id, c.content, c.createdAt, u.username) " +
            "FROM Comment c JOIN c.user u WHERE c.post.id = :postId ORDER BY c.createdAt ASC")
    List<CommentDto> findAllByPostIdWithUserInfo(@Param("postId") Long postId);
    List<Comment> findByUserId(Long userId);
    List<Comment> findByPostId(Long postId);
}