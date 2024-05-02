package hotel.guimero.api.services.reservation;
import hotel.guimero.api.domain.reservation.*;
import hotel.guimero.api.infra.mappers.ReservationMapper;
import hotel.guimero.api.repositories.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    @Override
    public ReservationShowData save(ReservationRegisterData reservationRegisterData) {
        var reservation = reservationMapper.convertRegisterDataToReservation(reservationRegisterData);
        int duration = calculateDuration(reservation.getCheckInDate(), reservation.getCheckOutDate());
        reservation.setPrice(duration * 70);
        this.reservationRepository.save(reservation);
        return reservationMapper.convertReservationToShow(reservation);
    }

    @Override
    public Page<ReservationShowData> findAll(boolean active, Pageable paging) {
        return this.reservationRepository.findAllByActive(active, paging).map(reservationMapper::convertReservationToShow);
    }

    @Override
    public ReservationShowData findById(Long id)  throws EntityNotFoundException{
       Reservation reservation = this.reservationRepository.findByIdAndActive(id, true)
                .orElseThrow(EntityNotFoundException::new);
        return reservationMapper.convertReservationToShow(reservation);
    }

    @Override
    public Page<ReservationShowData> findAllOnDate(LocalDateTime date, Pageable pageable) {
        return this.reservationRepository.findAllOnDate(date, pageable).map(reservationMapper::convertReservationToShow);
    }

    @Override
    public ReservationShowData update(ReservationUpdateData reservationUpdateData) throws EntityNotFoundException {
        Reservation reservation = this.reservationRepository.findById(reservationUpdateData.id())
                .orElseThrow(EntityNotFoundException::new);

        if (reservation.isActive()){
            if (reservationUpdateData.paymentMode() != null) {
                reservation.setPaymentMode(reservationUpdateData.paymentMode());
            }
            if (reservationUpdateData.checkInDate() != null) {
                reservation.setCheckInDate(reservationUpdateData.checkInDate());
                }
            if (reservationUpdateData.checkOutDate() != null) {
                reservation.setCheckOutDate(reservationUpdateData.checkOutDate());
            }
            int duration = calculateDuration(reservation.getCheckInDate(),
                    reservation.getCheckOutDate());
            reservation.setPrice(duration * 70);
            this.reservationRepository.save(reservation);}
        return reservationMapper.convertReservationToShow(reservation);
    }

    @Override
    public boolean toggleReservation(Long id) throws EntityNotFoundException {
        Reservation reservationToToggle = this.reservationRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        reservationToToggle.setActive(!reservationToToggle.isActive());
        this.reservationRepository.save(reservationToToggle);
        return reservationToToggle.isActive();
        }

    @Override
    public boolean delete(Long id) {
        this.reservationRepository.deleteById(id);
        return true;
    }

    private int calculateDuration(LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        return (int) checkInDate.until(checkOutDate, ChronoUnit.DAYS);
    }
}
