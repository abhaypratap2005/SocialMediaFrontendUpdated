package com.capgemini.socialmediafrontendupdated.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationDTO {

    @JsonProperty("notificationID")
    private Integer notificationID;
    private String content;
    private LocalDateTime timestamp;

    @JsonProperty("userID")
    private Integer userID;
    private String username;

    private Boolean isRead;
}
