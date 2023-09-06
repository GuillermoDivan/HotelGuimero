package hotel.guimero.api.domain.guest;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record GuestUpdateData(@NotNull Long id, String name, String surname,
                              String phone, String nationality, LocalDate birthdate) {}