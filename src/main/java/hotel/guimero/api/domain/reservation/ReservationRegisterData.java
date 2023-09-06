package hotel.guimero.api.domain.reservation;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReservationRegisterData(
        @NotNull @Future LocalDateTime checkInDate,
        @NotNull @Future LocalDateTime checkOutDate,
        @NotBlank String paymentMode) {
}

