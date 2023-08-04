package com.javenock.roomservice;

import com.javenock.roomservice.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestH2Repository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomNumber(String s);
}
