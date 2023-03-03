package mar.cod.flightback.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import mar.cod.flightback.utils.ApplicationStatus;
import mar.cod.flightback.utils.FlightClass;

@Entity
public class FlightUserConnect {

    @EmbeddedId
    private UserFlightId id;

    @Enumerated(EnumType.STRING)
    @Column(name = "flight_class", length = 10)
    @NotNull
    private FlightClass flightClass;

    @Column(name = "status", length = 10)
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.PENDING;

    @Column(length = 100)
    private String notes;

    @Column(precision = 2, length = 6)
    private Float price;

    public FlightUserConnect() {
    }

    public FlightUserConnect(UserFlightId id, @NotBlank FlightClass flightClass, ApplicationStatus status, String notes,
            Float price) {
        this.id = id;
        this.flightClass = flightClass;
        this.status = status;
        this.notes = notes;
        this.price = price;
    }

    public FlightUserConnect(UserFlightId id, @NotBlank FlightClass flightClass, Float price) {
        this.id = id;
        this.flightClass = flightClass;
        this.price = price;
    }

    // public FlightUserConnect(UserFlightId id, FlightClass flightClass) {
    // this.id = id;
    // this.flightClass = flightClass;
    // }

    public UserFlightId getId() {
        return id;
    }

    public void setId(UserFlightId id) {
        this.id = id;
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

}
