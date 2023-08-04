package com.javenock.inpatientservice.response;

import lombok.Data;

import java.util.UUID;
@Data
public class RoomResponse {
    private UUID id;
    private String roomNumber;
    private String type;
    private String status;
}
