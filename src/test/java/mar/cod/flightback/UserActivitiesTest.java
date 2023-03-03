package mar.cod.flightback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mar.cod.flightback.api.pojo.MainUserPojo;
import mar.cod.flightback.entities.Flight;
import mar.cod.flightback.entities.User;
import mar.cod.flightback.entities.dto.FlightRequestDto2;
import mar.cod.flightback.services.FlightService;
import mar.cod.flightback.services.FlightUserConnectService;
import mar.cod.flightback.services.UserService;
import mar.cod.flightback.utils.ApplicationStatus;
import mar.cod.flightback.utils.FlightClass;
import mar.cod.flightback.utils.Roles;
import mar.cod.flightback.utils.exception.NoResultsAvalilable;
import mar.cod.flightback.utils.exception.NotFoundEntityException;
import mar.cod.flightback.utils.exception.OperationNotPosible;
import mar.cod.flightback.utils.exception.UnmetConditionsException;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(value = Lifecycle.PER_CLASS) // keeps the same class state, for integration tests
public class UserActivitiesTest {
    /**
     * 1)Initial orders of test should be made with user cretion to make shure there
     * are users in the db.
     * Even after user deletion make shure that there are user in db.
     * 
     * 2)Afterwards test flight application (findAll, applications and
     * cancellations)
     * 
     * 3) Flights are allways present and unmutable so it doesn't mater when they
     * are tested
     */

    private static final Logger log = LoggerFactory.getLogger(UserActivitiesTest.class);

    @Autowired
    UserService userService;

    @Autowired
    FlightService flightService;

    @Autowired
    FlightUserConnectService flightUserConnectService;

    private long defaultUserId = -1;
    private String fnam2 = "mario2";
    private String lnam2 = "rista2";
    private User defUser = null;
    private Flight flight1 = null;
    private Flight flight2 = null;
    private Flight flight3 = null;

    // private int localRemainingFlights;

    @BeforeAll
    public void setup() {
        creatUser();
    }

    @AfterAll
    public void setup2() {
        userService.deleteUserById(defaultUserId);
    }

    @Order(1)
    @Test
    public void getAllUsers() {
        List<User> ret = userService.getAllUsers();
        assertNotNull(ret);
        // assertTrue(ret.isEmpty());
    }

    @Order(3)
    @Test
    public void getUser() {
        try {
            User res = userService.getUserById(defaultUserId);
            assertNotNull(res);
            assertEquals(res.getId(), defaultUserId);
        } catch (NotFoundEntityException e) {
            // user must be present
            fail();
        }
    }

    @Order(4)
    @Test
    public void modUser() {
        try {
            User res = userService.getUserById(defaultUserId);
            assertNotNull(res);
            res.setFname(fnam2);
            res.setLname(lnam2);
            User upt = userService.updateUser(res);
            assertNotNull(upt);
            assertEquals(upt.getId(), defaultUserId);
            assertEquals(upt.getFname(), fnam2);
            assertEquals(upt.getLname(), lnam2);
        } catch (NotFoundEntityException e) {
            // user must be present
            fail();
        }
    }

    @Order(5)
    @Test
    public void delUser() {
        try {
            User res = userService.getUserById(defaultUserId);
            assertNotNull(res);
            userService.deleteUser(res);
        } catch (NotFoundEntityException e) {
            // user must be present
            fail();
        }

        try {
            User res2 = userService.getUserById(defaultUserId);
            assertNull(res2);
        } catch (NotFoundEntityException e) {
            // user must not be present
            log.warn(e.getMessage());
            assertEquals("Not Present the entity with id " + defaultUserId, e.getMessage());
        }

        try {
            List<FlightRequestDto2> dtoList = flightUserConnectService.getUserFlightRequests(defaultUserId);
            assertTrue(dtoList.isEmpty());
        } catch (NoResultsAvalilable e) {
            assertEquals(e.getMessage(), "No resuls were found");
        }
    }

    @Order(6)
    @Test
    public void creatUser2() {
        creatUser();
    }

