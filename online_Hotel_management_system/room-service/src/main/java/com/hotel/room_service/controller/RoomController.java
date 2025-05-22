package com.hotel.room_service.controller;

import com.hotel.room_service.model.Room;
import com.hotel.room_service.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/getAll")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/get/{roomNumber}")
    public Room getRoomByNumber(@PathVariable int roomNumber) {
        return roomService.getRoomByNumber(roomNumber);
    }

    @PostMapping("/addRoom")
    public Room addRoom(@RequestBody Room room) {
        return roomService.addRoom(room);
    }

    @DeleteMapping("/delete/{roomNumber}")
    public String deleteRoom(@PathVariable int roomNumber) {
        roomService.deleteRoom(roomNumber);
        return "Room deleted successfully.";
    }

    @GetMapping("/get/rate/{roomNumber}")
    public double getRoomRate(@PathVariable int roomNumber) {
        return roomService.getRate(roomNumber);
    }

    @GetMapping("/get/reserved/{roomNumber}")
    public boolean isRoomReserved(@PathVariable int roomNumber) {
        return roomService.checkReservation(roomNumber);
    }
    @GetMapping("/availability/{roomId}")
    public ResponseEntity<Boolean> isRoomAvailable(@PathVariable int roomId) {
        Boolean isAvailable = roomService.isRoomAvailable(roomId);
        if (isAvailable == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
        return ResponseEntity.ok(isAvailable);
    }
    @PutMapping("/update")
    public Room updateRoom(@RequestBody Room room) {
        return roomService.updateRoom(room);
    }

}
