package com.socialmedia.twitter;

import com.socialmedia.twitter.Entities.Comment;
import com.socialmedia.twitter.Entities.Post;
import com.socialmedia.twitter.Entities.User;
import com.socialmedia.twitter.Requests.LoginRequest;
import com.socialmedia.twitter.Requests.SignupRequest;
import org.apache.coyote.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@EnableJpaRepositories
@SpringBootApplication
@RestController
@RequestMapping("")
public class UserController {
    private final PostRepository postRepository;

//    public static void main(String[] args) {
//        SpringApplication.run(UserController.class, args);
//    }

    @RequestMapping(value = "")
    public String hello() {
        return "Welcome to Twitter";
    }

    private final UserRepository userRepository;


    @Autowired
    public UserController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }


    @GetMapping("/")
    public ResponseEntity<?> getAllPosts() {
        try {
            List<PostResponse> posts = new ArrayList<>();
            for (Post post:postRepository.findAll()) {
                List<CommentResponse> comments = new ArrayList<>();
                for(Comment comment:post.getComments()) {
                    List<UserResponse> users = new ArrayList<>();
                    Optional<User> user = userRepository.findById(comment.getUserID());
                    comments.add(new CommentResponse(comment.getCommentID(), comment.getCommentBody(), new UserResponse(user.get().getUserID(), user.get().getName())));
                }
                Collections.reverse(comments);
                posts.add(new PostResponse(post.getPostID(), post.getPostBody(), post.getDate(), comments));
            }
            Collections.reverse(posts);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving posts");
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if(user==null) {
            return "User does not exist";
        }
        else if (user.getPassword().equals(request.getPassword())) {
            return "Login Successful";
        } else {
            return "Username/Password Incorrect";
        }
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest request) {
        // Check if the account already exists
        User existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser != null) {
            return "Forbidden, Account already exists";
        }

        // Create a new user
        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setName(request.getName());
        newUser.setPassword(request.getPassword());

        // Save the new user to the database
        userRepository.save(newUser);

        return "Account Creation Successful";
    }



    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(@RequestParam("userID") int userID) {
        try {
            User user = userRepository.findById(Math.toIntExact(userID)).orElse(null);
            AltUserResponse response = new AltUserResponse(user.getName(), user.getUserID(), user.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User does not exist");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<AltUserResponse> users = new ArrayList<>();
            for(User user:userRepository.findAll()) {
                users.add(new AltUserResponse(user.getName(), user.getUserID(), user.getEmail()));
            }
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving users");
        }
    }
}

