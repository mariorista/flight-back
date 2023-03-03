package mar.cod.flightback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mar.cod.flightback.entities.Flight;
import mar.cod.flightback.entities.repo.FlightRepo;
import mar.cod.flightback.services.FlightService;
import mar.cod.flightback.utils.exception.NotFoundEntityException;

@SpringBootTest
public class FlightsTest {

    private static final Logger log = LoggerFactory.getLogger(FlightsTest.class);

    @Autowired
    FlightService service;

    @Autowired
    private FlightRepo flightRepo;

    @Test
    public void getAllFlightsTest() {
        List<Flight> flightsList = service.getAllFlights();
        assertNotNull(flightsList);
        assertFalse(flightsList.isEmpty());
        assertTrue(flightsList.get(0).getDeparturDate().isAfter(LocalDate.now()));
    }

    @Test
    public void getAllFlightsFromToTest() {
        List<Flight> flightsList = service.getAllFlights();
        assertNotNull(flightsList);
        assertFalse(flightsList.isEmpty());
        assertTrue(flightsList.get(0).getDeparturDate().isAfter(LocalDate.now()));
    }

    @Test
    public void getFlightsFromToTest() {
        String from = "TIA", to = "ATH";
        List<Flight> flightsList = service.getAllFlights(from, to);
        assertNotNull(flightsList);
        assertTrue(flightsList.isEmpty());

        flightsList = service.getAllFlights("", "");
        assertNotNull(flightsList);
        assertFalse(flightsList.isEmpty());

        // pre conted
        flightsList = service.getAllFlights(from, "");
        assertNotNull(flightsList);
        assertFalse(flightsList.isEmpty());
        flightsList.parallelStream().forEach(f -> f.getDeparture().equals(from));
        flightsList.parallelStream().forEach(f -> f.getDeparturDate().isAfter(LocalDate.now()));

        flightsList = service.getAllFlights(null, to);
        assertNotNull(flightsList);
        assertFalse(flightsList.isEmpty());
        flightsList.parallelStream().forEach(f -> f.getDestination().equals(to));
        flightsList.parallelStream().forEach(f -> f.getDeparturDate().isAfter(LocalDate.now()));
    }


