package hotel.guimero.api.domain.user;
import hotel.guimero.api.domain.token.Token;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Entity(name = "User")
@Table(name = "users")
@Data
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    //UserDetails indica que aquí es la tabla de login.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean active;
    @OneToMany (mappedBy = "user")
    private List<Token> tokens;

    public User(UserAuthenticationData userData){
        this.username = userData.username();
        this.password = userData.password();
        this.email = userData.email();
        this.role = userData.role();
        this.active = true;
    }

    public User(UserUpdateData userData){ //No olvidar hacer constructor para update.
        this.id = userData.id(); // Para Update es necesario el id...
        this.password = userData.password();
        this.email = userData.email();
    }

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_".concat(this.role.name())));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
