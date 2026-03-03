package com.elevato.elevato.features.feed.repository;

import com.elevato.elevato.features.feed.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
