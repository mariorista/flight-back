package mar.cod.flightback.broker;

import java.util.List;

import mar.cod.flightback.entities.Flight;
import mar.cod.flightback.entities.User;

public interface FlightUserMyRepo {
    public List<Flight> acquireFlightsByUserId(long userId);

    public List<User> acquireUsersByFlightId(long flightId);
}
