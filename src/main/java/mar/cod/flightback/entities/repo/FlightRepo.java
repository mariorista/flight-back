package mar.cod.flightback.entities.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mar.cod.flightback.entities.Flight;

@Repository
public interface FlightRepo extends JpaRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f WHERE f.departure =?1 or f.destination = ?2")
    public List<Flight> findByDepartureOrDestination(String dep, String dest);

    @Query("SELECT f FROM Flight f WHERE f.departure =?1 ")
    public List<Flight> findByDeparture(String dep);

    @Query("SELECT f FROM Flight f WHERE f.destination = ?1")
    public List<Flight> findByDestination(String dest);

    @Query("SELECT f FROM Flight f WHERE f.departure =?1 and f.destination = ?2")
    public List<Flight> findByDepartureAndDestination(String from, String to);
}
