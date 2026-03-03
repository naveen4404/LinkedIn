package com.elevato.elevato.features.feed.repository;

import com.elevato.elevato.features.feed.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from posts p where p.author.id <> :userId")
    List<Post> findPostsNotByUser(@Param("userId") Long id);
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findByAuthorId(Long id);
}
