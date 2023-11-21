package hotel.guimero.api.domain.room;

import hotel.guimero.api.domain.reservation.Reservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Enum type;
    private boolean available;
    private boolean active;
    @ManyToMany
    private List<Reservation> reservationList;

}
