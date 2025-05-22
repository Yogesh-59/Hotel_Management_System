package com.hotel.guest_service.service;

import com.hotel.guest_service.model.GuestModel;
import com.hotel.guest_service.repository.GuestRepository;
import org.springframework.stereotype.Service;

@Service
public class GuestService {

    private final GuestRepository guestRepository;
    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public GuestModel addGuest(GuestModel guest) {
        return guestRepository.save(guest);
    }

    public GuestModel getGuestById(Long id) {
        return guestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guest not found with ID: " + id));
    }

    public GuestModel getGuestByMemberCode(String memberCode) {
        return guestRepository.findByMemberCode(memberCode)
                .orElseThrow(() -> new RuntimeException("Guest not found with member code: " + memberCode));
    }

    public void deleteGuestById(Long id) {
        if (!guestRepository.existsById(id)) {
            throw new RuntimeException("Guest not found with ID: " + id);
        }
        guestRepository.deleteById(id);
    }

    public void deleteGuestByMemberCode(String memberCode) {
        GuestModel guest = getGuestByMemberCode(memberCode);
        guestRepository.delete(guest);
    }

    public void updateGuest(Long id, GuestModel updatedGuest) {
        GuestModel existingGuest = getGuestById(id);

        existingGuest.setMemberCode(updatedGuest.getMemberCode());
        existingGuest.setPhoneNumber(updatedGuest.getPhoneNumber());
        existingGuest.setCompany(updatedGuest.getCompany());
        existingGuest.setName(updatedGuest.getName());
        existingGuest.setEmail(updatedGuest.getEmail());
        existingGuest.setGender(updatedGuest.getGender());
        existingGuest.setAddress(updatedGuest.getAddress());

        guestRepository.save(existingGuest);
    }
}
