package com.hotel.guest_service.controller;

import com.hotel.guest_service.model.GuestModel;
import com.hotel.guest_service.service.GuestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guest")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @PostMapping("/add")
    public ResponseEntity<String> addGuest(@Valid @RequestBody GuestModel guest) {
        guestService.addGuest(guest);
        return ResponseEntity.ok("Guest successfully added to the database.");
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<GuestModel> getGuestById(@PathVariable Long id) {
        GuestModel guest = guestService.getGuestById(id);
        return ResponseEntity.ok(guest);
    }

    @GetMapping("/get/memberCode/{memberCode}")
    public ResponseEntity<GuestModel> getGuestByMemberCode(@PathVariable String memberCode) {
        GuestModel guest = guestService.getGuestByMemberCode(memberCode);
        return ResponseEntity.ok(guest);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<String> deleteGuestById(@PathVariable Long id) {
        guestService.deleteGuestById(id);
        return ResponseEntity.ok("Guest deleted successfully.");
    }

    @DeleteMapping("/delete/memberCode/{memberCode}")
    public ResponseEntity<String> deleteGuestByMemberCode(@PathVariable String memberCode) {
        guestService.deleteGuestByMemberCode(memberCode);
        return ResponseEntity.ok("Guest with member code deleted successfully.");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateGuest(@PathVariable Long id, @Valid @RequestBody GuestModel updatedGuest) {
        guestService.updateGuest(id, updatedGuest);
        return ResponseEntity.ok("Guest updated successfully.");
    }
}
