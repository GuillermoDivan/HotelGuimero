package hotel.guimero.api.services.reservation;
import hotel.guimero.api.domain.reservation.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

public interface ReservationService {

        ReservationShowData save(ReservationRegisterData reservationRegisterData);
        Page<ReservationShowData> findAll(boolean active, Pageable paging);
        ReservationShowData findById(Long id) throws EntityNotFoundException;
        Page<ReservationShowData> findAllOnDate(LocalDateTime date, Pageable paging);
        ReservationShowData update(ReservationUpdateData reservationUpdateData) throws EntityNotFoundException;
        boolean toggleReservation(Long id) throws EntityNotFoundException;
        boolean delete(Long id);

    }
