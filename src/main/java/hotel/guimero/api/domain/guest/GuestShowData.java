package hotel.guimero.api.domain.guest;
import java.time.LocalDate;

public record GuestShowData (Long id, String name, String surname,
                             String nationality, String phone, LocalDate birthdate,
                             Long reservationId) {

    public GuestShowData(Guest guest) {
        this(guest.getId(), guest.getName(), guest.getSurname(),
                guest.getNationality(), guest.getPhone(), guest.getBirthdate(),
                guest.getReservation().getId());
    }
}
