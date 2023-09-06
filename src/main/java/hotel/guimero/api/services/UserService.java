package hotel.guimero.api.services;

import hotel.guimero.api.domain.user.UserAuthenticationData;
import hotel.guimero.api.domain.user.UserShowData;
import org.springframework.stereotype.Service;


public interface UserService {
    UserShowData registerUser(UserAuthenticationData userData);

    UserAuthenticationData findByUsername(String username);
}
