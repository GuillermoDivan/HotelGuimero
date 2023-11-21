package hotel.guimero.api.services.user;
import hotel.guimero.api.domain.user.*;
import hotel.guimero.api.repositories.UserRepository;
import hotel.guimero.api.services.authentication.AuthenticationService;
import hotel.guimero.api.services.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    @Override
    public UserShowData registerUser(UserAuthenticationData userData) {
        var user = new User(userData);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new UserShowData(user);
    }

    @Override
    public UserShowData findByUsername(String username) throws EntityNotFoundException{
        var user = userRepository.findByUsername(username);
                //.orElseThrow(EntityNotFoundException::new);
        return new UserShowData(user);
    }

    @Override
    public Page<UserShowData> findAll(boolean active, Pageable paging) {
        return this.userRepository.findAllByActive(active, paging).map(UserShowData::new);
    }

    @Override
    public UserShowData findById(Long id) throws EntityNotFoundException {
        User user = this.userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return new UserShowData(user);
    }

    @Override
    public UserShowData update(UserUpdateData userUpdateData) throws EntityNotFoundException {
        User user = this.userRepository.findById(userUpdateData.id())
                .orElseThrow(EntityNotFoundException::new);

        if (user.isActive()){
            if (userUpdateData.email() != null) {
                user.setUsername(userUpdateData.email());
            }
            if (userUpdateData.password() != null) {
                user.setPassword(passwordEncoder.encode(userUpdateData.password()));
            }
            this.userRepository.save(user);
            authenticationService.revokeAllUserTokens(user);
        }
        return new UserShowData(user);
    }

    @Override
    public boolean toggleUser(Long id) throws EntityNotFoundException {
        User userToToggle = this.userRepository.findById(id).orElse(null);
        userToToggle.setActive(!userToToggle.isActive());
            this.userRepository.save(userToToggle);
            return userToToggle.isActive();
        }
    }

