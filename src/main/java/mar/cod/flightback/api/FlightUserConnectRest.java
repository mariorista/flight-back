package mar.cod.flightback.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("admin/page/all")
    public List<FlightRequestDto2> getAllFlightRequests() throws NoResultsAvalilable {
        List<FlightRequestDto2> list = flightUserConnectService.getAllFlightRequests();
        return list;
    }

    @GetMapping("admin/all")
    public Page<FlightRequestDto2> getAllFlightRequestsPage(@RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size) throws NoResultsAvalilable {
        return flightUserConnectService.getAllFlightRequestsPage(PageRequest.of(page.orElse(0), size.orElse(5)));
    }

    @GetMapping("user/page/{userId}")
    public List<FlightRequestDto2> getUserFlightRequests(@PathVariable long userId) throws NoResultsAvalilable {
        List<FlightRequestDto2> list = flightUserConnectService.getUserFlightRequests(userId);
        return list;
    }

    @GetMapping("user/{userId}")
    public Page<FlightRequestDto2> getUserFlightRequestsPage(@PathVariable long userId,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size) throws NoResultsAvalilable {
        return flightUserConnectService.getUserFlightRequestsPage(userId,
                PageRequest.of(page.orElse(0), size.orElse(5)));
    }

    @GetMapping("specific/{userId}/{flightId}")
    public FlightRequestDto2 getSpecificDto(@PathVariable long userId, @PathVariable long flightId)
            throws NoResultsAvalilable {
        FlightRequestDto2 dto = flightUserConnectService.getSpecificDto(userId, flightId);
        return dto;
    }

}
