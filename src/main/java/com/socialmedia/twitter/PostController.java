package com.socialmedia.twitter;

import com.socialmedia.twitter.Entities.Post;
import com.socialmedia.twitter.Entities.User;
import com.socialmedia.twitter.Entities.Comment;
import com.socialmedia.twitter.Requests.CreatePostRequest;
import com.socialmedia.twitter.Requests.EditPostRequest;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
//import java.util.Date;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public PostController(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @PostMapping
    public String createPost(@RequestBody CreatePostRequest request) {
        Optional<User> existinguser = userRepository.findById(request.getUserID());
        if(existinguser.isEmpty()) {
            return "User does not exist";
        }
        else {
            Post newPost = new Post();
            newPost.setPostBody(request.getPostBody());
            newPost.setUserID(existinguser.get().getUserID());
            LocalDate date = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = date.format(formatter);
            newPost.setDate(formattedDate);

            existinguser.get().getPosts().add(newPost);

            postRepository.save(newPost);

            return "Post created successfully";
        }
    }


    @GetMapping
    public ResponseEntity<?> getPost(@RequestParam("postID") int postID) {
        try {
            Post post = postRepository.findById(postID).orElse(null);
            assert post != null;
            List<Comment> comments = post.getComments();
            List<CommentResponse> commentResponses = new ArrayList<>();
            for (Comment comment : comments) {
                User creator = userRepository.findById(comment.getUserID()).get();
                commentResponses.add(new CommentResponse(comment.getCommentID(), comment.getCommentBody(), new UserResponse(creator.getUserID(), creator.getName())));
            }

            PostResponse postResponse = new PostResponse(post.getPostID(), post.getPostBody(), post.getDate(), commentResponses);
            return ResponseEntity.ok(postResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Post does not exist");
        }
    }

    @PatchMapping
    public String editPost(@RequestBody EditPostRequest request) {

        Post post = postRepository.findById(request.getpostID()).orElse(null);
        if (post == null) {
            return "Post does not exist";
        }
        post.setPostBody(request.getPostBody());
        postRepository.save(post);
        return "Post edited successfully"; // Post edited successfully
    }

    @DeleteMapping
    public ResponseEntity<String> deletePost(@RequestParam("postID") int postID) {
        try {
            Post post = postRepository.findById(postID).orElse(null);
            if (post==null) {
                return ResponseEntity.badRequest().body("Post does not exist");
            }
            List<Comment> dcomments = new ArrayList<>(post.getComments());
            for(Comment comment : dcomments) {
                post.getComments().remove(comment);
                commentRepository.delete(comment);
            }
            postRepository.delete(post);
            return ResponseEntity.ok("Post deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
