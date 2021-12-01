package de.swa.rest.posts.dto;

public class VotingResponseDto {
    private boolean hasUpVoted;
    private boolean hasDownVoted;
    private Long postId;

    public boolean isHasUpVoted() {
        return hasUpVoted;
    }

    public void setHasUpVoted(boolean hasUpVoted) {
        this.hasUpVoted = hasUpVoted;
    }

    public boolean isHasDownVoted() {
        return hasDownVoted;
    }

    public void setHasDownVoted(boolean hasDownVoted) {
        this.hasDownVoted = hasDownVoted;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
