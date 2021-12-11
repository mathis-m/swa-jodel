package de.swa.rest.posts.dto;

import de.swa.rest.posts.Colors;

public class CreatePostDto {
    private String text;

    private Colors color;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }
}
