package com.hotel.staff_service.repository;

import com.hotel.staff_service.model.StaffModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<StaffModel,Long> {
}
