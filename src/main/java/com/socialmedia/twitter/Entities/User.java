package com.socialmedia.twitter.Entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name="AppUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    private String name;

    @Column(unique=true)
    private String email;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();



    // Constructors, getters, and setters
    public User() {
    }

    public User(int userID, String name, String email, List<Post> posts) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.posts = posts;
    }

    // Getters and setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
