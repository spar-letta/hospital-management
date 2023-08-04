package com.javenock.roomservice.controller;

import com.javenock.roomservice.exception.NoRoomsRegisteredException;
import com.javenock.roomservice.exception.RoomNotFoundException;
import com.javenock.roomservice.model.Room;
import com.javenock.roomservice.request.RoomRequest;
import com.javenock.roomservice.request.RoomStatusRequest;
import com.javenock.roomservice.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Room create_new_room(@RequestBody @Valid RoomRequest roomRequest){
        return roomService.createNewRoom(roomRequest);
    }

    @GetMapping("/{roomNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Room getRoomByNumber(@PathVariable String roomNumber) throws RoomNotFoundException {
        return roomService.getRoomByNumber(roomNumber);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Room> allRooms() throws NoRoomsRegisteredException {
        return roomService.findAllRooms();
    }

    @PutMapping("/{roomNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Room updateRoomStatus(@PathVariable String roomNumber, @RequestBody @Valid RoomStatusRequest roomStatusRequest ) throws RoomNotFoundException {
        return roomService.updateRoomStatus(roomNumber,roomStatusRequest);
    }

    @DeleteMapping("/{roomNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteRoomByRoom(@PathVariable String roomNumber) throws RoomNotFoundException {
        return roomService.deteleRoom(roomNumber);
    }
}
