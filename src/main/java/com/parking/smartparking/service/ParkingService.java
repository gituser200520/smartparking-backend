package com.parking.smartparking.service;

import com.parking.smartparking.dto.CheckInRequest;
import com.parking.smartparking.dto.CheckOutResponse;
import com.parking.smartparking.dto.ParkingResponse;
import com.parking.smartparking.dto.ParkingStatsResponse;
import com.parking.smartparking.exception.ParkingException;
import com.parking.smartparking.model.ParkingSlot;
import com.parking.smartparking.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ParkingService {

    private final ParkingRepository parkingRepository;
    private final ParkingFeeService feeService;

    @Autowired
    public ParkingService(ParkingRepository parkingRepository, ParkingFeeService feeService) {
        this.parkingRepository = parkingRepository;
        this.feeService        = feeService;
    }

    @Transactional(readOnly = true)
    public List<ParkingResponse> getAllSlots() {
        return parkingRepository.findAll().stream().map(ParkingResponse::fromSlot).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ParkingResponse getSlotById(Long id) {
        return ParkingResponse.fromSlot(parkingRepository.findById(id)
                .orElseThrow(() -> new ParkingException("Slot not found: " + id)));
    }

    @Transactional(readOnly = true)
    public ParkingResponse getSlotByNumber(String slotNumber) {
        return ParkingResponse.fromSlot(parkingRepository.findBySlotNumber(slotNumber.toUpperCase())
                .orElseThrow(() -> new ParkingException("Slot not found: " + slotNumber)));
    }

    @Transactional(readOnly = true)
    public List<ParkingResponse> getAvailableSlots() {
        return parkingRepository.findByOccupied(false).stream().map(ParkingResponse::fromSlot).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ParkingResponse> getOccupiedSlots() {
        return parkingRepository.findByOccupied(true).stream().map(ParkingResponse::fromSlot).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ParkingResponse> getSlotsByFloor(int floor) {
        return parkingRepository.findByFloor(floor).stream().map(ParkingResponse::fromSlot).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ParkingResponse> getSlotsByType(ParkingSlot.SlotType slotType) {
        return parkingRepository.findBySlotType(slotType).stream().map(ParkingResponse::fromSlot).collect(Collectors.toList());
    }

    public ParkingResponse checkIn(CheckInRequest request) {
        String vehicleNo = request.getVehicleNumber().toUpperCase().trim();
        if (parkingRepository.existsByVehicleNumber(vehicleNo))
            throw new ParkingException("Vehicle " + vehicleNo + " is already parked.");

        ParkingSlot.SlotType targetType = request.getSlotType() != null ? request.getSlotType() : ParkingSlot.SlotType.REGULAR;
        List<ParkingSlot> candidates = parkingRepository.findAvailableByTypeOrdered(targetType);
        if (candidates.isEmpty()) candidates = parkingRepository.findAllAvailableOrderedByFloorAndSlot();
        if (candidates.isEmpty()) throw new ParkingException("Parking lot is full.");

        ParkingSlot slot = candidates.get(0);
        slot.setOccupied(true);
        slot.setVehicleNumber(vehicleNo);
        slot.setEntryTime(LocalDateTime.now());
        ParkingSlot saved = parkingRepository.save(slot);
        return ParkingResponse.success(saved, "Vehicle " + vehicleNo + " parked at slot " + saved.getSlotNumber()
                + " on floor " + saved.getFloor() + ". Rate: ₹" + feeService.getHourlyRate(saved.getSlotType()) + "/hr");
    }

    public CheckOutResponse checkOut(String vehicleNumber) {
        String vehicleNo = vehicleNumber.toUpperCase().trim();
        ParkingSlot slot = parkingRepository.findByVehicleNumber(vehicleNo)
                .orElseThrow(() -> new ParkingException("Vehicle " + vehicleNo + " is not currently parked."));

        LocalDateTime entryTime = slot.getEntryTime();
        LocalDateTime exitTime  = LocalDateTime.now();
        double fee         = feeService.calculateFee(slot.getSlotType(), entryTime);
        String duration    = feeService.getDurationString(entryTime);
        double hourlyRate  = feeService.getHourlyRate(slot.getSlotType());

        CheckOutResponse res = new CheckOutResponse();
        res.setId(slot.getId());
        res.setSlotNumber(slot.getSlotNumber());
        res.setFloor(slot.getFloor());
        res.setSlotType(slot.getSlotType());
        res.setVehicleNumber(vehicleNo);
        res.setEntryTime(entryTime);
        res.setExitTime(exitTime);
        res.setHourlyRate(hourlyRate);
        res.setDuration(duration);
        res.setTotalFee(fee);
        res.setSuccess(true);
        res.setMessage("Vehicle " + vehicleNo + " checked out. Total fee: ₹" + fee);

        slot.setOccupied(false);
        slot.setVehicleNumber(null);
        slot.setEntryTime(null);
        parkingRepository.save(slot);
        return res;
    }

    @Transactional(readOnly = true)
    public ParkingResponse findVehicle(String vehicleNumber) {
        String vehicleNo = vehicleNumber.toUpperCase().trim();
        ParkingSlot slot = parkingRepository.findByVehicleNumber(vehicleNo)
                .orElseThrow(() -> new ParkingException("Vehicle " + vehicleNo + " is not currently parked."));
        return ParkingResponse.success(slot, "Vehicle found at slot " + slot.getSlotNumber() + " on floor " + slot.getFloor() + ".");
    }

    @Transactional(readOnly = true)
    public ParkingStatsResponse getStats() {
        long total = parkingRepository.count();
        long occupied = parkingRepository.countOccupiedSlots();
        long available = parkingRepository.countAvailableSlots();
        Map<String, Long> byType = new HashMap<>();
        for (Object[] row : parkingRepository.countGroupBySlotType()) byType.put(row[0].toString(), (Long) row[1]);
        Map<Integer, Long> byFloor = new HashMap<>();
        for (Object[] row : parkingRepository.countGroupByFloor()) byFloor.put(((Number) row[0]).intValue(), (Long) row[1]);
        return new ParkingStatsResponse(total, occupied, available, byType, byFloor);
    }
}
