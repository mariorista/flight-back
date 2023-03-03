package mar.cod.flightback.entities.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import mar.cod.flightback.utils.ApplicationStatus;
import mar.cod.flightback.utils.FlightClass;

public class FlightRequestDto {
    private long userId;
    private long flightId;
    private FlightClass flightClass;
    private ApplicationStatus status;
    private String notes;
    private Float price;
    private String departure;
    private String destination;
    private LocalTime departureTime;
    private LocalDate departurDate;
    private String airline;

    public FlightRequestDto(long userId, long flightId, FlightClass flightClass, ApplicationStatus status, String notes,
            Float price, String departure, String destination, LocalTime departureTime, LocalDate departurDate,
            String airline) {
        this.userId = userId;
        this.flightId = flightId;
        this.flightClass = flightClass;
        this.status = status;
        this.notes = notes;
        this.price = price;
        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
        this.departurDate = departurDate;
        this.airline = airline;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public FlightClass getFlightClass() {
        return flightClass;
    }

    public void setFlightClass(FlightClass flightClass) {
        this.flightClass = flightClass;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDate getDeparturDate() {
        return departurDate;
    }

    public void setDeparturDate(LocalDate departurDate) {
        this.departurDate = departurDate;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

}
