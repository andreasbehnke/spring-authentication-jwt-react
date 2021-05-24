package org.buildingblock.springauthjwt.boundary;

import org.buildingblock.springauthjwt.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorldResource {

    private final UserService userService;

    public HelloWorldResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String helloWorld() {
        return "Hello " + userService.getAuthorizedUser().getUsername() + "!";
    }
}
