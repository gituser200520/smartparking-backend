package com.parking.smartparking.config;

import com.parking.smartparking.model.ParkingSlot;
import com.parking.smartparking.repository.ParkingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner seedDatabase(ParkingRepository repo) {
        return args -> {
            if (repo.count() > 0) return;

            List<ParkingSlot> slots = new ArrayList<>();

            // Floor 1
            for (int i = 1; i <= 8; i++) {
                slots.add(new ParkingSlot(String.format("F1-%02d", i), 1, ParkingSlot.SlotType.REGULAR));
            }
            slots.add(new ParkingSlot("F1-H1", 1, ParkingSlot.SlotType.HANDICAPPED));
            slots.add(new ParkingSlot("F1-H2", 1, ParkingSlot.SlotType.HANDICAPPED));
            slots.add(new ParkingSlot("F1-E1", 1, ParkingSlot.SlotType.ELECTRIC));
            slots.add(new ParkingSlot("F1-E2", 1, ParkingSlot.SlotType.ELECTRIC));

            // Floor 2
            for (int i = 1; i <= 6; i++) {
                slots.add(new ParkingSlot(String.format("F2-%02d", i), 2, ParkingSlot.SlotType.REGULAR));
            }
            slots.add(new ParkingSlot("F2-V1", 2, ParkingSlot.SlotType.VIP));
            slots.add(new ParkingSlot("F2-V2", 2, ParkingSlot.SlotType.VIP));
            slots.add(new ParkingSlot("F2-V3", 2, ParkingSlot.SlotType.VIP));
            slots.add(new ParkingSlot("F2-E1", 2, ParkingSlot.SlotType.ELECTRIC));
            slots.add(new ParkingSlot("F2-E2", 2, ParkingSlot.SlotType.ELECTRIC));

            repo.saveAll(slots);
        };
    }
}
