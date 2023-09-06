package hotel.guimero.api.controllers;

import hotel.guimero.api.domain.user.UserAuthenticationData;
import hotel.guimero.api.domain.user.UserShowData;
import hotel.guimero.api.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<UserShowData> registerUser(@RequestBody @Valid UserAuthenticationData userData) {
        //llamar al service. SI NO SE LE PONE @RequestBody llega nulo, dah!
        var res = userService.registerUser(userData);

        //retornar confirmacion usuario
        return ResponseEntity.ok(res);
    }
}
