package com.javenock.roomservice.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class RoomRequest {
    @NotBlank
    private String roomNumber;
    @NotBlank
    private String type;
    @NotBlank
    private String status;
}
