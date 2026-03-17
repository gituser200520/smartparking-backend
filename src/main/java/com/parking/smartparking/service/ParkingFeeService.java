package com.parking.smartparking.service;

import com.parking.smartparking.model.ParkingSlot;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Calculates parking fees based on slot type and duration.
 *
 * Rates (per hour):
 *   REGULAR     → ₹20 / hr
 *   HANDICAPPED → ₹10 / hr (discounted)
 *   ELECTRIC    → ₹30 / hr
 *   VIP         → ₹50 / hr
 *
 * Minimum charge: 1 hour
 * Billing is rounded up to the next hour.
 */
@Service
public class ParkingFeeService {

    public static final double RATE_REGULAR     = 20.0;
    public static final double RATE_HANDICAPPED = 10.0;
    public static final double RATE_ELECTRIC    = 30.0;
    public static final double RATE_VIP         = 50.0;

    /** Returns hourly rate for a given slot type */
    public double getHourlyRate(ParkingSlot.SlotType slotType) {
        return switch (slotType) {
            case HANDICAPPED -> RATE_HANDICAPPED;
            case ELECTRIC    -> RATE_ELECTRIC;
            case VIP         -> RATE_VIP;
            default          -> RATE_REGULAR;
        };
    }

    /** Calculates total fee from entry time to now */
    public double calculateFee(ParkingSlot.SlotType slotType, LocalDateTime entryTime) {
        if (entryTime == null) return 0.0;
        long minutes  = Duration.between(entryTime, LocalDateTime.now()).toMinutes();
        long hours    = (long) Math.ceil(minutes / 60.0); // round up
        if (hours < 1) hours = 1;                         // minimum 1 hour
        double rate   = getHourlyRate(slotType);
        return Math.round(hours * rate * 100.0) / 100.0;
    }

    /** Returns duration string like "2 hrs 30 mins" */
    public String getDurationString(LocalDateTime entryTime) {
        if (entryTime == null) return "N/A";
        Duration d    = Duration.between(entryTime, LocalDateTime.now());
        long hours    = d.toHours();
        long mins     = d.toMinutesPart();
        if (hours == 0) return mins + " mins";
        return hours + " hr" + (hours > 1 ? "s" : "") + " " + mins + " mins";
    }
}
