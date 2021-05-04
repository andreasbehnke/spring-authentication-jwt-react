package org.buildingblock.springauthjwt.boundary;

import org.buildingblock.springauthjwt.model.UserAuthenticationRequestImpl;
import org.buildingblock.springauthjwt.model.UserView;
import org.springext.security.jwt.UserAuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/public/authentication")
public class UserAuthenticationResource {

    private final UserAuthenticationService userAuthenticationService;

    public UserAuthenticationResource(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("login")
    public ResponseEntity<UserView> login(@RequestBody @Valid UserAuthenticationRequestImpl request) {
        return userAuthenticationService.authenticateUser(request, UserView::new);
    }
}
