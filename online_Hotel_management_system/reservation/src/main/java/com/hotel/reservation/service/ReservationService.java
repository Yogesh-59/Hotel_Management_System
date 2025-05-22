package com.hotel.reservation.service;

import com.hotel.reservation.dto.GuestDTO;
import com.hotel.reservation.feign.GuestServiceClient;
import com.hotel.reservation.feign.PaymentServiceClient;
import com.hotel.reservation.feign.RoomServiceClient;
import com.hotel.reservation.model.Reservation;
import com.hotel.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomServiceClient roomServiceClient;
    private final GuestServiceClient guestServiceClient;
    private final PaymentServiceClient paymentServiceClient;

    public ReservationService(ReservationRepository reservationRepository,
                              RoomServiceClient roomServiceClient,
                              GuestServiceClient guestServiceClient,
                              PaymentServiceClient paymentServiceClient) {
        this.reservationRepository = reservationRepository;
        this.roomServiceClient = roomServiceClient;
        this.guestServiceClient = guestServiceClient;
        this.paymentServiceClient = paymentServiceClient;
    }

    public Reservation addReservation(Reservation reservation) {

        // Step 1: Check room availability
        Boolean isAvailable = roomServiceClient.isRoomAvailable(reservation.getRoomId());
        if (!isAvailable) {
            throw new RuntimeException("Room is not available!");
        }

        // Step 2: Fetch guest details
        GuestDTO guest = guestServiceClient.getGuestById(reservation.getGuestId());
        System.out.println("Guest Name: " + guest.getName());

        // Step 3: Save reservation
        Reservation savedReservation = reservationRepository.save(reservation);

        // Step 4: Check payment status (optional logic)
        String paymentStatus = paymentServiceClient.getPaymentStatusByBookingId(savedReservation.getId());
        System.out.println("Payment Status: " + paymentStatus);

        return savedReservation;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public GuestDTO getGuestDetails(Long guestId) {
        return guestServiceClient.getGuestById(guestId);
    }

    public Boolean isRoomAvailable(Long roomId) {
        return roomServiceClient.isRoomAvailable(roomId);
    }

    public String getPaymentStatus(Long reservationId) {
        return paymentServiceClient.getPaymentStatusByBookingId(reservationId);
    }

    public boolean isRoomReserved(int roomNumber) {
        return reservationRepository.findAll()
                .stream()
                .anyMatch(r -> r.getRoomId() == roomNumber);
    }
}
