package de.swa.rest.posts.dto;

public class CreateCommentDto {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
