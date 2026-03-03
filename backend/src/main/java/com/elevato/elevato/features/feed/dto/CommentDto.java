package com.elevato.elevato.features.feed.dto;

public class CommentDto {
    private String content;

    public CommentDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
