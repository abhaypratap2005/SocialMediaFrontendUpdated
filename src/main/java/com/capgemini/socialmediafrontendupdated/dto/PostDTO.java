package com.capgemini.socialmediafrontendupdated.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostDTO {

    @JsonProperty("postId")
    private Integer postId;
    private String content;
    private LocalDateTime timestamp;

    @JsonProperty("userID")
    private Integer userID;
    private String username;

    private Integer likeCount;
    private Integer commentCount;
}
