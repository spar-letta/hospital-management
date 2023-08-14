package com.javenock.inpatientservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {
    private UUID id;
    private String roomNumber;
    private String type;
    private String status;
}
