package com.capgemini.socialmediafrontendupdated.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TrendingDTO {
    @JsonProperty("postId")
    private int postId;
    private String content;
    private LocalDateTime timestamp;

    @JsonProperty("userId")
    private int userId;
    private String username;
    private String email;

    private long likes;
    private long comments;
}
