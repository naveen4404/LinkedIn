package com.linkedin.linkedin.features.feed.repository;

import com.linkedin.linkedin.features.feed.dto.PostDto;
import com.linkedin.linkedin.features.feed.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from posts p where p.author.id <> :userId")
    List<Post> findPostsNotByUser(@Param("userId") Long id);
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findByAuthorId(Long id);
}
