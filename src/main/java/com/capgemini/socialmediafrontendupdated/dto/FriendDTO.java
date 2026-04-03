package com.capgemini.socialmediafrontendupdated.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class FriendDTO {

    private Integer friendshipID;
    private String status;

    @JsonProperty("userID")
    private Integer userID;

    private String username;
}
