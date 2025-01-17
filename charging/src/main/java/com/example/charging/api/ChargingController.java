package com.example.charging.api;

import com.example.charging.domain.ChargingService;
import com.example.charging.domain.ChargingSession;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/charging-sessions")
public class ChargingController {
    private final ChargingService chargingService;

    public ChargingController(ChargingService chargingService) {
        this.chargingService = chargingService;
    }

    @PostMapping
    public Mono<ChargingSession> startSession(@RequestParam String stationId, @RequestParam String vehicleId) {
        return chargingService.startSession(stationId, vehicleId);
    }

    @PostMapping("/{sessionId}/complete")
    public Mono<ChargingSession> completeSession(@PathVariable String sessionId, @RequestParam double energyConsumed) {
        return chargingService.completeSession(sessionId, energyConsumed);
    }
}
