package hotel.guimero.api.infra.mappers;
import hotel.guimero.api.domain.reservation.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ReservationMapper {
    private final ModelMapper modelMapper;

    public ReservationShowData convertReservationToShow(Reservation reservation){
        ReservationShowData reservationShowData = modelMapper.map(reservation, ReservationShowData.class);
        return reservationShowData;
    }

    public Reservation convertRegisterDataToReservation(ReservationRegisterData reservationRegisterData){
        Reservation reservation = modelMapper.map(reservationRegisterData, Reservation.class);
        return reservation;
    }

    }