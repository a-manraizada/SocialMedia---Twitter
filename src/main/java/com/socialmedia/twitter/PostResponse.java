package com.socialmedia.twitter;

import com.socialmedia.twitter.Entities.Comment;

import java.util.Date;
import java.util.List;

public class PostResponse {
    private int postID;
    private String postBody;
    private String date;
    private List<CommentResponse> comments;

    public PostResponse(int postID, String postBody, String date, List<CommentResponse> comments) {
        this.postID = postID;
        this.postBody = postBody;
        this.date = date;
        this.comments = comments;
    }

    // Getters and setters
    public int getPostID() {
        return postID;
    }
    public String getPostBody() {
        return postBody;
    }
    public String getDate() {
        return date;
    }
    public List<CommentResponse> getComments() {
        return comments;
    }
    public void setPostID(int postID) {
        this.postID = postID;
    }
    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setComments(List<CommentResponse> comments) {
        this.comments = comments;
    }
}
