package hotel.guimero.api.repositories;
import hotel.guimero.api.domain.reservation.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Page<Reservation> findAllByActive(Boolean active, Pageable paging);
    @Query("SELECT r FROM Reservation r WHERE :date >= r.checkInDate AND :date <= r.checkOutDate AND r.active =true")
    Page<Reservation> findAllOnDate(LocalDateTime date, Pageable paging);
    Optional<Reservation> findByIdAndActive(Long id, Boolean active);

}
