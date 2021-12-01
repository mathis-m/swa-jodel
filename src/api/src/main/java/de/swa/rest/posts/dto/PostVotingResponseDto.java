package de.swa.rest.posts.dto;

public class PostVotingResponseDto {
    private PostResponseDto post;
    private VotingResponseDto voting;

    public PostResponseDto getPost() {
        return post;
    }

    public void setPost(PostResponseDto post) {
        this.post = post;
    }

    public VotingResponseDto getVoting() {
        return voting;
    }

    public void setVoting(VotingResponseDto voting) {
        this.voting = voting;
    }
}
