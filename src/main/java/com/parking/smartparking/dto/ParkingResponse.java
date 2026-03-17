package com.parking.smartparking.dto;

import com.parking.smartparking.model.ParkingSlot;
import java.time.LocalDateTime;

public class ParkingResponse {

    private Long id;
    private String slotNumber;
    private Boolean occupied;
    private String vehicleNumber;
    private LocalDateTime entryTime;
    private Integer floor;
    private ParkingSlot.SlotType slotType;
    private boolean success;
    private String message;
    private LocalDateTime timestamp;

    public static ParkingResponse success(ParkingSlot slot, String message) {
        ParkingResponse r = fromSlot(slot);
        r.success = true;
        r.message = message;
        r.timestamp = LocalDateTime.now();
        return r;
    }

    public static ParkingResponse error(String message) {
        ParkingResponse r = new ParkingResponse();
        r.success = false;
        r.message = message;
        r.timestamp = LocalDateTime.now();
        return r;
    }

    public static ParkingResponse fromSlot(ParkingSlot slot) {
        ParkingResponse r = new ParkingResponse();
        r.id = slot.getId();
        r.slotNumber = slot.getSlotNumber();
        r.occupied = slot.isOccupied();
        r.vehicleNumber = slot.getVehicleNumber();
        r.entryTime = slot.getEntryTime();
        r.floor = slot.getFloor();
        r.slotType = slot.getSlotType();
        return r;
    }

    public ParkingResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSlotNumber() { return slotNumber; }
    public void setSlotNumber(String slotNumber) { this.slotNumber = slotNumber; }

    public Boolean isOccupied() { return occupied; }
    public void setOccupied(Boolean occupied) { this.occupied = occupied; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public LocalDateTime getEntryTime() { return entryTime; }
    public void setEntryTime(LocalDateTime entryTime) { this.entryTime = entryTime; }

    public Integer getFloor() { return floor; }
    public void setFloor(Integer floor) { this.floor = floor; }

    public ParkingSlot.SlotType getSlotType() { return slotType; }
    public void setSlotType(ParkingSlot.SlotType slotType) { this.slotType = slotType; }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
