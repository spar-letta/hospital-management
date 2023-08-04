package com.javenock.roomservice.service;

import com.javenock.roomservice.exception.NoRoomsRegisteredException;
import com.javenock.roomservice.exception.RoomNotFoundException;
import com.javenock.roomservice.model.Room;
import com.javenock.roomservice.repository.RoomRepository;
import com.javenock.roomservice.request.RoomRequest;
import com.javenock.roomservice.request.RoomStatusRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;


    public Room createNewRoom(RoomRequest roomRequest) {
        Room newRoom = Room.builder()
                .roomNumber(roomRequest.getRoomNumber())
                .type(roomRequest.getType())
                .status(roomRequest.getStatus())
                .build();
        return roomRepository.save(newRoom);
    }


    public Room getRoomByNumber(String roomNumber) throws RoomNotFoundException {
        return roomRepository.findByRoomNumber(roomNumber).orElseThrow(() -> new RoomNotFoundException("No such room with Number :"+roomNumber));
    }

    public List<Room> findAllRooms() throws NoRoomsRegisteredException {
        List<Room> all_rooms = roomRepository.findAll();
        if(all_rooms.size() > 0){
            return all_rooms;
        }else {
            throw new NoRoomsRegisteredException("No rooms registered");
        }
    }

    public Room updateRoomStatus(String roomNumber, RoomStatusRequest roomStatusRequest) throws RoomNotFoundException {
        Room room = roomRepository.findByRoomNumber(roomNumber).orElseThrow(() -> new RoomNotFoundException("No such room with Number :" + roomNumber));
        room.setRoomNumber(room.getRoomNumber());
        room.setStatus(roomStatusRequest.getStatus());
        return roomRepository.save(room);
    }

    public String deteleRoom(String roomNumber) throws RoomNotFoundException {
        Room room = roomRepository.findByRoomNumber(roomNumber).orElseThrow(() -> new RoomNotFoundException("No such room with Number :" + roomNumber));
        roomRepository.delete(room);
        return "deleted successfully";
    }
}
