package com.socialmedia.twitter;

import com.socialmedia.twitter.Entities.Comment;
import com.socialmedia.twitter.Entities.Post;
import com.socialmedia.twitter.Entities.User;
import com.socialmedia.twitter.Requests.CreateCommentRequest;
import com.socialmedia.twitter.Requests.CreatePostRequest;
import com.socialmedia.twitter.Requests.EditCommentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentController(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @PostMapping
    public String createComment(@RequestBody CreateCommentRequest request) {
        Optional<User> existinguser = userRepository.findById(request.getUserID());
        Optional<Post> existingpost = postRepository.findById(request.getPostID());

        if(existinguser.isEmpty()) {
            return "User does not exist";
        }
        else if(existingpost.isEmpty()) {
            return "Post does not exist";
        }
        else {
            Comment newComment = new Comment();

            newComment.setCommentBody(request.getCommentBody());
            newComment.setUserID(existinguser.get().getUserID());
            newComment.setPostID(existingpost.get().getPostID());

            int postid = existingpost.get().getPostID();

            existingpost.get().setPostID(postid);
            existingpost.get().getComments().add(newComment);
            existingpost.get().setPostID(postid);

            commentRepository.save(newComment);

            return "Comment created successfully";
        }
    }

    @PatchMapping
    public String editComment(@RequestBody EditCommentRequest request) {
        Optional<Comment> existingcomment = commentRepository.findById(request.getCommentID());

        if(existingcomment.isEmpty()) {
            return "Comment does not exist";
        }
        else {

            Comment newComment = commentRepository.findById(request.getCommentID()).orElse(null);

            User user = userRepository.findById(newComment.getUserID()).orElse(null);
            Post post = postRepository.findById(newComment.getPostID()).orElse(null);

            post.getComments().remove(newComment);

            newComment.setCommentBody(request.getCommentBody());

            post.getComments().add(newComment);

            commentRepository.save(newComment);

            return "Comment edited successfully";
        }
    }

    @DeleteMapping
    public String deleteComment(@RequestParam("commentID") int commentID) {
        Optional<Comment> existingcomment = commentRepository.findById(commentID);
        if(existingcomment.isEmpty()) {
            return "Comment does not exist";
        }
        else {
            Post post = postRepository.findById(existingcomment.get().getPostID()).orElse(null);
            User user = userRepository.findById(existingcomment.get().getUserID()).orElse(null);

            assert post != null;
            post.getComments().remove(existingcomment.get());
            commentRepository.delete(existingcomment.get());

            return "Comment deleted";
        }
    }

    @GetMapping
    public ResponseEntity<?> getComment(@RequestParam("commentID") int commentID) {
        try {
            Comment comment = commentRepository.findById(commentID).orElse(null);
            if (comment != null) {
                User user = userRepository.findById(comment.getUserID()).orElse(null);
                CommentResponse response = new CommentResponse(comment.getCommentID(), comment.getCommentBody(), new UserResponse(user.getUserID(), user.getName()));
                return ResponseEntity.ok(response);
            }
            else {
                return ResponseEntity.badRequest().body("Comment does not exist");
            }
        }
        catch(Exception E) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}

