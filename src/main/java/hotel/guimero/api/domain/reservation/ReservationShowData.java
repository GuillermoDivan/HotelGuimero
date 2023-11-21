package hotel.guimero.api.domain.reservation;

import hotel.guimero.api.domain.guest.Guest;

import java.time.LocalDateTime;

public record ReservationShowData(Long id, LocalDateTime checkInDate,
                                  LocalDateTime checkOutDate, double price, PaymentMode paymentMode) {

    public ReservationShowData(Reservation reservation) {
        this(reservation.getId(), reservation.getCheckInDate(), reservation.getCheckOutDate(),
                reservation.getPrice(), reservation.getPaymentMode());
    }
}
