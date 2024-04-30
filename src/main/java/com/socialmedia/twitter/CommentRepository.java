package com.socialmedia.twitter;

import com.socialmedia.twitter.Entities.Comment;
import com.socialmedia.twitter.Entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    //    Post findByEmail(String email);
    Optional<Comment> findById(int id);
}

