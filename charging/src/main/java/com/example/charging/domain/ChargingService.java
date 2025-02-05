package com.example.charging.domain;

import com.example.charging.adapters.ChargingSessionRepository;
// dependency to adapters!
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
// where are the tests
public class ChargingService {

    // why not use interface?
    private final ChargingSessionRepository sessionRepository;

    // wstrzyknięcie zależności
    public ChargingService(ChargingSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Mono<ChargingSession> startSession(String stationId, String vehicleId) {
        ChargingSession session = new ChargingSession();
        // why not use builder pattern or factory method?
        session.setStationId(stationId);
        session.setVehicleId(vehicleId);
        session.setStatus(ChargingSession.ChargingStatus.IN_PROGRESS);
        session.setEnergyConsumed(0.0); // default?

        return sessionRepository.save(session).then(Mono.just(session)); // why void and no object returned? forces use of Mono.just
    }

    // energy consumed od pobrania zamiast w requeście
    public Mono<ChargingSession> completeSession(String sessionId, double energyConsumed) {
        return sessionRepository.findById(sessionId)
                .flatMap(session -> {
                    // domain method encapsulation +
                    session.setEnergyConsumed(energyConsumed);
                    session.setStatus(ChargingSession.ChargingStatus.COMPLETED);
                    return sessionRepository.save(session).then(Mono.just(session));
                }); // error handling?
    }
}
// testy
// widzi że nie ma opisu
// zauważył brancha
// dokumentacja
// struktura endpointu
