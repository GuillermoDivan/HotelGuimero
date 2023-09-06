package hotel.guimero.api.domain.guest;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record GuestRegisterData(
        @NotBlank String name,
        @NotBlank String surname,
        @NotBlank String nationality,
        @NotNull LocalDate birthdate,
        @NotBlank @Pattern(regexp = "\\d{8,13}") String phone,
        @NotNull Long reservationId) {}