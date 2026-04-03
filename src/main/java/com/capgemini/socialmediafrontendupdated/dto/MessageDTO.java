package com.capgemini.socialmediafrontendupdated.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessageDTO {

    @JsonProperty("messageID")
    private Integer messageID;
    private String messageText;
    private LocalDateTime timestamp;

    @JsonProperty("senderID")
    private Integer senderID;
    private String senderName;

    @JsonProperty("receiverID")
    private Integer receiverID;
    private String receiverName;

    private Boolean isRead;
}
