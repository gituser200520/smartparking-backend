package com.parking.smartparking.repository;

import com.parking.smartparking.model.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingRepository extends JpaRepository<ParkingSlot, Long> {

    Optional<ParkingSlot> findBySlotNumber(String slotNumber);
    Optional<ParkingSlot> findByVehicleNumber(String vehicleNumber);
    List<ParkingSlot> findByOccupied(boolean occupied);
    List<ParkingSlot> findByFloor(int floor);
    List<ParkingSlot> findBySlotType(ParkingSlot.SlotType slotType);
    List<ParkingSlot> findByOccupiedAndSlotType(boolean occupied, ParkingSlot.SlotType slotType);
    boolean existsBySlotNumber(String slotNumber);
    boolean existsByVehicleNumber(String vehicleNumber);

    @Query("SELECT COUNT(p) FROM ParkingSlot p WHERE p.occupied = false")
    long countAvailableSlots();

    @Query("SELECT COUNT(p) FROM ParkingSlot p WHERE p.occupied = true")
    long countOccupiedSlots();

    @Query("SELECT p FROM ParkingSlot p WHERE p.occupied = false ORDER BY p.floor ASC, p.slotNumber ASC")
    List<ParkingSlot> findAllAvailableOrderedByFloorAndSlot();

    @Query("SELECT p FROM ParkingSlot p WHERE p.occupied = false AND p.slotType = :type ORDER BY p.floor ASC, p.slotNumber ASC")
    List<ParkingSlot> findAvailableByTypeOrdered(@Param("type") ParkingSlot.SlotType type);

    @Query("SELECT p.slotType, COUNT(p) FROM ParkingSlot p GROUP BY p.slotType")
    List<Object[]> countGroupBySlotType();

    @Query("SELECT p.floor, COUNT(p) FROM ParkingSlot p GROUP BY p.floor")
    List<Object[]> countGroupByFloor();
}
