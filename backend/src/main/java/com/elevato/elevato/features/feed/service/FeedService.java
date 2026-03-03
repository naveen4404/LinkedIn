package com.elevato.elevato.features.feed.service;

import com.elevato.elevato.exception.ForbiddenException;
import com.elevato.elevato.exception.ResourceNotFoundException;
import com.elevato.elevato.features.authentication.model.AuthenticationUser;
import com.elevato.elevato.features.authentication.repository.AuthenticationUserRepository;
import com.elevato.elevato.features.feed.dto.CommentDto;
import com.elevato.elevato.features.feed.dto.PostDto;
import com.elevato.elevato.features.feed.dto.PostMapper;
import com.elevato.elevato.features.feed.model.Comment;
import com.elevato.elevato.features.feed.model.Post;
import com.elevato.elevato.features.feed.repository.CommentRepository;
import com.elevato.elevato.features.feed.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService {
    Logger logger = LoggerFactory.getLogger(FeedService.class);
    private final PostRepository postRepository;
    private final AuthenticationUserRepository authenticationUserRepository;
    private final CommentRepository commentRepository;

    public FeedService(PostRepository postRepository, AuthenticationUserRepository authenticationUserRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.authenticationUserRepository = authenticationUserRepository;
        this.commentRepository = commentRepository;
    }

    public PostDto createPost(Long id, PostDto post) {
        AuthenticationUser user = authenticationUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found") );
        Post newPost = new Post(post.getContent(), post.getPicture(), user);
        postRepository.save(newPost);
        return PostMapper.toDto(newPost);
    }

    public PostDto editPost(Long userId, PostDto post, Long postId) {

        Post oldPost = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        AuthenticationUser user = authenticationUserRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(!oldPost.getAuthor().equals(user)){
            throw new ForbiddenException("User is not allowed to edit this post");
        }
        oldPost.setContent(post.getContent());
        oldPost.setPicture(post.getPicture());
        return PostMapper.toDto(postRepository.save(oldPost));
    }


    public List<PostDto> getFeed(Long id) {
        List<Post> posts = postRepository.findPostsNotByUser(id);
        List<PostDto> feed = posts.stream().map((post) -> PostMapper.toDto(post)).toList();
        return feed;
    }

    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostDto> feed = posts.stream().map((post) -> PostMapper.toDto(post)).toList();
        return feed;
    }

    public PostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found"));
        return PostMapper.toDto(post);
    }

    public void deletePost(Long userId, Long postId) {
        AuthenticationUser user = authenticationUserRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post not found"));
        if(!post.getAuthor().equals(user)){
            throw new ForbiddenException("User is not allowed to do this.");
        }
        postRepository.delete(post);
    }

    public List<PostDto> getAllPostsByAuthorId(Long authorId) {
        AuthenticationUser user = authenticationUserRepository.findById(authorId).orElseThrow(()->new ResourceNotFoundException("User not found"));
        List<Post> posts = user.getPosts();
        return posts.stream().map((post)-> PostMapper.toDto(post)).toList();
    }

    public Post likePost(Long userId, Long postId) {
        AuthenticationUser user = authenticationUserRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post not found"));
        if(post.getLikes().contains(user)){
            post.getLikes().remove(user);
        }else{
            post.getLikes().add(user);
        }
        return postRepository.save(post);
    }

    public Comment addComment(Long userId, Long postId, CommentDto comment) {
        AuthenticationUser user = authenticationUserRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post not found"));
        Comment cmt = new Comment(post, user, comment.getContent());
        return commentRepository.save(cmt);
    }

    public Comment editComment(Long userId, Long commentId, CommentDto comment) {
        AuthenticationUser user = authenticationUserRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
        Comment cmt = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment not found"));
        if(!cmt.getUser().equals(user)){
            throw new ForbiddenException("User is not allowed to edit this comment");
        }
        cmt.setContent(comment.getContent());
        return commentRepository.save(cmt);
    }

    public void deleteComment(Long userId, Long commentId) {
        AuthenticationUser user = authenticationUserRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
        Comment cmt = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment not found"));
        if(!cmt.getUser().equals(user)){
            throw new ForbiddenException("User is not allowed to delete this comment");
        }
        commentRepository.delete(cmt);
    }
}