    @Test
    public Map[] destDepMapperTest() {
        Map<String, Flight> destKeyMap = new HashMap<>();
        Map<String, Flight> depKeyMap = new HashMap<>();
        LocalDate dat = LocalDate.now().plusDays(3);
        LocalTime tim = LocalTime.now();
        String from = "TIA", to = "ATH";
        Flight f1 = new Flight(100l, "TIA", "BUD", tim, dat, null);
        Flight f2 = new Flight(200l, "TIA", "MLP", tim, dat, null);
        Flight f3 = new Flight(200l, "TIA", "IST", tim, dat, null);
        Flight f4 = new Flight(200l, "TIA", "MSL", tim, dat, null);
        Flight f5 = new Flight(200l, "TIA", "LHR", tim, dat, null);

        Flight f6 = new Flight(200l, "BUD", "ATH", tim, dat, null);
        Flight f7 = new Flight(200l, "IST", "ATH", tim, dat, null);
        Flight f8 = new Flight(200l, "FMC", "ATH", tim, dat, null);
        Flight f9 = new Flight(200l, "LYN", "ATH", tim, dat, null);
        List<Flight> flights = Arrays.asList(f1, f2, f3, f4, f5, f6, f7, f8, f9);

        Method ethodDestDepMaper;
        try {
            ethodDestDepMaper = FlightService.class.getDeclaredMethod("destDepMaper", List.class, Map.class, Map.class,
                    String.class, String.class);
            ethodDestDepMaper.setAccessible(true);
            ethodDestDepMaper.invoke(new FlightService(), flights, destKeyMap, depKeyMap, from, to);
            assertTrue(!depKeyMap.isEmpty() || !destKeyMap.isEmpty());

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Map<String, Flight>[] mar = new Map[]{destKeyMap,depKeyMap};
        return new Map[] { destKeyMap, depKeyMap };

        // return new HashMap<String, Flight>[] { destKeyMap, depKeyMap };
    }

    @Test
    public void transitListBuilderTest() {

        Map<String, Flight> destKeyMap = new HashMap<>();
        Map<String, Flight> depKeyMap = new HashMap<>();
        Map[] rmap = destDepMapperTest();

        destKeyMap = rmap[0];
        depKeyMap = rmap[1];

        Method methodTransitListBuilder;
        try {
            methodTransitListBuilder = FlightService.class.getDeclaredMethod("transitListBuilder", Map.class,  Map.class);
            methodTransitListBuilder.setAccessible(true);
            List<List<Flight>> ret = (List<List<Flight>>) methodTransitListBuilder.invoke(new FlightService(), destKeyMap, depKeyMap);
            log.info("size--------:{}", ret.size());
            assertFalse(ret.isEmpty());

            for (List<Flight> transitFlightSegment : ret) {
                assertEquals(transitFlightSegment.get(0).getDestination(), transitFlightSegment.get(1).getDeparture());
                log.info("f1:{}-{} _-_ {}-{}",transitFlightSegment.get(0).getDeparture(), transitFlightSegment.get(0).getDestination(),
                transitFlightSegment.get(1).getDeparture(),transitFlightSegment.get(1).getDestination());
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void transitDestDepMaper() {
        String from = "TIA", to = "ATH";
        List<Flight> flightsList = flightRepo.findByDepartureOrDestination(from, to);
        assertNotNull(flightsList);
        assertFalse(flightsList.isEmpty());
        List<Flight> filtered = flightsList.parallelStream()
                .filter(f -> f.getDeparturDate().equals(from) || f.getDestination().equals(to))
                .toList();

        // there is at least one element inside
        assertFalse(filtered.isEmpty());

        // ---------
        try {
            Map<String, Flight> destKeyMap = new HashMap<>();
            Map<String, Flight> depKeyMap = new HashMap<>();

            Method methodDestDepMaper = FlightService.class.getDeclaredMethod("destDepMaper", List.class, Map.class,
                    Map.class, String.class, String.class);
            methodDestDepMaper.setAccessible(true);
            methodDestDepMaper.invoke(new FlightService(), flightsList, destKeyMap, depKeyMap, from, to);

            // at least one of them is not empty
            assertTrue(!depKeyMap.isEmpty() || !destKeyMap.isEmpty());



            Method   methodTransitListBuilder = FlightService.class.getDeclaredMethod("transitListBuilder", Map.class,  Map.class);
            methodTransitListBuilder.setAccessible(true);
            List<List<Flight>> ret = (List<List<Flight>>) methodTransitListBuilder.invoke(new FlightService(), destKeyMap, depKeyMap);
            log.info("size--------:{}", ret.size());
            assertFalse(ret.isEmpty());

            for (List<Flight> transitFlightSegment : ret) {
                assertEquals(transitFlightSegment.get(0).getDestination(), transitFlightSegment.get(1).getDeparture());
                log.info("f1:{}-{} _-_ {}-{}",transitFlightSegment.get(0).getDeparture(), transitFlightSegment.get(0).getDestination(),
                transitFlightSegment.get(1).getDeparture(),transitFlightSegment.get(1).getDestination());
            }


        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSpecificFlightNoFlight() {
        long flightId = -1;
        try {
            Flight flight = service.getFlight(flightId);
            assertNotNull(flight);
            assertEquals(flight.getFlightId().longValue(), flightId);
            assertTrue(flight.getDeparture().length() > 0);
            assertTrue(flight.getDestination().length() > 0);
        } catch (NotFoundEntityException e) {
            log.warn("EXCEPTION: {}", e.getMessage());
            assertEquals(e.getMessage(), "Not Present the entity with id " + flightId);
        }
    }

    @Test
    public void getSpecificFlight() {
        long flightId = 2;
        try {
            Flight flight = service.getFlight(flightId);
            assertNotNull(flight);
            assertEquals(flight.getFlightId().longValue(), flightId);
            assertTrue(flight.getDeparture().length() > 0);
            assertTrue(flight.getDestination().length() > 0);
        } catch (NotFoundEntityException e) {
            log.warn("EXCEPTION: {}", e.getMessage());
            assertEquals(e.getMessage(), "Not Present the entity with id " + flightId);
        }
    }

}
