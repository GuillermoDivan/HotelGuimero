package hotel.guimero.api.services;
import hotel.guimero.api.domain.reservation.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@Service
public class ReservationServiceImpl implements ReservationService{
    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public ReservationShowData save(ReservationRegisterData reservationRegisterData) {
        Reservation reservation = new Reservation(reservationRegisterData);
        int duration = calculateDuration(reservation.getCheckInDate(), reservation.getCheckOutDate());
        reservation.setPrice(duration * 70);
        this.reservationRepository.save(reservation);
        return new ReservationShowData(reservation);
    }

    @Override
    public Page<ReservationShowData> findAll(boolean active, Pageable paging) {
        return this.reservationRepository.findAllByActive(active, paging).map(ReservationShowData::new);
    }

    @Override
    public ReservationShowData findById(Long id) {
       Reservation reservation = this.reservationRepository.findByIdAndActive(id, true).
                orElseThrow(() -> new EntityNotFoundException());
        return new ReservationShowData(reservation);
    }

    @Override
    public Page<ReservationShowData> findAllOnDate(LocalDateTime date, Pageable pageable) {
        return this.reservationRepository.findAllOnDate(date, pageable).map(ReservationShowData::new);
    }

    @Override
    public ReservationShowData update(ReservationUpdateData reservationUpdateData) {
        Reservation newReservation = new Reservation(reservationUpdateData);
        Reservation oldReservation = this.reservationRepository.findById(newReservation.getId()).orElse(null);

        if (oldReservation.isActive()){
            if (newReservation.getPaymentMode() != null) {
                oldReservation.setPaymentMode(newReservation.getPaymentMode());
            }
            if (newReservation.getCheckInDate() != null) {
                oldReservation.setCheckInDate(newReservation.getCheckInDate());
                }
            if (newReservation.getCheckOutDate() != null) {
                oldReservation.setCheckOutDate(newReservation.getCheckOutDate());
            }
            int duration = calculateDuration(oldReservation.getCheckInDate(),
                    oldReservation.getCheckOutDate());
            oldReservation.setPrice(duration * 70);
            this.reservationRepository.save(oldReservation);}
        return new ReservationShowData(oldReservation);
    }

    @Override
    public boolean turnOffReservation(Long id) {
        Reservation reservationToTurnOff = this.reservationRepository
                .findById(id).orElse(null);
        if (reservationToTurnOff.isActive()) {
            reservationToTurnOff.setActive(false);
            this.reservationRepository.save(reservationToTurnOff);
            return true;
        }
        return false;
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
