package hotel.guimero.api.controllers;
import hotel.guimero.api.services.authentication.AuthenticationService;
import jakarta.validation.Valid;
import hotel.guimero.api.domain.user.UserAuthenticationData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping //Toma del endpoint el DTO, genera por SpringBoot un token con user y pass y lo autentica.
    public ResponseEntity authenticateUser(@RequestBody @Valid
                   UserAuthenticationData userAuthenticationData) {
        try {
            var JWTToken = authenticationService.authenticate(userAuthenticationData);
            return ResponseEntity.ok(JWTToken); //Devuelve el JWT luego de hashear el pass.
        } catch (Exception ex) {
            return null;
        }
    }


}