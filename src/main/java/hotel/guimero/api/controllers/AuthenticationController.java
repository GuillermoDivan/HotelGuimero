package hotel.guimero.api.controllers;

import hotel.guimero.api.infra.security.JWTTokenData;
import hotel.guimero.api.services.UserService;
import jakarta.validation.Valid;
import hotel.guimero.api.domain.user.User;
import hotel.guimero.api.domain.user.UserAuthenticationData;
import hotel.guimero.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationController(
            AuthenticationManager authenticationManager,
            TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping //Toma del endpoint el DTO, genera por SpringBoot un token con user y pass y lo autentica.
    public ResponseEntity authenticateUser(@RequestBody @Valid
                                               UserAuthenticationData userAuthenticationData) {
        Authentication authToken = new UsernamePasswordAuthenticationToken
                (userAuthenticationData.username(), userAuthenticationData.password());
        var authenticatedUser = authenticationManager.authenticate(authToken);
        var JWTToken = tokenService.generateToken((User) authenticatedUser.getPrincipal());
        return ResponseEntity.ok(new JWTTokenData(JWTToken)); //Devuelve el JWT luego de hashear el pass.
    }
}