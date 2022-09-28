package eu.luminis.workshop.smallsteps;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: First run the the application using the Quarkus dev profile, push "r" to re-start the tests
 * TODO: A test should fail, fix the code to make the test pass
 */
@RestController
@RequestMapping("/greeting")
public class GreetingController {

    @GetMapping
    public String hello(@RequestParam String name) {
        return "Hello!";
//        return String.format("Hello %s!", name);
    }
}
