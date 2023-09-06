package hotel.guimero.api.domain.reservation;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ReservationUpdateData(@NotNull Long id, LocalDateTime checkInDate,
                                    LocalDateTime checkOutDate, String paymentMode, String price){
}
