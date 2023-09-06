package hotel.guimero.api.domain.user;

public record UserAuthenticationData(String username, String password) {
    public UserAuthenticationData(User user){
        this(user.getUsername(), user.getPassword());
    }
}