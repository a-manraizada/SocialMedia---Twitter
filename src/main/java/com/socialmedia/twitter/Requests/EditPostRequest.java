package com.socialmedia.twitter.Requests;

public class EditPostRequest {
    private String postBody;
    private int postID;

    public EditPostRequest() {
    }

    public EditPostRequest(String postBody, int postID) {
        this.postBody = postBody;
        this.postID = postID;
    }

    public String getPostBody() {
        return postBody;
    }
    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }
    public int getpostID() {
        return postID;
    }
    public void setPostID(int postID) {
        this.postID = postID;
    }
}
