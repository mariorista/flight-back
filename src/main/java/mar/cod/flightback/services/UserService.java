package mar.cod.flightback.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import mar.cod.flightback.api.pojo.LogInPojo;
import mar.cod.flightback.api.pojo.MainUserPojo;
import mar.cod.flightback.entities.Flight;
import mar.cod.flightback.entities.FlightUserConnect;
import mar.cod.flightback.entities.User;
import mar.cod.flightback.entities.UserFlightId;
import mar.cod.flightback.entities.dto.FlightRequestDto2;
import mar.cod.flightback.entities.repo.FlightRepo;
import mar.cod.flightback.entities.repo.FlightUserConnectRepo;
import mar.cod.flightback.entities.repo.UserRepo;
import mar.cod.flightback.utils.ApplicationStatus;
import mar.cod.flightback.utils.FlightClass;
import mar.cod.flightback.utils.Roles;
import mar.cod.flightback.utils.exception.InsertionException;
import mar.cod.flightback.utils.exception.NoResultsAvalilable;
import mar.cod.flightback.utils.exception.NotFoundEntityException;
import mar.cod.flightback.utils.exception.OperationNotPosible;
import mar.cod.flightback.utils.exception.UnmetConditionsException;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Value("${default.initial.flights}")
    private Integer initialFlights;

    private UserRepo userRepo;
    private FlightUserConnectRepo fucRepo;
    private FlightUserConnectService fucService;
    private FlightRepo flightRepo;

    @Autowired
    public UserService(UserRepo userRepo, FlightUserConnectRepo fucRepo, FlightRepo flightRepo,
            FlightUserConnectService fucService) {
        this.userRepo = userRepo;
        this.fucRepo = fucRepo;
        this.flightRepo = flightRepo;
        this.fucService = fucService;
    }

    public User createUser(MainUserPojo pojo) throws Exception {
        // Confirm creation comes from admin
        Optional<User> admin = userRepo.findById(pojo.getCreatorId());
        if (admin.isPresent()) {
            log.info("{}", admin.get().toString());
            if (admin.get().getRole() == Roles.ADMIN) {
                pojo.getUser().setRemainingFlights(initialFlights);

                try {
                    Optional<User> foundUser = userRepo.findByUniqueFields(pojo.getUser().getUsr(),
                            pojo.getUser().getPsw(), pojo.getUser().getEmail());
                    if (foundUser.isPresent())
                        throw new OperationNotPosible("Possible duplicate credentials or email");
                    else
                        return userRepo.save(pojo.getUser());
                } catch (Exception e) {
                    throw new InsertionException(e.getMessage());
                }
            }
        }

        throw new UnmetConditionsException("User creation is not made by an Admin");
    }

    public User getUserById(long id) throws NotFoundEntityException {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent())
            return user.get();
        else {
            throw new NotFoundEntityException(
                    "Not Present the entity with id " + id);
        }
    }

    public User getUserByLogin(LogInPojo pojo) throws NotFoundEntityException {
        Optional<User> user = userRepo.findUserByPswAndUsr( pojo.getPassword(),pojo.getUsername());
        if (user.isPresent())
            return user.get();
        else {
            throw new NotFoundEntityException(" The usr or psw was not correct");
        }
    }

    public List<User> getAllUsers() {
        List<User> usrs = userRepo.findAll();
        return usrs;
    }

    public User updateUser(User u) throws InsertionException, OperationNotPosible {

        Optional<User> foundUser = userRepo.findById(u.getId());
        if (foundUser.isEmpty())
            throw new OperationNotPosible("User with id " + u.getId() + " not registered");
        else {
            try {
                return userRepo.save(u);
            } catch (Exception e) {
                throw new InsertionException(e.getMessage());
            }
        }

    }

    @Transactional
    public void deleteUserById(long userId, Long adminId)
            throws OperationNotPosible, InsertionException, UnmetConditionsException {

        Optional<User> admin = userRepo.findById(adminId);
        if (admin.isPresent()) {
            if (admin.get().getRole() == Roles.ADMIN) {
                try {
                    Optional<User> foundUser = userRepo.findById(userId);
                    if (foundUser.isEmpty())
                        throw new OperationNotPosible("Unable to delete, user not registered");
                    else {
                        fucRepo.deleteUserFK(userId);
                        userRepo.deleteById(userId);
                    }
                } catch (Exception e) {
                    throw new InsertionException(e.getMessage());
                }
            }
        }
        throw new UnmetConditionsException("User deletion is not made by an Admin");
    }

    public void modifyPsw(long userId, String newPsw) throws OperationNotPosible, InsertionException {
        Optional<User> foundUser = userRepo.findById(userId);
        if (foundUser.isEmpty())
            throw new OperationNotPosible("User with id " + userId + " not registered");
        else {
            try {
                userRepo.setUserPswById(newPsw, userId);
            } catch (Exception e) {
                throw new InsertionException(e.getMessage());
            }
        }

    }

    @Transactional
    public void requestFlight(long userId, Long flightId, FlightClass flightClass, float price)
            throws OperationNotPosible {

        User user = userRepo.findById(userId).get();
        int remainingFlights = user.getRemainingFlights();
        if (remainingFlights > 0) {
            user.setRemainingFlights(user.getRemainingFlights() - 1);
            userRepo.save(user);

            UserFlightId ufId = new UserFlightId(flightId, userId);
            FlightUserConnect flightUserConnect = new FlightUserConnect(ufId, flightClass, price);
            fucRepo.save(flightUserConnect);

        } else
            throw new OperationNotPosible("User has remaining flights " + remainingFlights);

    }

    @Transactional
    public void cancelFlight(long userId, Long flightId)
            throws OperationNotPosible, NotFoundEntityException, NoResultsAvalilable {

        smallPreRequestActionCheck(userId, flightId);

        fucRepo.cancelFlight(userId, flightId, ApplicationStatus.CANCELED);

        User user = userRepo.findById(userId).get();
        user.setRemainingFlights(user.getRemainingFlights() + 1);
        userRepo.save(user);
    }

    @Transactional
    public void denyFlightRequest(long userId, Long flightId, String reason)
            throws NotFoundEntityException, OperationNotPosible, NoResultsAvalilable {

        Optional<FlightUserConnect> usrFlightConn = preRequestActionCheck(userId, flightId);
        if (usrFlightConn.isPresent()) {
            if (reason == null || reason.length() == 0)
                throw new OperationNotPosible("Reasoning required to deny");
            FlightUserConnect fuc = usrFlightConn.get();
            fuc.setStatus(ApplicationStatus.DENIED);
            fuc.setNotes(reason);
            fucRepo.save(fuc);

            User user = userRepo.findById(userId).get();
            user.setRemainingFlights(user.getRemainingFlights() + 1);
            user.setAlert(ApplicationStatus.DENIED + " request for flight " + flightId);
            userRepo.save(user);
        }
    }

    @Transactional
    public void approveFlightRequest(long userId, Long flightId, String reason)
            throws NotFoundEntityException, OperationNotPosible, NoResultsAvalilable {

        Optional<FlightUserConnect> usrFlightConn = preRequestActionCheck(userId, flightId);
        if (usrFlightConn.isPresent()) {
            FlightUserConnect fuc = usrFlightConn.get();
            fuc.setStatus(ApplicationStatus.APPROVED);
            fucRepo.save(fuc);

            User user = userRepo.findById(userId).get();
            user.setAlert(ApplicationStatus.APPROVED + " request for flight " + flightId);
            userRepo.save(user);
        }
    }

    private Optional<FlightUserConnect> preRequestActionCheck(long userId, Long flightId)
            throws OperationNotPosible, NotFoundEntityException, NoResultsAvalilable {
        // try {
        UserFlightId ufId = new UserFlightId(flightId, userId);
        Optional<FlightUserConnect> usrFlightConn = fucRepo.findById(ufId);
        if (usrFlightConn.isPresent()) {
            smallPreRequestActionCheck(userId, flightId);
        }
        return usrFlightConn;
    }

    private void smallPreRequestActionCheck(long userId, Long flightId)
            throws NotFoundEntityException, OperationNotPosible, NoResultsAvalilable {
        Optional<Flight> flight = flightRepo.findById(flightId);
        if (flight.isEmpty()) {
            throw new NotFoundEntityException("Flight not present: " + flightId);
        } else {
            if (flight.get().getDeparturDate().isBefore(LocalDate.now()))
                throw new OperationNotPosible(" The flight has already departed ");
        }

        FlightRequestDto2 dto = fucService.getSpecificDto(userId, flightId);
        if (dto.getFuc().getStatus() == ApplicationStatus.DENIED
                || dto.getFuc().getStatus() == ApplicationStatus.CANCELED)
            throw new OperationNotPosible("Flight request status is set : " + dto.getFuc().getStatus().name());
    }

}
