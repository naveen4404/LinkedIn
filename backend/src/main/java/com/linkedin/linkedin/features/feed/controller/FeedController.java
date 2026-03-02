package com.linkedin.linkedin.features.feed.controller;

import com.linkedin.linkedin.features.authentication.model.AuthenticationUser;
import com.linkedin.linkedin.features.feed.dto.PostDto;
import com.linkedin.linkedin.features.feed.model.Post;
import com.linkedin.linkedin.features.feed.repository.PostRepository;
import com.linkedin.linkedin.features.feed.service.FeedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feed")
public class FeedController {

    private final FeedService feedService;

    public FeedController( FeedService feedService) {
        this.feedService = feedService;
    }

    @PostMapping("/posts")
    public ResponseEntity<PostDto> createPost(@RequestAttribute("authenticatedUser")AuthenticationUser user, @RequestBody PostDto post){
        PostDto createdPost = feedService.createPost(user.getId(), post);
        return ResponseEntity.created(null).body(createdPost);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> editPost(@RequestAttribute("authenticatedUser")AuthenticationUser user, @RequestBody PostDto post,@PathVariable Long postId){
        PostDto editedPost = feedService.editPost(user.getId(),post,postId);
        return ResponseEntity.ok(editedPost);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId){
        PostDto post = feedService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(@RequestAttribute("authenticatedUser") AuthenticationUser user, @PathVariable Long postId){
        feedService.deletePost(user.getId(), postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getFeed(@RequestAttribute("authenticatedUser")AuthenticationUser user){
        List<PostDto> feed = feedService.getFeed(user.getId());
        return ResponseEntity.ok(feed);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPosts(){
        List<PostDto> feed = feedService.getAllPosts();
        return ResponseEntity.ok(feed);
    }

    @GetMapping("/posts/user/{authorId}")
    public ResponseEntity<List<PostDto>> getAllPostsOfAuthor(@PathVariable Long authorId){
        List<PostDto> feed = feedService.getAllPostsByAuthorId(authorId);
        return ResponseEntity.ok(feed);
    }

    @PutMapping("/posts/{postId}/like")
    public ResponseEntity<Post> likePost(@RequestAttribute("authenticatedUser") AuthenticationUser user, @PathVariable Long postId){
        return ResponseEntity.ok(feedService.likePost(user.getId(),postId));
    }

}
