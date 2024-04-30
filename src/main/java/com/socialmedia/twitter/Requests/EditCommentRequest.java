package com.socialmedia.twitter.Requests;

public class EditCommentRequest {
    private String commentBody;
    private int commentID;

    public void CreatePostRequest() {
    }

    public void CreatePostRequest(String commentBody, int commentID) {
        this.commentBody = commentBody;
        this.commentID = commentID;
    }

    public String getCommentBody() {
        return commentBody;
    }
    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public int getCommentID() {
        return commentID;
    }
    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

}

