package com.parking.smartparking.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String vehicleNumber;

    @Column(nullable = false, length = 20)
    private String slotNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ParkingSlot.SlotType slotType;

    @Column(nullable = false)
    private int floor;

    @Column(nullable = false)
    private LocalDateTime reservedAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private boolean active = true;

    public Reservation() {}

    public Reservation(String vehicleNumber, String slotNumber, ParkingSlot.SlotType slotType, int floor) {
        this.vehicleNumber = vehicleNumber;
        this.slotNumber = slotNumber;
        this.slotType = slotType;
        this.floor = floor;
        this.reservedAt = LocalDateTime.now();
        this.expiresAt = LocalDateTime.now().plusMinutes(15);
        this.active = true;
    }

    public Long getId() { return id; }
    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String v) { this.vehicleNumber = v; }
    public String getSlotNumber() { return slotNumber; }
    public void setSlotNumber(String s) { this.slotNumber = s; }
    public ParkingSlot.SlotType getSlotType() { return slotType; }
    public void setSlotType(ParkingSlot.SlotType t) { this.slotType = t; }
    public int getFloor() { return floor; }
    public void setFloor(int f) { this.floor = f; }
    public LocalDateTime getReservedAt() { return reservedAt; }
    public void setReservedAt(LocalDateTime t) { this.reservedAt = t; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime t) { this.expiresAt = t; }
    public boolean isActive() { return active; }
    public void setActive(boolean a) { this.active = a; }
}