    @Order(7)
    @Test
    public void modifyPsw() {
        String newPsw = "newPsw";
        userService.modifyPsw(defaultUserId, newPsw);

        try {
            User res = userService.getUserById(defaultUserId);
            assertNotNull(res);
            assertEquals(res.getPsw(), newPsw);
            defUser = res;
        } catch (NotFoundEntityException e) {
            // user must be present
            fail();
        }

    }

    @Test
    @Order(10)
    public void applyForFlight() {
        // user can apply for a flight and admin can apply for a user
        List<Flight> flightsList = flightService.getAllFlights();
        if (flightsList.isEmpty()) {
            log.error("NO FLIGHT IN THE SYSTEM");
            fail();
        }

        // User retUsr = userService.getUserById(defaultUserId);
        // localRemainingFlights = retUsr.getRemainingFlights();

        flight1 = flightsList.get(0);

        // populate for other queries
        if (flightsList.size() > 1)
            flight2 = flightsList.get(1);
        if (flightsList.size() > 2)
            flight3 = flightsList.get(2);

        FlightClass flightClass = FlightClass.BUSINESS;
        float price = 200.10f;
        try {
            userService.requestFlight(defaultUserId, flight1.getFlightId(), flightClass, price);
        } catch (OperationNotPosible e) {
            log.error(e.getMessage());
        }

        FlightRequestDto2 flightRequest;
        try {
            flightRequest = flightUserConnectService.getSpecificDto(defaultUserId, flight1.getFlightId());
            assertNotNull(flightRequest);
            assertEquals(flightRequest.getFuc().getId().getUserId(), defaultUserId);
            assertEquals(flightRequest.getFuc().getId().getFlightId(), flight1.getFlightId());
            assertEquals(flightRequest.getFuc().getFlightClass(), flightClass);

            // retUsr = userService.getUserById(defaultUserId);
            // assertEquals(retUsr.getRemainingFlights(), localRemainingFlights);
        } catch (NoResultsAvalilable e) {
            // assertEquals(e.getMessage(), "No resuls were found");
            fail();
        }
    }

    @Test
    @Order(11)
    public void seeAllApplications() throws OperationNotPosible {

        FlightClass flightClass = FlightClass.ECONOMY;
        float price = 100.20f;
        int count = 1;
        if (flight2 != null) {
            userService.requestFlight(defUser.getId(), flight2.getFlightId(), flightClass, price);
            count++;
        }
        if (flight3 != null) {
            userService.requestFlight(defUser.getId(), flight3.getFlightId(), flightClass, price);
            count++;
        }

        List<FlightRequestDto2> dtoList = null;
        try {
            dtoList = flightUserConnectService.getAllFlightRequests();
            assertNotNull(dtoList);
            assertFalse(dtoList.isEmpty());
            assertTrue(dtoList.size() >= count);
        } catch (NoResultsAvalilable e) {
            // assertEquals(e.getMessage(), "No resuls were found");
            fail();
        }

    }

    @Test
    @Order(12)
    public void cancelFlight() {

        try {
            userService.cancelFlight(defaultUserId, flight1.getFlightId());
        } catch (OperationNotPosible | NotFoundEntityException | NoResultsAvalilable e) {
            log.error(e.getMessage());
        }

        FlightRequestDto2 flightRequest;
        try {
            flightRequest = flightUserConnectService.getSpecificDto(defaultUserId, flight1.getFlightId());
            assertNotNull(flightRequest);
            assertEquals(flightRequest.getFuc().getId().getUserId(), defaultUserId);
            assertEquals(flightRequest.getFuc().getId().getFlightId(), flight1.getFlightId());
            assertEquals(flightRequest.getFuc().getStatus(), ApplicationStatus.CANCELED);

        } catch (NoResultsAvalilable e) {
            // assertEquals(e.getMessage(), "No resuls were found");
            fail();
        }
    }

