package com.hotel.staff_service.service;

import com.hotel.staff_service.dto.ReservationDTO;
import com.hotel.staff_service.dto.RoomDTO;
import com.hotel.staff_service.dto.StaffDTO;
import com.hotel.staff_service.feign.ReservationServiceClient;
import com.hotel.staff_service.feign.RoomServiceClient;
import com.hotel.staff_service.model.StaffModel;
import com.hotel.staff_service.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private ReservationServiceClient reservationClient;

    @Autowired
    private RoomServiceClient roomClient;

    public List<ReservationDTO> getStaffReservations(Long staffId) {
        return reservationClient.getReservationsByStaffId(staffId);
    }

    public List<RoomDTO> getManagedRooms(Long staffId) {
        return roomClient.getRoomsManagedByStaff(staffId);
    }

    public String addStaff(StaffDTO dto) {
        StaffModel staff = new StaffModel(
                null,
                dto.getCode(),
                dto.getEmployeeName(),
                dto.getEmployeeAddress(),
                dto.getNic(),
                dto.getSalary(),
                dto.getAge(),
                dto.getOccupation(),
                dto.getEmail()
        );
        staffRepository.save(staff);
        return "Staff member successfully added.";
    }

    public StaffModel getStaffById(Long id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff member not found with ID: " + id));
    }

    public void deleteStaff(Long id) {
        if (!staffRepository.existsById(id)) {
            throw new RuntimeException("Staff member not found with ID: " + id);
        }
        staffRepository.deleteById(id);
    }

    public void updateStaff(Long id, StaffDTO updatedDto) {
        StaffModel existing = getStaffById(id);

        existing.setCode(updatedDto.getCode());
        existing.setEmployeeName(updatedDto.getEmployeeName());
        existing.setEmployeeAddress(updatedDto.getEmployeeAddress());
        existing.setNic(updatedDto.getNic());
        existing.setSalary(updatedDto.getSalary());
        existing.setAge(updatedDto.getAge());
        existing.setOccupation(updatedDto.getOccupation());
        existing.setEmail(updatedDto.getEmail());

        staffRepository.save(existing);
    }
}
