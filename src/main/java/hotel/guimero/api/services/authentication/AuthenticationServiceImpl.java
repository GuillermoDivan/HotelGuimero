package hotel.guimero.api.services.authentication;
import hotel.guimero.api.domain.token.Token;
import hotel.guimero.api.domain.user.UserAuthenticationData;
import hotel.guimero.api.infra.security.JWTTokenData;
import hotel.guimero.api.infra.security.TokenService;
import hotel.guimero.api.repositories.TokenRepository;
import hotel.guimero.api.domain.token.TokenType;
import hotel.guimero.api.domain.user.User;
import hotel.guimero.api.repositories.UserRepository;
import hotel.guimero.api.services.authentication.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    public JWTTokenData authenticate(UserAuthenticationData userAuthenticationData) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(userAuthenticationData.getUsername(),
                userAuthenticationData.getPassword());
        var authenticatedUser = authenticationManager.authenticate(authToken);
        var JWTToken = tokenService.generateToken((User) authenticatedUser.getPrincipal());
        var user = userRepository.findByUsername(userAuthenticationData.getUsername()).get();
        revokeAllUserTokens(user);
        savedUserToken(user, JWTToken);
        return new JWTTokenData(JWTToken);
    }

    public void savedUserToken(User savedUser, String jwtToken){
        var token = Token.builder().user(savedUser).token(jwtToken)
                .tokenType(TokenType.BEARER).expired(false).revoked(false).build();
        tokenRepository.save(token);
    }

    public void revokeAllUserTokens(User user){
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach(t -> {t.setExpired(true); t.setRevoked(true);});
        tokenRepository.saveAll(validUserTokens);
    }
}
