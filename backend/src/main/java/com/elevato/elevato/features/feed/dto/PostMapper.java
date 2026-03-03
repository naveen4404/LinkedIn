package com.elevato.elevato.features.feed.dto;

import com.elevato.elevato.features.authentication.model.AuthenticationUser;
import com.elevato.elevato.features.feed.model.Post;

public class PostMapper {

    public static Post toEntity(PostDto dto, AuthenticationUser user){
        return new Post(dto.getContent(), dto.getPicture(), user);
    }

    public  static  PostDto toDto(Post post){
        return new PostDto(post.getId(), post.getContent(), post.getPicture(),post.getCreatedAt(),post.getUpdatedAt());
    }

}
