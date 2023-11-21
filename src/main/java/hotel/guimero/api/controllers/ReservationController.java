package hotel.guimero.api.controllers;

import hotel.guimero.api.domain.reservation.ReservationRegisterData;
import hotel.guimero.api.domain.reservation.ReservationShowData;
import hotel.guimero.api.domain.reservation.ReservationUpdateData;
import hotel.guimero.api.services.reservation.ReservationService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @Transactional
    public ResponseEntity<ReservationShowData> registerReservation(@RequestBody @Valid
                                                                       ReservationRegisterData reservationRegisterData, UriComponentsBuilder uriBuilder) {
        var data = this.reservationService.save(reservationRegisterData);
        URI url = uriBuilder.path("/reservations/id/{id}").buildAndExpand(data.id()).toUri();
        return ResponseEntity.created(url).body(data);
    }

    @GetMapping("/all")
    @PostAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<Page<ReservationShowData>> findReservationList(@PageableDefault(size = 3) Pageable paging) {
        return ResponseEntity.ok(reservationService.findAll(true, paging));
    }

    @GetMapping("/allInactive")
    @PostAuthorize("hasRole('ADMIN')") //Se ejecuta después del securityFilter.
    public ResponseEntity<Page<ReservationShowData>> findInactiveReservationList(@PageableDefault(size = 3) Pageable paging) {
        return ResponseEntity.ok(reservationService.findAll(false, paging));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ReservationShowData> findReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.findById(id));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ReservationShowData> updateReservation(@RequestBody @Valid
                                                                 ReservationUpdateData reservationUpdateData) {
        return ResponseEntity.ok(this.reservationService.update(reservationUpdateData));
    }

    @PutMapping("/id/{id}")
    @Transactional
    public ResponseEntity<Boolean> toggleReservation(@PathVariable Long id) {
        boolean toggledReservation = reservationService.toggleReservation(id);
        if (toggledReservation) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }
}
