package hotel.guimero.api.services.user;
import hotel.guimero.api.domain.user.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface UserService {
    UserShowData registerUser(UserAuthenticationData userData);
    UserShowData findByUsername(String username);
    Page<UserShowData> findAll(boolean active, Pageable paging);
    UserShowData findById(Long id);
    UserShowData update(UserUpdateData userUpdateData);
    boolean toggleUser(Long id);
}
