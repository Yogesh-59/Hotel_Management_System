package com.hotel.guest_service.service;

import com.hotel.guest_service.model.GuestModel;
import com.hotel.guest_service.repository.GuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuestServiceTest {

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private GuestService guestService;

    private GuestModel guest;

    @BeforeEach
    void setUp() {
        guest = new GuestModel();
        guest.setId(1L);
        guest.setMemberCode("MC123");
        guest.setName("Deepraj");
        guest.setPhoneNumber("7773567890");
        guest.setCompany("ABC Corp");
        guest.setEmail("deepraj@example.com");
        guest.setGender("Male");
        guest.setAddress("123 Street");
    }

    @Test
    void testAddGuest() {
        when(guestRepository.save(any())).thenReturn(guest);

        GuestModel result = guestService.addGuest(guest);

        assertNotNull(result);
        assertEquals("Deepraj", result.getName());
        verify(guestRepository, times(1)).save(guest);
    }

    @Test
    void testGetGuestById_Found() {
        when(guestRepository.findById(1L)).thenReturn(Optional.of(guest));

        GuestModel result = guestService.getGuestById(1L);

        assertEquals("Deepraj", result.getName());
        verify(guestRepository, times(1)).findById(1L);
    }

    @Test
    void testGetGuestById_NotFound() {
        when(guestRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> guestService.getGuestById(2L));
        assertEquals("Guest not found with ID: 2", exception.getMessage());
    }

    @Test
    void testGetGuestByMemberCode_Found() {
        when(guestRepository.findByMemberCode("MC123")).thenReturn(Optional.of(guest));

        GuestModel result = guestService.getGuestByMemberCode("MC123");

        assertEquals("Deepraj", result.getName());
    }

    @Test
    void testGetGuestByMemberCode_NotFound() {
        when(guestRepository.findByMemberCode("XYZ")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> guestService.getGuestByMemberCode("XYZ"));
        assertEquals("Guest not found with member code: XYZ", exception.getMessage());
    }

    @Test
    void testDeleteGuestById_Exists() {
        when(guestRepository.existsById(1L)).thenReturn(true);
        doNothing().when(guestRepository).deleteById(1L);

        guestService.deleteGuestById(1L);

        verify(guestRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteGuestById_NotExists() {
        when(guestRepository.existsById(2L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> guestService.deleteGuestById(2L));
        assertEquals("Guest not found with ID: 2", exception.getMessage());
    }

    @Test
    void testDeleteGuestByMemberCode() {
        when(guestRepository.findByMemberCode("MC123")).thenReturn(Optional.of(guest));
        doNothing().when(guestRepository).delete(guest);

        guestService.deleteGuestByMemberCode("MC123");

        verify(guestRepository, times(1)).delete(guest);
    }

    @Test
    void testUpdateGuest() {
        GuestModel updated = new GuestModel();
        updated.setMemberCode("MC456");
        updated.setPhoneNumber("9998887777");
        updated.setCompany("XYZ Ltd");
        updated.setName("sajay");
        updated.setEmail("sanjay@gmail.com");
        updated.setGender("male");
        updated.setAddress("456 Avenue");

        when(guestRepository.findById(1L)).thenReturn(Optional.of(guest));
        when(guestRepository.save(any())).thenReturn(updated);

        guestService.updateGuest(1L, updated);

        assertEquals("MC456", guest.getMemberCode());
        assertEquals("sajay", guest.getName());
        verify(guestRepository).save(guest);
    }
}
