package mar.cod.flightback.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mar.cod.flightback.entities.dto.FlightRequestDto2;
import mar.cod.flightback.services.FlightUserConnectService;
import mar.cod.flightback.utils.exception.NoResultsAvalilable;

@RestController
@RequestMapping("connect")
public class FlightUserConnectRest {
    @Autowired
    FlightUserConnectService flightUserConnectService;

    @GetMapping("v")
    public String version() {
        return "1.1.1";
    }

    @GetMapping("admin/all")
    public List<FlightRequestDto2> getAllFlightRequests() throws NoResultsAvalilable {
        List<FlightRequestDto2> list = flightUserConnectService.getAllFlightRequests();
        return list;
    }

    @GetMapping("user/{userId}")
    public List<FlightRequestDto2> getUserFlightRequests(long userId) throws NoResultsAvalilable {
        List<FlightRequestDto2> list = flightUserConnectService.getUserFlightRequests(userId);
        return list;
    }

    @GetMapping("specific/{userId}/{flightId}")
    public FlightRequestDto2 getSpecificDto(long userId, long flightId) throws NoResultsAvalilable {
        FlightRequestDto2 dto = flightUserConnectService.getSpecificDto(userId, flightId);
        return dto;
    }

}
