package hotel.guimero.api.services;
import hotel.guimero.api.domain.guest.GuestRegisterData;
import hotel.guimero.api.domain.guest.GuestShowData;
import hotel.guimero.api.domain.guest.GuestUpdateData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GuestService {

        GuestShowData save(GuestRegisterData guestRegisterData);
        Page<GuestShowData> findAll(boolean active, Pageable paging);
        GuestShowData findById(Long id);
        GuestShowData findByName(String name, String surname);
        GuestShowData update(GuestUpdateData guestUpdateData);
        boolean turnOffGuest(Long id);

}
