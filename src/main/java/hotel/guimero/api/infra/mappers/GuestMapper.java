package hotel.guimero.api.infra.mappers;
import hotel.guimero.api.domain.guest.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

 @Configuration
@RequiredArgsConstructor
public class GuestMapper {

    private final ModelMapper modelMapper;

    public GuestShowData convertGuestToShow(Guest guest){
        GuestShowData guestShowData = modelMapper.map(guest, GuestShowData.class);
        return guestShowData;
    }

    public Guest convertRegisterDataToGuest(GuestRegisterData guestRegisterData){
        Guest guest = modelMapper.map(guestRegisterData, Guest.class);
        return guest;
    }

    }