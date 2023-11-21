package hotel.guimero.api.domain.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import hotel.guimero.api.domain.guest.Guest;
import hotel.guimero.api.domain.room.Room;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reservations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private double price;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private PaymentMode paymentMode;
    private boolean active;
    @OneToMany(mappedBy = "reservation")
    private List<Guest> guestList;
    @ManyToMany
    private List<Room> roomList;
    private int amountRooms;

    public Reservation(ReservationRegisterData registerData){
        this.checkInDate = registerData.checkInDate();
        this.checkOutDate = registerData.checkOutDate();
        this.paymentMode = registerData.paymentMode();
        this.active = true;
    }

    public Reservation(ReservationUpdateData updateData){
        this.id = updateData.id();
        this.checkInDate = updateData.checkInDate();
        this.checkOutDate = updateData.checkOutDate();
        this.paymentMode = updateData.paymentMode();
        this.active = true;
    }

}
