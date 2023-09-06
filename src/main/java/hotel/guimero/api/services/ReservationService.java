package hotel.guimero.api.services;
import hotel.guimero.api.domain.reservation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

public interface ReservationService {

        ReservationShowData save(ReservationRegisterData reservationRegisterData);
        Page<ReservationShowData> findAll(boolean active, Pageable paging);
        ReservationShowData findById(Long id);
        Page<ReservationShowData> findAllOnDate(LocalDateTime date, Pageable paging);
        ReservationShowData update(ReservationUpdateData reservationUpdateData);
        boolean turnOffReservation(Long id);
        boolean delete(Long id);

    }
