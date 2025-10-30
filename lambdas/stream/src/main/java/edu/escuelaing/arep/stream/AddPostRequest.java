package edu.escuelaing.arep.stream;

public class AddPostRequest {
    private Long postId;
    private Long streamId;

    public  AddPostRequest(Long postId, Long streamId) {
        this.postId = postId;
        this.streamId = streamId;
    }

    public AddPostRequest() {}
    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public Long getStreamId() { return streamId; }
    public void setStreamId(Long streamId) { this.streamId = streamId; }
}