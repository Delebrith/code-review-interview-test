package com.example.charging.api;

import com.example.charging.domain.ChargingService;
import com.example.charging.domain.ChargingSession;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/charging-sessions") // no prefix, no versioning
// no documentation
public class ChargingController {
    private final ChargingService chargingService;

    public ChargingController(ChargingService chargingService) {
        this.chargingService = chargingService;
    }

    @PostMapping // sugeruje modyfikację na /start-session, ignoruje znaczenie operacji restowych
    // request params +
    // sugeruje wprowadzenie obiektu na warstwie API. "tworzę oddzielne obiekty do api, do logiki i do bazy"
    public Mono<ChargingSession> startSession(@RequestParam String stationId, @RequestParam String vehicleId) {
        return chargingService.startSession(stationId, vehicleId);
    }

    @PostMapping("/{sessionId}/complete")
    // request body? where is validation? - "typ prosty double do zmiany?"
    // znalazł że może  nie powinna być to informacja z zewnątrz!
    public Mono<ChargingSession> completeSession(@PathVariable String sessionId, @RequestParam double energyConsumed) {
        return chargingService.completeSession(sessionId, energyConsumed);
    }
}
