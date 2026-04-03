package com.capgemini.socialmediafrontendupdated.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedDTO {
    private String username;
    private String contentPreview;
    private LocalDateTime timestamp;

    @JsonProperty("postID")   // 🔥 THIS IS THE FIX
    private int postId;
    @JsonProperty("userID")   // 🔥 map backend
    private int userId;
}
