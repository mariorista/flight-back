package mar.cod.flightback.entities;

@Deprecated
// @Entity
public class UserFlightEx1 {

    // @Id
    private long id;

    // @ManyToOne
    // @JoinColumn(name = "user_id")
    private User user;

    // @ManyToOne
    // @JoinColumn(name = "flight_id")
    private Flight flight;

    public UserFlightEx1() {
    }

    public UserFlightEx1(long id, User user, Flight flight) {
        this.id = id;
        this.user = user;
        this.flight = flight;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private long id;

    // private long flightId;
    // private long userId;

    // public UserFlight() {
    // }

    // public UserFlight(long id, long flightId, long userId) {
    // this.id = id;
    // this.flightId = flightId;
    // this.userId = userId;
    // }

    // public long getId() {
    // return id;
    // }

    // public void setId(long id) {
    // this.id = id;
    // }

    // public long getFlightId() {
    // return flightId;
    // }

    // public void setFlightId(long flightId) {
    // this.flightId = flightId;
    // }

    // public long getUserId() {
    // return userId;
    // }

    // public void setUserId(long userId) {
    // this.userId = userId;
    // }

}
