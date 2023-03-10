package mar.cod.flightback.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import mar.cod.flightback.api.pojo.FlightActionPojo;
import mar.cod.flightback.api.pojo.MainUserPojo;
import mar.cod.flightback.entities.User;
import mar.cod.flightback.services.UserService;
import mar.cod.flightback.utils.exception.InsertionException;
import mar.cod.flightback.utils.exception.NoResultsAvalilable;
import mar.cod.flightback.utils.exception.NotFoundEntityException;
import mar.cod.flightback.utils.exception.OperationNotPosible;
import mar.cod.flightback.utils.exception.UnmetConditionsException;

@RestController
@RequestMapping("user")
public class UserRest {

    @Autowired
    private UserService userService;

    @GetMapping("admin/v")
    public ResponseEntity<String> getVersion() throws NotFoundEntityException {
        return ResponseEntity.ok("1.0.0,23");
    }

    @PostMapping("admin/v")
    public ResponseEntity<String> getVersion3() throws NotFoundEntityException {
        return ResponseEntity.ok("1.0.0,23");
    }

    @GetMapping("v")
    public ResponseEntity<String> getVersion2() throws NotFoundEntityException {
        return ResponseEntity.ok("1.0.0,23");
    }

    @GetMapping("admin/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("id/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) throws NotFoundEntityException {
        User ret = userService.getUserById(id);
        return ResponseEntity.ok(ret);
    }

    @PostMapping("admin/create")
    private ResponseEntity<User> createUser(@RequestBody @Valid MainUserPojo userPojo)
            throws Exception {
        User ret = userService.createUser(userPojo);
        if (ret == null)
            throw new UnmetConditionsException("Creation application is not made by an Admin");
        return ResponseEntity.ok(ret);
    }

    @PutMapping("admin/update")
    private ResponseEntity<User> updateUser(@RequestBody @Valid MainUserPojo userPojo)
            throws UnmetConditionsException, InsertionException, OperationNotPosible {
        User ret = userService.updateUser(userPojo.getUser());
        return ResponseEntity.ok(ret);
    }

    // @DeleteMapping("admin/delete")
    // private void deleteUser(@RequestBody @Valid MainUserPojo userPojo)
    //         throws UnmetConditionsException, OperationNotPosible, InsertionException {
    //     userService.deleteUser(userPojo.getUser());
    //     // return ResponseEntity.ok(ret);
    // }

    @DeleteMapping("admin/delete/{userId}/{adminId}")
    private void deleteUser(@PathVariable Long userId, @PathVariable Long adminId)
            throws UnmetConditionsException, OperationNotPosible, InsertionException {
        userService.deleteUserById(userId, adminId);
        // return ResponseEntity.ok(ret);
    }

    @PutMapping("admin/pswmod")
    private void modifyPassword(@RequestBody @Valid MainUserPojo userPojo)
            throws UnmetConditionsException, OperationNotPosible, InsertionException {
        userService.modifyPsw(userPojo.getUser().getId(), userPojo.getUser().getPsw());
    }
    

    @GetMapping("requestflight")
    public void requestFlight(@RequestBody FlightActionPojo flightActionPojo) throws OperationNotPosible {
        userService.requestFlight(flightActionPojo.getUserId(), flightActionPojo.getFlightId(),
                flightActionPojo.getFlightClass(), flightActionPojo.getPrice());
    }

    @GetMapping("cancelflightrequest")
    public void cancelFlight(@RequestBody FlightActionPojo flightActionPojo)
            throws OperationNotPosible, NotFoundEntityException, NoResultsAvalilable {
        userService.cancelFlight(flightActionPojo.getUserId(), flightActionPojo.getFlightId());
    }

    @GetMapping("admin/denyflightrequest")
    public void denyFlight(@RequestBody FlightActionPojo flightActionPojo)
            throws OperationNotPosible, NotFoundEntityException, NoResultsAvalilable {
        userService.denyFlightRequest(flightActionPojo.getUserId(), flightActionPojo.getFlightId(),
                flightActionPojo.getReason());
    }

    @GetMapping("admin/approveflightrequest")
    public void approveFlightRequest(@RequestBody FlightActionPojo flightActionPojo)
            throws OperationNotPosible, NotFoundEntityException, NoResultsAvalilable {
        userService.approveFlightRequest(flightActionPojo.getUserId(), flightActionPojo.getFlightId(),
                flightActionPojo.getReason());
    }

}
