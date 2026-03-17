package com.parking.smartparking.dto;

import com.parking.smartparking.model.ParkingSlot;

public class CheckInRequest {

    private String vehicleNumber;
    private ParkingSlot.SlotType slotType;

    public CheckInRequest() {}

    public CheckInRequest(String vehicleNumber, ParkingSlot.SlotType slotType) {
        this.vehicleNumber = vehicleNumber;
        this.slotType = slotType;
    }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public ParkingSlot.SlotType getSlotType() { return slotType; }
    public void setSlotType(ParkingSlot.SlotType slotType) { this.slotType = slotType; }
}
