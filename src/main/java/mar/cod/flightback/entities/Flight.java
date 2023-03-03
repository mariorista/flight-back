package mar.cod.flightback.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private Long flightId;

    @Column(length = 5)
    @NotBlank(message = "you must insert a departure")
    private String departure;

    @Column(length = 5)
    @NotBlank(message = "you must insert a destination")
    private String destination;

    @NotBlank
    private LocalTime departureTime;

    @Future(message = "the departure date has to be in the future")
    private LocalDate departurDate;

    @Column(length = 30)
    private String airline;

    // @JsonIgnore
    // @ManyToMany(mappedBy = "flights")
    // private Set<User> users = new HashSet<>();

    // @OneToMany(mappedBy = "flight")
    // private Set<UserFlightEx> userFlightSet;

    public Flight() {
    }

    public Flight(Long flightId, @NotBlank(message = "you must insert a departure") String departure,
            @NotBlank(message = "you must insert a destination") String destination, @NotBlank LocalTime departureTime,
            @Future(message = "the departure date has to be in the future") LocalDate departurDate, String airline) {
        this.flightId = flightId;
        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
        this.departurDate = departurDate;
        this.airline = airline;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
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

    @Override
    public String toString() {
        return "Flight [flightId=" + flightId + ", departure=" + departure + ", destination=" + destination
                + ", departureTime=" + departureTime + ", departurDate=" + departurDate + ", airline=" + airline + "]";
    }

    

    // public Set<UserFlightEx> getUserFlightSet() {
    // return userFlightSet;
    // }

    // public Set<User> getUsers() {
    // return users;
    // }

}
