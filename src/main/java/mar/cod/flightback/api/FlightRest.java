package mar.cod.flightback.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mar.cod.flightback.entities.Flight;
import mar.cod.flightback.services.FlightService;
import mar.cod.flightback.utils.exception.NotFoundEntityException;

@RestController
@RequestMapping("flight")
public class FlightRest {

    @Autowired
    private FlightService flightService;

    @GetMapping("id/{id}")
    public ResponseEntity<Flight> getFlight(@PathVariable long id) throws NotFoundEntityException {
        return ResponseEntity.ok(flightService.getFlight(id));
    }

    @GetMapping("all")
    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @GetMapping("fromto/{from}/{to}")
    public ResponseEntity<List<Flight>> getAllFlights(String from, String to) {
        return ResponseEntity.ok(flightService.getAllFlights(from, to));
    }

    @GetMapping("transit/{from}/{to}")
    public ResponseEntity<List<List<Flight>>> findTransitFlights(String from, String to) {
        return ResponseEntity.ok(flightService.findTransitFlights(from, to));
    }

    // @PostMapping("create")
    // private void createFlight(@RequestBody Flight flght) {
    // flightService.createFlight(flght);
    // }

}
