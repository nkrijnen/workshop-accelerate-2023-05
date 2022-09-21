package eu.luminis.workshop.smallsteps.api;

import eu.luminis.workshop.smallsteps.logic.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/user", consumes = "application/json")
    public ResponseEntity<String> createUser(@RequestBody NewUserRequest request) {
        userService.registerNewUser(request.toCommand());
        return new ResponseEntity<>("User registered correctly", HttpStatus.CREATED);
    }
}
