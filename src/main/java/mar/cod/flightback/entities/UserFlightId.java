package mar.cod.flightback.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserFlightId implements Serializable {
    @Column(name = "flight_id")
    private Long flightId;
    @Column(name = "user_id")
    private Long userId;

    // @ManyToOne
    // @MapsId
    // @JoinColumn(name = "user_id")
    // private User user;

    // @ManyToOne
    // @MapsId
    // @JoinColumn(name = "flight_id")
    // private Flight flight;

    public UserFlightId() {
    }

    public UserFlightId(Long flightId, Long userId) {
        this.flightId = flightId;
        this.userId = userId;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /*
     * public UserFlightId(User user, Flight flight) {
     * this.user = user;
     * this.flight = flight;
     * }
     * 
     * public User getUser() {
     * return user;
     * }
     * 
     * public void setUser(User user) {
     * this.user = user;
     * }
     * 
     * public Flight getFlight() {
     * return flight;
     * }
     * 
     * public void setFlight(Flight flight) {
     * this.flight = flight;
     * }
     */
}
