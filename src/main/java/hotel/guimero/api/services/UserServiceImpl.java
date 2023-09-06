package hotel.guimero.api.services;

import hotel.guimero.api.domain.user.User;
import hotel.guimero.api.domain.user.UserAuthenticationData;
import hotel.guimero.api.domain.user.UserRepository;
import hotel.guimero.api.domain.user.UserShowData;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder){
        this.userRepository = userRepository; //Reemplazo de @Autowired,
        this.passwordEncoder = passwordEncoder; //Reemplazo de @Autowired,
    }

    @Override
    public UserShowData registerUser(UserAuthenticationData userData) {
        // convertir DTO a Entidad
        var user = new User(userData);
        // preparar la entidad para guardarse
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //mandar a guardar la entidad
        userRepository.save(user);
        // ver de devolver algo
        return new UserShowData(user);
    }

    @Override
    public UserAuthenticationData findByUsername(String username) {
        var user = userRepository.findByUsername(username);
        return new UserAuthenticationData((User)user);
    }
}
