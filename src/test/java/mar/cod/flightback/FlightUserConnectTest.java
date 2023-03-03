package mar.cod.flightback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mar.cod.flightback.entities.dto.FlightRequestDto2;
import mar.cod.flightback.services.FlightUserConnectService;
import mar.cod.flightback.utils.exception.NoResultsAvalilable;

@SpringBootTest
public class FlightUserConnectTest {

    @Autowired
    FlightUserConnectService service;

    @Test
    public void test1() {
        try {
            FlightRequestDto2 ret = service.getSpecificDto(0, 0);
            assertNull(ret);
        } catch (NoResultsAvalilable e) {
            assertEquals(e.getMessage(), "No resuls were found");
        }
    }
}
