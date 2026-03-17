package com.parking.smartparking.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ParkingStatsResponse {

    private long totalSlots;
    private long occupiedSlots;
    private long availableSlots;
    private double occupancyRate;
    private Map<String, Long> slotsByType;
    private Map<Integer, Long> slotsByFloor;
    private LocalDateTime generatedAt;

    public ParkingStatsResponse() {}

    public ParkingStatsResponse(long total, long occupied, long available,
                                Map<String, Long> slotsByType,
                                Map<Integer, Long> slotsByFloor) {
        this.totalSlots = total;
        this.occupiedSlots = occupied;
        this.availableSlots = available;
        this.occupancyRate = total > 0 ? Math.round((double) occupied / total * 10000.0) / 100.0 : 0.0;
        this.slotsByType = slotsByType;
        this.slotsByFloor = slotsByFloor;
        this.generatedAt = LocalDateTime.now();
    }

    public long getTotalSlots() { return totalSlots; }
    public void setTotalSlots(long totalSlots) { this.totalSlots = totalSlots; }

    public long getOccupiedSlots() { return occupiedSlots; }
    public void setOccupiedSlots(long occupiedSlots) { this.occupiedSlots = occupiedSlots; }

    public long getAvailableSlots() { return availableSlots; }
    public void setAvailableSlots(long availableSlots) { this.availableSlots = availableSlots; }

    public double getOccupancyRate() { return occupancyRate; }
    public void setOccupancyRate(double occupancyRate) { this.occupancyRate = occupancyRate; }

    public Map<String, Long> getSlotsByType() { return slotsByType; }
    public void setSlotsByType(Map<String, Long> slotsByType) { this.slotsByType = slotsByType; }

    public Map<Integer, Long> getSlotsByFloor() { return slotsByFloor; }
    public void setSlotsByFloor(Map<Integer, Long> slotsByFloor) { this.slotsByFloor = slotsByFloor; }

    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
}
