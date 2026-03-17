package com.parking.smartparking.controller;
import com.parking.smartparking.dto.CheckInRequest;
import com.parking.smartparking.dto.CheckOutResponse;
import com.parking.smartparking.dto.ParkingResponse;
import com.parking.smartparking.dto.ParkingStatsResponse;
import com.parking.smartparking.model.ParkingSlot;
import com.parking.smartparking.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;
import com.parking.smartparking.service.ReservationService;
import java.util.Map;

@RestController
@RequestMapping("/api/parking")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class ParkingController {

    private final ParkingService parkingService;
    @Autowired
    private ReservationService reservationService;

    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("/slots")
    public ResponseEntity<List<ParkingResponse>> getAllSlots() {
        return ResponseEntity.ok(parkingService.getAllSlots());
    }

    @GetMapping("/slots/{id}")
    public ResponseEntity<ParkingResponse> getSlotById(@PathVariable Long id) {
        return ResponseEntity.ok(parkingService.getSlotById(id));
    }

    @GetMapping("/slots/number/{slotNumber}")
    public ResponseEntity<ParkingResponse> getSlotByNumber(@PathVariable String slotNumber) {
        return ResponseEntity.ok(parkingService.getSlotByNumber(slotNumber));
    }

    @GetMapping("/slots/available")
    public ResponseEntity<List<ParkingResponse>> getAvailableSlots() {
        return ResponseEntity.ok(parkingService.getAvailableSlots());
    }

    @GetMapping("/slots/occupied")
    public ResponseEntity<List<ParkingResponse>> getOccupiedSlots() {
        return ResponseEntity.ok(parkingService.getOccupiedSlots());
    }

    @GetMapping("/slots/floor/{floor}")
    public ResponseEntity<List<ParkingResponse>> getSlotsByFloor(@PathVariable int floor) {
        return ResponseEntity.ok(parkingService.getSlotsByFloor(floor));
    }

    @GetMapping("/slots/type/{type}")
    public ResponseEntity<List<ParkingResponse>> getSlotsByType(@PathVariable ParkingSlot.SlotType type) {
        return ResponseEntity.ok(parkingService.getSlotsByType(type));
    }

    @PostMapping("/checkin")
    public ResponseEntity<ParkingResponse> checkIn(@RequestBody CheckInRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingService.checkIn(request));
    }

    @PostMapping("/checkout/{vehicleNumber}")
    public ResponseEntity<CheckOutResponse> checkOut(@PathVariable String vehicleNumber) {
        return ResponseEntity.ok(parkingService.checkOut(vehicleNumber));
    }

    @GetMapping("/find/{vehicleNumber}")
    public ResponseEntity<ParkingResponse> findVehicle(@PathVariable String vehicleNumber) {
        return ResponseEntity.ok(parkingService.findVehicle(vehicleNumber));
    }

    @GetMapping("/stats")
    public ResponseEntity<ParkingStatsResponse> getStats() {
        return ResponseEntity.ok(parkingService.getStats());
    }

    @PostMapping("/reserve")
    public ResponseEntity<Map<String, Object>> reserve(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(reservationService.createReservation(
                body.get("vehicleNumber"), body.get("slotType")));
    }

    @GetMapping("/reserve/{vehicleNumber}")
    public ResponseEntity<Map<String, Object>> getReservation(@PathVariable String vehicleNumber) {
        return ResponseEntity.ok(reservationService.getReservation(vehicleNumber));
    }

    @DeleteMapping("/reserve/{vehicleNumber}")
    public ResponseEntity<Map<String, Object>> cancelReservation(@PathVariable String vehicleNumber) {
        return ResponseEntity.ok(reservationService.cancelReservation(vehicleNumber));
    }

    @GetMapping("/reserve/all")
    public ResponseEntity<?> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllActive());
    }
}
