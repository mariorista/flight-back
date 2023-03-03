package mar.cod.flightback.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mar.cod.flightback.api.pojo.LogInPojo;
import mar.cod.flightback.entities.User;
import mar.cod.flightback.services.UserService;
import mar.cod.flightback.utils.exception.NotFoundEntityException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("r1")
public class PrimRest {

    private static final Logger log = LoggerFactory.getLogger(PrimRest.class);

    @Autowired
    private UserService userService;

    @GetMapping("test")
    public String test() {
        return "1.0.0";
    }

    @PostMapping("login")
    public ResponseEntity<User> login(@RequestBody LogInPojo pojo) throws NotFoundEntityException {
        log.info(pojo.toString());
        User ret = userService.getUserByLogin(pojo);
        return ResponseEntity.ok(ret);
    }

}
