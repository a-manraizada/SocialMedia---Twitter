package com.socialmedia.twitter.Requests;

public class CreatePostRequest {
    private String postBody;
    private int userID;

    public CreatePostRequest() {
    }

    public CreatePostRequest(String postBody, int userID) {
        this.postBody = postBody;
        this.userID = userID;
    }

    public String getPostBody() {
        return postBody;
    }
    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
}
