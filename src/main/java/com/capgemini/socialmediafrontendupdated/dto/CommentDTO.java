package com.capgemini.socialmediafrontendupdated.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentDTO {
    @JsonProperty("commentID")
    private Integer commentID;
    private String commentText;
    private LocalDateTime timestamp;

    @JsonProperty("userID")
    private Integer userID;
    private String username;

    @JsonProperty("postID")
    private Integer postID;

    private Integer likeCount;
}
