package com.example.charging.domain;

import com.example.charging.adapters.ChargingSessionRepository;
// dependency to adapters! -
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
// where are the tests -
public class ChargingService {

    // why not use interface?
    private final ChargingSessionRepository sessionRepository;

    // wstrzyknięcie zależności
    public ChargingService(ChargingSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Mono<ChargingSession> startSession(String stationId, String vehicleId) {
        ChargingSession session = new ChargingSession();
        // why not use builder pattern or factory method? +
        session.setStationId(stationId);
        session.setVehicleId(vehicleId);
        session.setStatus(ChargingSession.ChargingStatus.IN_PROGRESS);
        session.setEnergyConsumed(0.0); // default?

        // error handling +
        // sugeruje try-catch -
        // "z mono tak dużo nie pracowałem" - nie ma problemu, to jest związane z reaktywnością
        return sessionRepository.save(session).then(Mono.just(session)); // why void and no object returned? forces use of Mono.just
    }

    // energy consumed od pobrania zamiast w requeście
    public Mono<ChargingSession> completeSession(String sessionId, double energyConsumed) {
        return sessionRepository.findById(sessionId)
                // nie zna API Mono
                .flatMap(session -> {
                    // domain method encapsulation
                    session.setEnergyConsumed(energyConsumed);
                    session.setStatus(ChargingSession.ChargingStatus.COMPLETED);
                    return sessionRepository.save(session).then(Mono.just(session));
                }); // error handling?
    }
}
// testy -
// widzi że nie ma opisu -
// zauważył brancha -
// dokumentacja -
// struktura endpointu: sprawdził ale nie miał uwag -
// pyta zanim napisze komentarz: plus bo komunikatywny, minus bo się boi napisać i skorzystać z narzędzia
