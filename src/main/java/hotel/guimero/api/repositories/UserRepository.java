package hotel.guimero.api.repositories;
import hotel.guimero.api.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAllByActive(Boolean active, Pageable paging);
    Optional<User> findByUsername(String username);

}