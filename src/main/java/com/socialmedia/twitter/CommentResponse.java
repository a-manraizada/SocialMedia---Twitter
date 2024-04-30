package com.socialmedia.twitter;

public class CommentResponse {
    private int commentID;
    private String commentBody;
    private UserResponse commentCreator;

    public CommentResponse(int commentID, String commentBody, UserResponse commentCreator) {
        this.commentID = commentID;
        this.commentBody = commentBody;
        this.commentCreator = commentCreator;
    }

    public int getCommentID() {
        return commentID;
    }
    public String getCommentBody() {
        return commentBody;
    }
    public UserResponse getCommentCreator() {
        return commentCreator;
    }
    public void setCommentCreator(UserResponse commentCreator) {
        this.commentCreator = commentCreator;
    }
    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }
    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }
}

