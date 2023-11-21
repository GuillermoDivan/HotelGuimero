package hotel.guimero.api.services.guest;
import hotel.guimero.api.domain.guest.GuestRegisterData;
import hotel.guimero.api.domain.guest.GuestShowData;
import hotel.guimero.api.domain.guest.GuestUpdateData;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GuestService {

        GuestShowData save(GuestRegisterData guestRegisterData);
        Page<GuestShowData> findAll(boolean active, Pageable paging);
        GuestShowData findById(Long id) throws EntityNotFoundException;
        GuestShowData findByName(String name, String surname) throws EntityNotFoundException;
        GuestShowData update(GuestUpdateData guestUpdateData) throws EntityNotFoundException;
        boolean toggleGuest(Long id) throws EntityNotFoundException;

}
