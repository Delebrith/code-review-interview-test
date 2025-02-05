package com.example.charging.domain;

// nie widzi że to obiekt domenowy. Szuka klucza bazodanowego w domenie!
// no lombok?
// private!
public class ChargingSession {
    // dlaczego to stringi - sugeruje UUID lub value object z session id dla klarowności
    public String sessionId; // just id?
    public String stationId; // why not use object?
    public String vehicleId; // why not use object?
    public ChargingStatus status;
    public Double energyConsumed; // in kWh
    // could be an object with unit inside
    // sugeruje integer dla najmniejszej jednostki?

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public ChargingStatus getStatus() {
        return status;
    }

    public void setStatus(ChargingStatus status) {
        this.status = status;
    }

    public Double getEnergyConsumed() {
        return energyConsumed;
    }

    // zauważa za dużo setterów
    public void setEnergyConsumed(Double energyConsumed) {
        this.energyConsumed = energyConsumed;
    }

    // simply status
    // brak walidacji
    // współdzielenie enuma - whyyyyy
    public enum ChargingStatus {
        IN_PROGRESS,
        COMPLETED,
        CANCELLED
    }
}
