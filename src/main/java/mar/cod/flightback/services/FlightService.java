package mar.cod.flightback.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mar.cod.flightback.entities.Flight;
import mar.cod.flightback.entities.repo.FlightRepo;
import mar.cod.flightback.utils.exception.NotFoundEntityException;

@Service
public class FlightService {

    private FlightRepo flightRepo;

    @Autowired
    public FlightService(FlightRepo flightRepo) {
        this.flightRepo = flightRepo;
    }

    public FlightService() {
    }

    public Flight getFlight(long id) throws NotFoundEntityException {
        Optional<Flight> ret = flightRepo.findById(id);
        if (ret.isPresent())
            return ret.get();
        else
            throw new NotFoundEntityException(
                    "Not Present the entity with id " + id);
    }

    public List<Flight> getAllFlights() {
        // filter older flights
        List<Flight> flights = flightRepo.findAll().stream()
                .filter(flight -> flight.getDeparturDate().isAfter(LocalDate.now())).toList();
        return flights;
    }

    // first call here
    public List<Flight> getAllFlights(String from, String to) {
        // filter older flights
        List<Flight> retFlights = new ArrayList<>();
        if ((from == null || from.length() == 0) && (to == null || to.length() == 0)) {
            retFlights = flightRepo.findAll();
        } else if (from == null || from.length() == 0) {
            retFlights = flightRepo.findByDestination(to);
        } else if (to == null || to.length() == 0) {
            retFlights = flightRepo.findByDeparture(from);
        } else {
            retFlights = flightRepo.findByDepartureAndDestination(from, to);
        }
        // return retFlights;
        List<Flight> flights = retFlights.stream()
                .filter(flight -> flight.getDeparturDate().isAfter(LocalDate.now())).toList();
        return flights;
    }

    // automatically secondary call here for transit
    public List<List<Flight>> findTransitFlights(String dep, String dest) {
        List<Flight> flights = flightRepo.findByDepartureOrDestination(dep, dest);
        if (flights.isEmpty())
            return new ArrayList<>();

        Map<String, Flight> destKeyMap = new HashMap<>();
        Map<String, Flight> depKeyMap = new HashMap<>();

        destDepMaper(flights, destKeyMap, depKeyMap, dep, dest);

        List<List<Flight>> transit = transitListBuilder(destKeyMap, depKeyMap);
        return transit;
    }

    private void destDepMaper(List<Flight> flights, Map<String, Flight> destKeyMap, Map<String, Flight> depKeyMap,
            String dep, String dest) {
        // Map<String, Flight> destKeyMap = new HashMap<>();
        // Map<String, Flight> depKeyMap = new HashMap<>();
        for (Flight flight : flights) {
            if (flight.getDeparture().equals(dep)) {
                destKeyMap.put(flight.getDestination(), flight);
            }

            if (flight.getDestination().equals(dest)) {
                depKeyMap.put(flight.getDeparture(), flight);
            }
        }
    }

    private List<List<Flight>> transitListBuilder(Map<String, Flight> destKeyMap, Map<String, Flight> depKeyMap) {
        List<List<Flight>> transit = new ArrayList<>();
        for (String key : destKeyMap.keySet()) {
            if (depKeyMap.containsKey(key)) {
                List<Flight> list = new ArrayList<>();
                list.add(destKeyMap.get(key));
                list.add(depKeyMap.get(key));
                transit.add(list);
            }
        }
        return transit;
    }

}