    @Test
    @Order(13)
    public void denieFlight() {
        // TODO alert user for this action

        String reason = "too expensive";
        try {
            userService.denieFlightRequest(defaultUserId, flight1.getFlightId(), reason);
        } catch (NotFoundEntityException | OperationNotPosible | NoResultsAvalilable e) {
            log.error(e.getMessage());
            // e.printStackTrace();
        }

        FlightRequestDto2 flightRequest;
        try {
            flightRequest = flightUserConnectService.getSpecificDto(defaultUserId, flight1.getFlightId());
            assertNotNull(flightRequest);
            assertEquals(flightRequest.getFuc().getId().getUserId(), defaultUserId);
            assertEquals(flightRequest.getFuc().getId().getFlightId(), flight1.getFlightId());

            //from the operation above
            assertEquals(ApplicationStatus.CANCELED, flightRequest.getFuc().getStatus());
        } catch (NoResultsAvalilable e) {
            // assertEquals(e.getMessage(), "No resuls were found");
            fail();
        }
    }

    @Order(8)
    @Test
    public void CRUDUserTest() {
        // simple Users are created by admin
        String fname = "mario";
        String lname = "rista";
        String usr = "mar_mar12";
        String psw = "root";
        String email = "mar12@mr.com";
        long userId = -1;
        User u1 = new User(0, fname, lname, usr, psw, email, LocalDate.now().minusYears(3), Roles.SIMPLE);
        Long creatorsId = 1l;

        MainUserPojo pojo = new MainUserPojo(creatorsId, u1);
        User ret = null;
        try {
            ret = userService.createUser(pojo);
            userId = ret.getId();
            log.info("{}", ret.toString());
            assertNotNull(ret);
            assertEquals(ret.getFname(), fname);
            assertEquals(ret.getLname(), lname);
            assertEquals(ret.getUsr(), usr);
            assertEquals(ret.getPsw(), psw);
            assertEquals(ret.getEmail(), email);

        } catch (UnmetConditionsException e) {

            e.printStackTrace();
            log.error(e.getMessage());
        }

        // --------------

        User u2 = null;
        try {
            if (userId > -1) {
                u2 = userService.getUserById(userId);
                assertNotNull(u2);
                assertEquals(u2.getFname(), fname);
                assertEquals(u2.getLname(), lname);
                assertEquals(u2.getUsr(), usr);
                assertEquals(u2.getPsw(), psw);
                assertEquals(u2.getEmail(), email);

            }
        } catch (NotFoundEntityException e) {
            log.error(e.getMessage());
            // shouldnt enter here
            fail();
        }

        String fname2 = "mario2";
        String lname2 = "rista2";
        u2.setFname(fname2);
        u2.setLname(lname2);
        // u2.set
        userService.updateUser(u2);
        User u3 = null;
        try {
            u3 = userService.getUserById(userId);
            assertNotNull(u3);
            assertEquals(u3.getFname(), fname2);
            assertEquals(u3.getLname(), lname2);
        } catch (NotFoundEntityException e) {
            log.error(e.getMessage());
            // shouldnt enter here
            fail();
        }

        // ----------

        userService.deleteUser(u3);
        User u4 = null;
        try {
            u4 = userService.getUserById(userId);
            assertNull(u4);
        } catch (NotFoundEntityException e) {
            assertNotNull(e);
            assertEquals("Not Present the entity with id " + userId, e.getMessage());

        }

    }

    private void creatUser() {
        // There must be 1 admin in the table
        long creatorsId = 1l;

        String fname = "mario";
        String lname = "rista";
        String usr = "mar_mar0";
        String psw = "root";
        String email = "mar1@mr.com";
        User u1 = new User(fname, lname, usr, psw, email, LocalDate.now().minusYears(3), Roles.SIMPLE);
        MainUserPojo pojo = new MainUserPojo(creatorsId, u1);
        try {
            User ret = userService.createUser(pojo);
            defaultUserId = ret.getId();
            assertNotNull(ret);
            assertTrue(ret.getId() > 0);
            assertTrue(ret.getUsr().length() > 0);
            assertEquals(ret.getUsr(), usr);
            assertEquals(ret.getEmail(), email);
            assertEquals(ret.getFname(), fname);
        } catch (UnmetConditionsException e) {
            assertEquals(e.getMessage(), "User creation is not made by an Admin");
        }
    }

}
