package com.hotel.reservation.controller;

import com.hotel.reservation.dto.GuestDTO;
import com.hotel.reservation.model.Reservation;
import com.hotel.reservation.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    @InjectMocks
    private ReservationController reservationController;

    @Mock
    private ReservationService reservationService;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = new Reservation();
        reservation.setId(1L);
        reservation.setGuestId(101L);
        reservation.setRoomId(202l);
        reservation.setCheckInDate(LocalDate.of(2025, 4, 15));
        reservation.setCheckOutDate(LocalDate.of(2025, 4, 18));
    }

    @Test
    void testCreateReservation() {
        when(reservationService.addReservation(reservation)).thenReturn(reservation);

        ResponseEntity<String> response = reservationController.createReservation(reservation);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Reservation created successfully and database updated.", response.getBody());

        verify(reservationService, times(1)).addReservation(reservation);
    }

    @Test
    void testGetAllReservations() {
        when(reservationService.getAllReservations()).thenReturn(List.of(reservation));

        List<Reservation> result = reservationController.getAllReservations();

        assertEquals(1, result.size());
        assertEquals(reservation.getId(), result.get(0).getId());
    }

    @Test
    void testGetReservationWithDetails_Found() {
        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setName("John Doe");

        when(reservationService.getReservationById(1L)).thenReturn(Optional.of(reservation));
        when(reservationService.getGuestDetails(101L)).thenReturn(guestDTO);
        when(reservationService.isRoomAvailable(202L)).thenReturn(true);
        when(reservationService.getPaymentStatus(1L)).thenReturn("Paid");

        ResponseEntity<Map<String, Object>> response = reservationController.getReservationWithDetails(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().containsKey("guestDetails"));
        assertEquals("John Doe", ((GuestDTO) response.getBody().get("guestDetails")).getName());
        assertEquals(true, response.getBody().get("roomAvailable"));
        assertEquals("Paid", response.getBody().get("paymentStatus"));
    }

    @Test
    void testGetReservationWithDetails_NotFound() {
        when(reservationService.getReservationById(999L)).thenReturn(Optional.empty());

        ResponseEntity<Map<String, Object>> response = reservationController.getReservationWithDetails(999L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testIsRoomReserved_True() {
        reservation.setRoomId(303L);
        when(reservationService.getAllReservations()).thenReturn(List.of(reservation));

        ResponseEntity<Boolean> response = reservationController.isRoomReserved(303);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody());
    }

    @Test
    void testIsRoomReserved_False() {
        reservation.setRoomId(404l);
        when(reservationService.getAllReservations()).thenReturn(List.of(reservation));

        ResponseEntity<Boolean> response = reservationController.isRoomReserved(999);

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody());
    }
}
