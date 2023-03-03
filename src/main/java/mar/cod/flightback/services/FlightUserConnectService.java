package mar.cod.flightback.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mar.cod.flightback.entities.dto.FlightRequestDto2;
import mar.cod.flightback.entities.repo.FlightUserConnectRepo;
import mar.cod.flightback.utils.exception.NoResultsAvalilable;

@Service
public class FlightUserConnectService {

    private FlightUserConnectRepo repo;

    @Autowired
    public FlightUserConnectService(FlightUserConnectRepo repo) {
        this.repo = repo;
    }

    public List<FlightRequestDto2> getAllFlightRequests() throws NoResultsAvalilable {
        List<FlightRequestDto2> list = repo.getAllDto();
        if (list == null)
            throw new NoResultsAvalilable("No resuls were found");
        else
            return list;
    }

    public List<FlightRequestDto2> getUserFlightRequests(long userId) throws NoResultsAvalilable {
        List<FlightRequestDto2> list = repo.getUserDtoList(userId);
        if (list == null)
            throw new NoResultsAvalilable("No resuls were found");
        else
            return list;
    }

    public FlightRequestDto2 getSpecificDto(long userId, long flightId) throws NoResultsAvalilable {
        FlightRequestDto2 dto = repo.getSpecificDto(userId, flightId);
        if (dto == null)
            throw new NoResultsAvalilable("No resuls were found");
        else
            return dto;
    }

   
}
