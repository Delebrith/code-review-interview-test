package com.example.charging.adapters;

import com.example.charging.domain.ChargingSession;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component // charge to repository +
// no interface implemented
// no db migration files
public class ChargingSessionRepository {

    // why use entity template when we can implement R2dbcRepository?
    private final R2dbcEntityTemplate entityTemplate;

    public ChargingSessionRepository(R2dbcEntityTemplate entityTemplate) {
        this.entityTemplate = entityTemplate;
    }

    public Mono<ChargingSession> findById(String sessionId) {
        // no separate object for data layer with data validation
        // widzi brak walidacji i rzeczy bazodanowych +
        return entityTemplate.select(ChargingSession.class)
                .matching(Query.query(Criteria.where("sessionId").is(sessionId)))
                .one();
    }

    // widzi że warto zwrócić obiekt +
    public Mono<Void> save(ChargingSession session) {
        return entityTemplate.insert(session).then();
    }
}
