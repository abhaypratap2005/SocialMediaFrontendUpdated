package com.capgemini.socialmediafrontendupdated.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class FriendDTO {

    private Integer friendshipID;
    private String status;

    @JsonProperty("user1Id")
    private Integer user1ID;

    @JsonProperty("user1Name")
    private String user1Name;

    @JsonProperty("user2Id")
    private Integer user2ID;

    @JsonProperty("user2Name")
    private String user2Name;

    private String since;
}
