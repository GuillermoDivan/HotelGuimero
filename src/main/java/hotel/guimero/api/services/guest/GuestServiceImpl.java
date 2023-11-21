package hotel.guimero.api.services.guest;
import hotel.guimero.api.domain.guest.*;
import hotel.guimero.api.repositories.GuestRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {
    private final GuestRepository guestRepository;

    @Override
    public GuestShowData save(GuestRegisterData guestRegisterData) {
        Guest guest = new Guest(guestRegisterData);
        this.guestRepository.save(guest);
        return new GuestShowData(guest);
    }

    @Override
    public Page<GuestShowData> findAll(boolean active, Pageable paging) {
        return this.guestRepository.findAllByActive(active, paging).map(GuestShowData::new);
    }

    @Override
    public GuestShowData findById(Long id) throws EntityNotFoundException{
        Guest guest = this.guestRepository.findByIdAndActive(id, true)
                .orElseThrow(EntityNotFoundException::new);
        return new GuestShowData(guest);
    }

    @Override
    public GuestShowData findByName(String name, String surname) throws EntityNotFoundException{
        Guest guest = this.guestRepository.findByNameAndSurnameAndActive(name, surname,true)
                .orElseThrow(EntityNotFoundException::new);
        return new GuestShowData(guest);
    }

    @Override
    public GuestShowData update(GuestUpdateData guestUpdateData) throws EntityNotFoundException{
        Guest guest = this.guestRepository.findById(guestUpdateData.id())
                .orElseThrow(EntityNotFoundException::new);

        if (guest.isActive()){
            if (guestUpdateData.name() != null) {
                guest.setName(guestUpdateData.name());
            }
        if (guestUpdateData.surname() != null) {
            guest.setSurname(guestUpdateData.surname());
        }
        if (guestUpdateData.phone() != null) {
            guest.setPhone(guestUpdateData.phone());
            }
        if (guestUpdateData.nationality() != null) {
            guest.setNationality(guestUpdateData.nationality());
        }
        if (guestUpdateData.birthdate() != null) {
            guest.setBirthdate(guestUpdateData.birthdate());
        }
        this.guestRepository.save(guest);}
        return new GuestShowData(guest);
    }

    @Override
    public boolean toggleGuest(Long id)throws EntityNotFoundException{
        Guest guestToToggle = this.guestRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        guestToToggle.setActive(!guestToToggle.isActive());
        this.guestRepository.save(guestToToggle);
        return guestToToggle.isActive();
    }
}
