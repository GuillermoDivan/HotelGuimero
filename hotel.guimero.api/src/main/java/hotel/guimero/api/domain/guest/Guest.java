package hotel.guimero.api.domain.guest;
import hotel.guimero.api.domain.reservation.Reservation;
import hotel.guimero.api.domain.reservation.ReservationRegisterData;
import hotel.guimero.api.domain.reservation.ReservationUpdateData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "guests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private LocalDate birthdate;
    private String nationality;
    private String phone;
    private boolean active;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    private Reservation reservation;

    public Guest(GuestRegisterData guestData){
        this.name = guestData.name();
        this.surname = guestData.surname();
        this.birthdate = guestData.birthdate();
        this.nationality = guestData.nationality();
        this.phone = guestData.phone();
        this.reservation = new Reservation();
        this.reservation.setId(guestData.reservationId());
        this.active = true;
    }

    public Guest(GuestUpdateData updateData){
        this.id = updateData.id();
        this.name = updateData.name();
        this.surname = updateData.surname();
        this.birthdate = updateData.birthdate();
        this.nationality = updateData.nationality();
        this.phone = updateData.phone();
        this.active = true;
    }
}

