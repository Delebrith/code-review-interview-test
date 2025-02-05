package com.example.charging.domain;

import com.example.charging.adapters.ChargingSessionRepository;
// dependency to adapters! -
// nie patrzy w strukturę katalogów
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
        ChargingSession session = new ChargingSession(); // wciąż uważa że to encja >////<
        // why not use builder pattern or factory method? +
        // sugeruje walidację przy tworzeniu obiektu domenowego
        session.setStationId(stationId);
        session.setVehicleId(vehicleId);
        session.setStatus(ChargingSession.ChargingStatus.IN_PROGRESS);
        session.setEnergyConsumed(0.0); // default? +

        return sessionRepository.save(session).then(Mono.just(session)); // why void and no object returned? forces use of Mono.just +
    }

    // sugeruje transakcje +
    // energy consumed od pobrania zamiast w requeście - poniekąd znalazł na poziomie controllera
    public Mono<ChargingSession> completeSession(String sessionId, double energyConsumed) {
        return sessionRepository.findById(sessionId)
                .flatMap(session -> {
                    // domain method encapsulation -
                    session.setEnergyConsumed(energyConsumed);
                    session.setStatus(ChargingSession.ChargingStatus.COMPLETED);
                    return sessionRepository.save(session).then(Mono.just(session)); // + wyciągnąć z mapy
                }); // error handling?
    }
}
// testy -
// widzi że nie ma opisu -
// zauważył brancha -
// dokumentacja -
// struktura endpointu +
// skupia się na tym czy się skompiluje ????
// sugeruje rezygnację z reaktora +