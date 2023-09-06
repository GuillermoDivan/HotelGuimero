package hotel.guimero.api.controllers;

import hotel.guimero.api.domain.guest.*;
import hotel.guimero.api.services.GuestService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/guests")
public class GuestController {
    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<GuestShowData> registerGuest(@RequestBody @Valid
                                                           GuestRegisterData GuestRegisterData, UriComponentsBuilder uriBuilder) {
        var data = this.guestService.save(GuestRegisterData);
        URI url = uriBuilder.path("/guests/id/{id}").buildAndExpand(data.id()).toUri();
        return ResponseEntity.created(url).body(data);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<GuestShowData>> findGuestList(@PageableDefault(size = 3)
                           Pageable paging) {
        return ResponseEntity.ok(guestService.findAll(true, paging));
    }

    @GetMapping("/allInactive")
    public ResponseEntity<Page<GuestShowData>> findInactiveGuestList(@PageableDefault(size = 3)
                          Pageable paging) {
        return ResponseEntity.ok(guestService.findAll(false, paging));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<GuestShowData> findGuestById(@PathVariable Long id) {
        return ResponseEntity.ok(guestService.findById(id));
    }

    @GetMapping("/name/{name}/{surname}")
    public ResponseEntity<GuestShowData> findGuestByName(@PathVariable String name,
               @PathVariable String surname) {
        return ResponseEntity.ok(guestService.findByName(name, surname));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<GuestShowData> updateGuest(@RequestBody @Valid
                 GuestUpdateData guestUpdateData) {
        return ResponseEntity.ok(this.guestService.update(guestUpdateData));
    }

    @DeleteMapping("/id/{id}")
    @Transactional
    public ResponseEntity<Boolean> turnOffGuest(@PathVariable Long id) {
        boolean turnedOff = guestService.turnOffGuest(id);
        if (turnedOff) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}