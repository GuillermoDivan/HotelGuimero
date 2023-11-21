package hotel.guimero.api.controllers;
import hotel.guimero.api.domain.user.*;
import hotel.guimero.api.services.user.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<UserShowData> registerUser(@RequestBody @Valid
                                                         UserAuthenticationData userData) {
        //llamar al service. SI NO SE LE PONE @RequestBody llega nulo, dah!
        var res = userService.registerUser(userData);
        //retornar confirmacion usuario
        return ResponseEntity.ok(res);
    }

    @GetMapping("/all")
    @PostAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<Page<UserShowData>> findUserList(@PageableDefault(size = 10)
                                                             Pageable paging) {
        return ResponseEntity.ok(userService.findAll(true, paging));
    }

    @GetMapping("/allInactive")
    @PostAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserShowData>> findInactiveUserList
            (@PageableDefault(size = 10) Pageable paging) {
        return ResponseEntity.ok(userService.findAll(false, paging));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserShowData> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/name/{username}")
    public ResponseEntity<UserShowData> findByUsername(@PathVariable
                                                               String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<UserShowData> update(@RequestBody @Valid
                                                     UserUpdateData userUpdateData) {
        return ResponseEntity.ok(this.userService.update(userUpdateData));
    }

    @PutMapping("/id/{id}")
    @Transactional
    public ResponseEntity<Boolean> toggleUser(@PathVariable Long id) {
        boolean toggledUser = userService.toggleUser(id);
        if (!toggledUser) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }


}
