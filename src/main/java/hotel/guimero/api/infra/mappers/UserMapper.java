package hotel.guimero.api.infra.mappers;
import hotel.guimero.api.domain.user.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserShowData convertUserToShow(User user){
        UserShowData userShowData = modelMapper.map(user, UserShowData.class);
        return userShowData;
    }

    public User convertRegisterDataToUser(UserAuthenticationData userAuthenticationData){
        User user = modelMapper.map(userAuthenticationData, User.class);
        return user;
    }

    }