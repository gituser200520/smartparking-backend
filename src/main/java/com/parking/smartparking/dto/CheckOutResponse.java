package com.parking.smartparking.dto;

import com.parking.smartparking.model.ParkingSlot;
import java.time.LocalDateTime;

/**
 * Response DTO for checkout — includes fee, duration, and receipt details.
 */
public class CheckOutResponse {

    private Long   id;
    private String slotNumber;
    private int    floor;
    private ParkingSlot.SlotType slotType;

    // Vehicle info
    private String vehicleNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    // Fee info
    private double hourlyRate;
    private String duration;
    private double totalFee;
    private String currency = "INR";
    private String currencySymbol = "₹";

    private boolean success;
    private String  message;

    public CheckOutResponse() {}

    // ── Getters & Setters ─────────────────────────────────
    public Long getId()                        { return id; }
    public void setId(Long id)                 { this.id = id; }

    public String getSlotNumber()                    { return slotNumber; }
    public void   setSlotNumber(String s)            { this.slotNumber = s; }

    public int  getFloor()                     { return floor; }
    public void setFloor(int f)                { this.floor = f; }

    public ParkingSlot.SlotType getSlotType()              { return slotType; }
    public void                 setSlotType(ParkingSlot.SlotType t) { this.slotType = t; }

    public String getVehicleNumber()                   { return vehicleNumber; }
    public void   setVehicleNumber(String v)           { this.vehicleNumber = v; }

    public LocalDateTime getEntryTime()                      { return entryTime; }
    public void          setEntryTime(LocalDateTime t)       { this.entryTime = t; }

    public LocalDateTime getExitTime()                       { return exitTime; }
    public void          setExitTime(LocalDateTime t)        { this.exitTime = t; }

    public double getHourlyRate()                    { return hourlyRate; }
    public void   setHourlyRate(double r)            { this.hourlyRate = r; }

    public String getDuration()                      { return duration; }
    public void   setDuration(String d)              { this.duration = d; }

    public double getTotalFee()                      { return totalFee; }
    public void   setTotalFee(double f)              { this.totalFee = f; }

    public String getCurrency()                      { return currency; }
    public void   setCurrency(String c)              { this.currency = c; }

    public String getCurrencySymbol()                { return currencySymbol; }
    public void   setCurrencySymbol(String s)        { this.currencySymbol = s; }

    public boolean isSuccess()                       { return success; }
    public void    setSuccess(boolean s)             { this.success = s; }

    public String getMessage()                       { return message; }
    public void   setMessage(String m)               { this.message = m; }
}
