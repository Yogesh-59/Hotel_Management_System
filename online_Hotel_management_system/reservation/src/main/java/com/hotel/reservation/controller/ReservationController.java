package com.hotel.reservation.controller;

import com.hotel.reservation.dto.GuestDTO;
import com.hotel.reservation.model.Reservation;
import com.hotel.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // Add new reservation
    @PostMapping("/add")
    public ResponseEntity<String> createReservation(@RequestBody Reservation reservation) {
        reservationService.addReservation(reservation);
        return ResponseEntity.ok("Reservation created successfully and database updated.");
    }

    // Get all reservations
    @GetMapping("/getAll")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    // Get reservation by ID along with guest details, room availability, and payment status
    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> getReservationWithDetails(@PathVariable Long id) {
        Optional<Reservation> optionalReservation = reservationService.getReservationById(id);

        if (optionalReservation.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Reservation reservation = optionalReservation.get();

        Map<String, Object> response = new HashMap<>();
        response.put("reservation", reservation);

        try {
            GuestDTO guest = reservationService.getGuestDetails(reservation.getGuestId());
            Boolean roomAvailable = reservationService.isRoomAvailable(reservation.getRoomId());
            String paymentStatus = reservationService.getPaymentStatus(reservation.getId());

            response.put("guestDetails", guest);
            response.put("roomAvailable", roomAvailable);
            response.put("paymentStatus", paymentStatus);

        } catch (Exception e) {
            response.put("error", "Error fetching data from external services: " + e.getMessage());
        }

        return ResponseEntity.ok(response);
    }
    @GetMapping("/room/{roomNumber}")
    public ResponseEntity<Boolean> isRoomReserved(@PathVariable int roomNumber) {
        // Check if any reservation exists for the given room
        List<Reservation> reservations = reservationService.getAllReservations();
        boolean reserved = reservations.stream()
                .anyMatch(r -> r.getRoomId() == roomNumber);
        return ResponseEntity.ok(reserved);
    }
}
