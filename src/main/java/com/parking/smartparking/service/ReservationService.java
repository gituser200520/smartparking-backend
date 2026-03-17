package com.parking.smartparking.service;

import com.parking.smartparking.model.ParkingSlot;
import com.parking.smartparking.model.Reservation;
import com.parking.smartparking.repository.ParkingRepository;
import com.parking.smartparking.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired private ReservationRepository reservationRepo;
    @Autowired private ParkingRepository parkingRepo;

    public Map<String, Object> createReservation(String vehicleNumber, String slotType) {
        Map<String, Object> result = new HashMap<>();

        // Check if vehicle already has active reservation
        if (reservationRepo.findByVehicleNumberAndActiveTrue(vehicleNumber).isPresent()) {
            result.put("success", false);
            result.put("message", "Vehicle already has an active reservation");
            return result;
        }

        // Find available slot of requested type
        ParkingSlot.SlotType type;
        try { type = ParkingSlot.SlotType.valueOf(slotType); }
        catch (Exception e) { type = ParkingSlot.SlotType.REGULAR; }

        final ParkingSlot.SlotType finalType = type;
        Optional<ParkingSlot> slotOpt = parkingRepo.findAll().stream()
                .filter(s -> s.getSlotType() == finalType && !s.isOccupied()
                        && !reservationRepo.existsBySlotNumberAndActiveTrue(s.getSlotNumber()))
                .findFirst();

        if (slotOpt.isEmpty()) {
            result.put("success", false);
            result.put("message", "No available " + slotType + " slots for reservation");
            return result;
        }

        ParkingSlot slot = slotOpt.get();
        Reservation reservation = new Reservation(vehicleNumber, slot.getSlotNumber(), slot.getSlotType(), slot.getFloor());
        reservationRepo.save(reservation);

        result.put("success", true);
        result.put("message", "Slot reserved successfully! You have 15 minutes to arrive.");
        result.put("slotNumber", slot.getSlotNumber());
        result.put("floor", slot.getFloor());
        result.put("slotType", slot.getSlotType());
        result.put("reservedAt", reservation.getReservedAt());
        result.put("expiresAt", reservation.getExpiresAt());
        result.put("vehicleNumber", vehicleNumber);
        return result;
    }

    public Map<String, Object> getReservation(String vehicleNumber) {
        Map<String, Object> result = new HashMap<>();
        Optional<Reservation> res = reservationRepo.findByVehicleNumberAndActiveTrue(vehicleNumber);
        if (res.isEmpty()) {
            result.put("success", false);
            result.put("message", "No active reservation found");
            return result;
        }
        Reservation r = res.get();
        long secondsLeft = java.time.Duration.between(LocalDateTime.now(), r.getExpiresAt()).getSeconds();
        result.put("success", true);
        result.put("slotNumber", r.getSlotNumber());
        result.put("floor", r.getFloor());
        result.put("slotType", r.getSlotType());
        result.put("reservedAt", r.getReservedAt());
        result.put("expiresAt", r.getExpiresAt());
        result.put("vehicleNumber", r.getVehicleNumber());
        result.put("secondsLeft", Math.max(0, secondsLeft));
        return result;
    }

    public Map<String, Object> cancelReservation(String vehicleNumber) {
        Map<String, Object> result = new HashMap<>();
        Optional<Reservation> res = reservationRepo.findByVehicleNumberAndActiveTrue(vehicleNumber);
        if (res.isEmpty()) {
            result.put("success", false);
            result.put("message", "No active reservation found");
            return result;
        }
        Reservation r = res.get();
        r.setActive(false);
        reservationRepo.save(r);
        result.put("success", true);
        result.put("message", "Reservation cancelled successfully");
        return result;
    }

    // Auto-expire reservations every minute
    @Scheduled(fixedRate = 60000)
    public void expireReservations() {
        List<Reservation> expired = reservationRepo.findByActiveTrueAndExpiresAtBefore(LocalDateTime.now());
        expired.forEach(r -> r.setActive(false));
        reservationRepo.saveAll(expired);
    }

    public List<Reservation> getAllActive() {
        return reservationRepo.findAll().stream()
                .filter(Reservation::isActive)
                .filter(r -> r.getExpiresAt().isAfter(LocalDateTime.now()))
                .toList();
    }
}