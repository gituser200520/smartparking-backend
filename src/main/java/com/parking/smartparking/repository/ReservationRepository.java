package com.parking.smartparking.repository;

import com.parking.smartparking.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByVehicleNumberAndActiveTrue(String vehicleNumber);
    List<Reservation> findByActiveTrueAndExpiresAtBefore(LocalDateTime time);
    boolean existsBySlotNumberAndActiveTrue(String slotNumber);
}