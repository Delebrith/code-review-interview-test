package com.example.charging.domain;

import com.example.charging.adapters.ChargingSessionRepository;
// dependency to adapters!
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ChargingService {
    private final ChargingSessionRepository sessionRepository;

    public ChargingService(ChargingSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Mono<ChargingSession> startSession(String stationId, String vehicleId) {
        ChargingSession session = new ChargingSession();
        // why not use builder pattern?
        session.setStationId(stationId);
        session.setVehicleId(vehicleId);
        session.setStatus(ChargingSession.ChargingStatus.IN_PROGRESS);
        session.setEnergyConsumed(0.0); // default?

        return sessionRepository.save(session).then(Mono.just(session));
    }

    public Mono<ChargingSession> completeSession(String sessionId, double energyConsumed) {
        return sessionRepository.findById(sessionId)
                .flatMap(session -> {
                    // domain method encapsulation
                    session.setEnergyConsumed(energyConsumed);
                    session.setStatus(ChargingSession.ChargingStatus.COMPLETED);
                    return sessionRepository.save(session).then(Mono.just(session));
                });
    }
}
