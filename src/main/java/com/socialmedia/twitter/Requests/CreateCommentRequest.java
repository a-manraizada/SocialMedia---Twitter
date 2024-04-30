package com.socialmedia.twitter.Requests;

public class CreateCommentRequest {
    private String commentBody;
    private int userID;
    private int postID;

    public void CreatePostRequest() {
    }

    public void CreatePostRequest(String commentBody, int userID, int postID) {
        this.commentBody = commentBody;
        this.userID = userID;
        this.postID = postID;
    }

    public String getCommentBody() {
        return commentBody;
    }
    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }
}

