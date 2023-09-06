package hotel.guimero.api.services;

import hotel.guimero.api.domain.guest.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GuestServiceImpl implements GuestService{
    private final GuestRepository guestRepository;
    public GuestServiceImpl(GuestRepository guestRepository) {
        this.guestRepository = guestRepository; }

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
    public GuestShowData findById(Long id) {
        Guest guest = this.guestRepository.findByIdAndActive(id, true).
                orElseThrow(() -> new EntityNotFoundException());
        return new GuestShowData(guest);
    }

    @Override
    public GuestShowData findByName(String name, String surname) {
        Guest guest = this.guestRepository.findByNameAndSurnameAndActive(name, surname,true).
                orElseThrow(() -> new EntityNotFoundException());
        return new GuestShowData(guest);
    }

    @Override
    public GuestShowData update(GuestUpdateData guestUpdateData) {
        Guest guest = new Guest(guestUpdateData);
        Guest guestToUpdate = this.guestRepository.findById(guest.getId()).orElse(null);

        if (guestToUpdate.isActive()){
            if (guest.getName() != null) {
                guestToUpdate.setName(guest.getName());
            }
        if (guest.getSurname() != null) {
            guestToUpdate.setSurname(guest.getSurname());
        }
        if (guest.getPhone() != null) {
            guestToUpdate.setPhone(guest.getPhone());
            }
        if (guest.getNationality() != null) {
            guestToUpdate.setNationality(guest.getNationality());
        }
        if (guest.getBirthdate() != null) {
            guestToUpdate.setBirthdate(guest.getBirthdate());
        }
        this.guestRepository.save(guestToUpdate);}
        return new GuestShowData(guestToUpdate);
    }

    @Override
    public boolean turnOffGuest(Long id) {
        Guest guestToTurnOff = this.guestRepository.findById(id).orElse(null);
        if (guestToTurnOff.isActive()) {
            guestToTurnOff.setActive(false);
            this.guestRepository.save(guestToTurnOff);
            return true;
        }
        return false;
    }
}
