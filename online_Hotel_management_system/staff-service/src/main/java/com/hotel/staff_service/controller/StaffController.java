package com.hotel.staff_service.controller;

import com.hotel.staff_service.dto.ReservationDTO;
import com.hotel.staff_service.dto.RoomDTO;
import com.hotel.staff_service.dto.StaffDTO;
import com.hotel.staff_service.model.StaffModel;
import com.hotel.staff_service.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    // Add a new staff member
    @PostMapping("/add")
    public ResponseEntity<String> addStaff(@RequestBody StaffDTO staffDTO) {
        String result = staffService.addStaff(staffDTO);
        return ResponseEntity.ok(result);
    }

    // Get a staff member by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<StaffModel> getStaffById(@PathVariable Long id) {
        StaffModel staff = staffService.getStaffById(id);
        return ResponseEntity.ok(staff);
    }

    // Update staff member
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateStaff(@PathVariable Long id, @RequestBody StaffDTO staffDTO) {
        staffService.updateStaff(id, staffDTO);
        return ResponseEntity.ok("Staff member updated successfully.");
    }

    // Delete staff member
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.ok("Staff member deleted successfully.");
    }

    // Get reservations handled by a staff member (via Feign client)
    @GetMapping("/{staffId}/reservations")
    public ResponseEntity<List<ReservationDTO>> getStaffReservations(@PathVariable Long staffId) {
        List<ReservationDTO> reservations = staffService.getStaffReservations(staffId);
        return ResponseEntity.ok(reservations);
    }

    // Get rooms managed by a staff member (via Feign client)
    @GetMapping("/{staffId}/rooms")
    public ResponseEntity<List<RoomDTO>> getManagedRooms(@PathVariable Long staffId) {
        List<RoomDTO> rooms = staffService.getManagedRooms(staffId);
        return ResponseEntity.ok(rooms);
    }
}
