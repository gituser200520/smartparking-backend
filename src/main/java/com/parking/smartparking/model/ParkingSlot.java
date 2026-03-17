package com.parking.smartparking.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking_slots")
public class ParkingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "slot_number", nullable = false, unique = true, length = 20)
    private String slotNumber;

    @Column(name = "is_occupied", nullable = false)
    private boolean occupied = false;

    @Column(name = "vehicle_number", length = 20)
    private String vehicleNumber;

    @Column(name = "entry_time")
    private LocalDateTime entryTime;

    @Column(name = "floor", nullable = false)
    private int floor;

    @Enumerated(EnumType.STRING)
    @Column(name = "slot_type", nullable = false, length = 20)
    private SlotType slotType;

    public enum SlotType {
        REGULAR, HANDICAPPED, ELECTRIC, VIP
    }

    public ParkingSlot() {}

    public ParkingSlot(String slotNumber, int floor, SlotType slotType) {
        this.slotNumber = slotNumber;
        this.floor = floor;
        this.slotType = slotType;
        this.occupied = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSlotNumber() { return slotNumber; }
    public void setSlotNumber(String slotNumber) { this.slotNumber = slotNumber; }

    public boolean isOccupied() { return occupied; }
    public void setOccupied(boolean occupied) { this.occupied = occupied; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public LocalDateTime getEntryTime() { return entryTime; }
    public void setEntryTime(LocalDateTime entryTime) { this.entryTime = entryTime; }

    public int getFloor() { return floor; }
    public void setFloor(int floor) { this.floor = floor; }

    public SlotType getSlotType() { return slotType; }
    public void setSlotType(SlotType slotType) { this.slotType = slotType; }
}
