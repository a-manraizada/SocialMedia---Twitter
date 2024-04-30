package com.socialmedia.twitter;

import com.socialmedia.twitter.Entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
//    Post findByEmail(String email);
    Optional<Post> findById(int postID);

}

