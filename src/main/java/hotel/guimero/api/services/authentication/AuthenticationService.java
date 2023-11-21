package hotel.guimero.api.services.authentication;

import hotel.guimero.api.domain.user.User;
import hotel.guimero.api.domain.user.UserAuthenticationData;
import hotel.guimero.api.infra.security.JWTTokenData;

public interface AuthenticationService {

    JWTTokenData authenticate(UserAuthenticationData userAuthenticationData);
    void savedUserToken(User savedUser, String jwtToken);
    void revokeAllUserTokens(User user);
}
