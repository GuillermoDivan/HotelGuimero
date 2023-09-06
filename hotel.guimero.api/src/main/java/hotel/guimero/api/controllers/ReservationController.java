package hotel.guimero.api.controllers;

import hotel.guimero.api.domain.reservation.ReservationRegisterData;
import hotel.guimero.api.domain.reservation.ReservationShowData;
import hotel.guimero.api.domain.reservation.ReservationUpdateData;
import hotel.guimero.api.services.ReservationService;
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
@RequestMapping("/reservations")
public class ReservationController {

    private final hotel.guimero.api.services.ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ReservationShowData> registerReservation(@RequestBody @Valid
         ReservationRegisterData reservationRegisterData, UriComponentsBuilder uriBuilder) {
        var data = this.reservationService.save(reservationRegisterData);
        URI url = uriBuilder.path("/reservations/id/{id}").buildAndExpand(data.id()).toUri();
        return ResponseEntity.created(url).body(data);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ReservationShowData>> findReservationList(@PageableDefault(size=3) Pageable paging){
        return ResponseEntity.ok(reservationService.findAll(true, paging));
    }

    @GetMapping("/allInactive")
    public ResponseEntity<Page<ReservationShowData>> findInactiveReservationList(@PageableDefault(size=3) Pageable paging){
        return ResponseEntity.ok(reservationService.findAll(false, paging));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ReservationShowData> findReservationById(@PathVariable Long id){
        return ResponseEntity.ok(reservationService.findById(id));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ReservationShowData> updateReservation(@RequestBody @Valid
                                                                     ReservationUpdateData reservationUpdateData){
        return ResponseEntity.ok(this.reservationService.update(reservationUpdateData));
    }

    @DeleteMapping("/id/{id}")
    @Transactional
    public ResponseEntity<Boolean> turnOffReservation(@PathVariable Long id){
        boolean turnedOff = reservationService.turnOffReservation(id);
        if (turnedOff) { return ResponseEntity.noContent().build(); }
        else { return ResponseEntity.badRequest().build(); }
    }
}
