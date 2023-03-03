package mar.cod.flightback.entities.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import mar.cod.flightback.entities.FlightUserConnect;
import mar.cod.flightback.entities.UserFlightId;
import mar.cod.flightback.entities.dto.FlightRequestDto2;
import mar.cod.flightback.utils.ApplicationStatus;

@Repository
public interface FlightUserConnectRepo extends JpaRepository<FlightUserConnect, UserFlightId> {

    @Query("SELECT new mar.cod.flightback.entities.dto.FlightRequestDto2(f,fuc) FROM Flight f JOIN FlightUserConnect fuc ON (f.flightId = fuc.id.flightId)")
    public List<FlightRequestDto2> getAllDto();

    @Query("SELECT new mar.cod.flightback.entities.dto.FlightRequestDto2(f,fuc) FROM Flight f JOIN FlightUserConnect fuc ON (f.flightId = fuc.id.flightId) WHERE fuc.id.userId=?1")
    public List<FlightRequestDto2> getUserDtoList(long userId);

    @Query("SELECT new mar.cod.flightback.entities.dto.FlightRequestDto2(f,fuc) FROM Flight f JOIN FlightUserConnect fuc ON (f.flightId = fuc.id.flightId) WHERE fuc.id.userId=?1 AND fuc.id.flightId=?2")
    public FlightRequestDto2 getSpecificDto(long userId, long flightId);


    @Modifying
    @Transactional
    @Query("UPDATE FlightUserConnect fuc SET fuc.status = ?3 WHERE  fuc.id.userId=?1 AND fuc.id.flightId=?2")
    public void cancelFlight( long userId, long flightId, ApplicationStatus status);

    @Modifying
    @Transactional
    @Query("Delete FlightUserConnect fuc WHERE  fuc.id.userId=?1 ")
    public void deleteUserFK( long userId);

}
