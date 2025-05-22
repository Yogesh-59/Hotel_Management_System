package com.hotel.room_service.service;

import com.hotel.room_service.client.RateClient;
import com.hotel.room_service.client.ReservationClient;
import com.hotel.room_service.model.Room;
import com.hotel.room_service.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RateClient rateClient;
    private final ReservationClient reservationClient;

    // Constructor for dependency injection
    public RoomService(RoomRepository roomRepository,
                       RateClient rateClient,
                       ReservationClient reservationClient) {
        this.roomRepository = roomRepository;
        this.rateClient = rateClient;
        this.reservationClient = reservationClient;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomByNumber(int roomNumber) {
        return roomRepository.findById(roomNumber).orElse(null);
    }

    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    public void deleteRoom(int roomNumber) {
        roomRepository.deleteById(roomNumber);
    }

    public double getRate(int roomNumber) {
        Room room = roomRepository.findById(roomNumber)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Assuming you have a roomType field in Room model (you'll need to add this if not)
        String roomType = room.getRoomType();
        return rateClient.getRateByRoomType(roomType);
    }

    public boolean checkReservation(int roomNumber) {
        return reservationClient.isRoomReserved(roomNumber);
    }

    public Boolean isRoomAvailable(int roomId) {
        Optional<Room> roomOpt = roomRepository.findById(roomId);
        if (roomOpt.isPresent()) {
            Room room = roomOpt.get();
            System.out.println("Room availability: " + room.isAvailable() + ", Booked: " + room.isBooked());
            return room.isAvailable() && !room.isBooked();
        } else {
            return null;
        }
    }

    public Room updateRoom(Room updatedRoom) {
        Optional<Room> existingRoomOpt = roomRepository.findById(updatedRoom.getRoomNumber());

        if (existingRoomOpt.isPresent()) {
            Room existingRoom = existingRoomOpt.get();
            existingRoom.setAvailable(updatedRoom.isAvailable());
            existingRoom.setBooked(updatedRoom.isBooked());
            existingRoom.setPrice(updatedRoom.getPrice());
            return roomRepository.save(existingRoom);
        } else {
            throw new RuntimeException("Room not found with number: " + updatedRoom.getRoomNumber());
        }
    }
}
