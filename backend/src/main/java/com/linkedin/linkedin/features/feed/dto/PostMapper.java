package com.linkedin.linkedin.features.feed.dto;

import com.linkedin.linkedin.features.authentication.model.AuthenticationUser;
import com.linkedin.linkedin.features.feed.model.Post;

public class PostMapper {

    public static Post toEntity(PostDto dto, AuthenticationUser user){
        return new Post(dto.getContent(), dto.getPicture(), user);
    }

    public  static  PostDto toDto(Post post){
        return new PostDto(post.getId(), post.getContent(), post.getPicture(),post.getCreatedAt(),post.getUpdatedAt());
    }

}
