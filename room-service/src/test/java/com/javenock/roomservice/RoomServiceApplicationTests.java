package com.javenock.roomservice;

import com.javenock.roomservice.model.Room;
import com.javenock.roomservice.request.RoomRequest;
import com.javenock.roomservice.request.RoomStatusRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoomServiceApplicationTests {

	@LocalServerPort
	private int port;

	private String baseUrl="http://localhost";

	private static RestTemplate restTemplate;

	@Autowired
	private TestH2Repository testH2Repository;

	@BeforeAll
	public static void init(){
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void setUp(){
		baseUrl = baseUrl.concat(":").concat(port+"").concat("/room");
	}

	@Test
	@Sql(statements = "DELETE FROM ROOM_TB WHERE room_number = '1234R'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void testAddRoom(){
		RoomRequest roomRequest = new RoomRequest("1234R","labour ward","in use");
		ResponseEntity<Room> roomResponseEntity = restTemplate.postForEntity(baseUrl, roomRequest, Room.class);
		assertEquals("1234R",roomResponseEntity.getBody().getRoomNumber());
		assertEquals(1, testH2Repository.findAll().size());
	}

	@Test
	@Sql(statements = "INSERT INTO ROOM_TB (id, room_number, type, status) VALUES ('1c96eaf6-7df9-4008-bb67-5a785e48e0a0','12345T','OPERATION_WARD', 'IN_USE')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM ROOM_TB WHERE id = '1c96eaf6-7df9-4008-bb67-5a785e48e0a0'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void testFindAllRooms(){
		ResponseEntity<List> list_of_rooms = restTemplate.getForEntity(baseUrl, List.class);
		assertAll(
				() -> assertNotNull(list_of_rooms),
				() -> assertEquals(1,list_of_rooms.getBody().size()),
				() -> assertEquals(1, testH2Repository.findAll().size())
		);
	}

	@Test
	@Sql(statements = "INSERT INTO ROOM_TB (id, room_number, type, status) VALUES ('1c96eaf6-7df9-4008-bb67-5a785e48e0a0','12345T','OPERATION_WARD', 'IN_USE')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM ROOM_TB WHERE id = '1c96eaf6-7df9-4008-bb67-5a785e48e0a0'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void testFindByRoomNumber(){
		ResponseEntity<Room> room = restTemplate.getForEntity(baseUrl + "/{roomNumber}", Room.class, "12345T");
		assertAll(
				() -> assertNotNull(room),
				() -> assertEquals("OPERATION_WARD",room.getBody().getType()),
				() -> assertEquals("1c96eaf6-7df9-4008-bb67-5a785e48e0a0",testH2Repository.findByRoomNumber("12345T").get().getId().toString())
		);
	}

	@Test
	@Sql(statements = "INSERT INTO ROOM_TB (id, room_number, type, status) VALUES ('1c96eaf6-7df9-4008-bb67-5a785e48e0a0','12345T','OPERATION_WARD', 'IN_USE')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "DELETE FROM ROOM_TB WHERE id = '1c96eaf6-7df9-4008-bb67-5a785e48e0a0'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void testUpdateRoomStatus(){
		RoomStatusRequest roomStatusRequest = new RoomStatusRequest("NOT_IN_USE");
		restTemplate.put(baseUrl + "/{roomNumber}", roomStatusRequest,"12345T");
		Room room = testH2Repository.findByRoomNumber("12345T").get();
		assertAll(
				() -> assertEquals("NOT_IN_USE", room.getStatus()),
				() -> assertEquals("1c96eaf6-7df9-4008-bb67-5a785e48e0a0", room.getId().toString())
		);
	}

	@Test
	@Sql(statements = "INSERT INTO ROOM_TB (id, room_number, type, status) VALUES ('1c96eaf6-7df9-4008-bb67-5a785e48e0a0','12345T','OPERATION_WARD', 'IN_USE')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void testDeleteRoom(){
		int list_size = testH2Repository.findAll().size();
		assertEquals(1, list_size);
		restTemplate.delete(baseUrl+"/{roomNumber}", "12345T");
		assertEquals(0, testH2Repository.findAll().size());
	}

}
