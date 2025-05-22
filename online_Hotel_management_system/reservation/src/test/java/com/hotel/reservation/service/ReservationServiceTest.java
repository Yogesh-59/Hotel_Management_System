package com.hotel.reservation.service;

import com.hotel.reservation.dto.GuestDTO;
import com.hotel.reservation.feign.GuestServiceClient;
import com.hotel.reservation.feign.PaymentServiceClient;
import com.hotel.reservation.feign.RoomServiceClient;
import com.hotel.reservation.model.Reservation;
import com.hotel.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RoomServiceClient roomServiceClient;

    @Mock
    private GuestServiceClient guestServiceClient;

    @Mock
    private PaymentServiceClient paymentServiceClient;

    @InjectMocks
    private ReservationService reservationService;

    private Reservation reservation;
    private GuestDTO guest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        reservation = new Reservation();
        reservation.setId(1L);
        reservation.setRoomId(101L);
        reservation.setGuestId(10L);

        guest = new GuestDTO();
        guest.setName("Deepraj");
        guest.setEmail("deepraj@cg.com");
        guest.setPhoneNumber("7725867890");
        guest.setAddress("bhoal");
        guest.setCompany("capgemini");
        guest.setGender("Male");
        guest.setMemberCode("M123");
    }

    @Test
    void testAddReservation_Success() {
        when(roomServiceClient.isRoomAvailable(101L)).thenReturn(true);
        when(guestServiceClient.getGuestById(10L)).thenReturn(guest);
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(paymentServiceClient.getPaymentStatusByBookingId(1L)).thenReturn("Paid");

        Reservation saved = reservationService.addReservation(reservation);

        assertNotNull(saved);
        assertEquals(1L, saved.getId());
        verify(roomServiceClient).isRoomAvailable(101L);
        verify(guestServiceClient).getGuestById(10L);
        verify(paymentServiceClient).getPaymentStatusByBookingId(1L);
    }

    @Test
    void testAddReservation_RoomNotAvailable() {
        when(roomServiceClient.isRoomAvailable(101L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservationService.addReservation(reservation);
        });

        assertEquals("Room is not available!", exception.getMessage());
        verify(roomServiceClient).isRoomAvailable(101L);
        verifyNoInteractions(guestServiceClient, reservationRepository, paymentServiceClient);
    }

    @Test
    void testGetAllReservations() {
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation));

        List<Reservation> list = reservationService.getAllReservations();

        assertEquals(1, list.size());
    }

    @Test
    void testGetReservationById() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        Optional<Reservation> result = reservationService.getReservationById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testGetGuestDetails() {
        when(guestServiceClient.getGuestById(10L)).thenReturn(guest);

        GuestDTO result = reservationService.getGuestDetails(10L);

        assertEquals("Deepraj", result.getName());
    }

    @Test
    void testIsRoomAvailable() {
        when(roomServiceClient.isRoomAvailable(101L)).thenReturn(true);

        Boolean available = reservationService.isRoomAvailable(101L);

        assertTrue(available);
    }

    @Test
    void testGetPaymentStatus() {
        when(paymentServiceClient.getPaymentStatusByBookingId(1L)).thenReturn("Paid");

        String status = reservationService.getPaymentStatus(1L);

        assertEquals("Paid", status);
    }

    @Test
    void testIsRoomReserved() {
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation));

        boolean reserved = reservationService.isRoomReserved(101);

        assertTrue(reserved);
    }
}
