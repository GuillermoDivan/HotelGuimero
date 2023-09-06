package hotel.guimero.api.domain.guest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    Page<Guest> findAllByActive(Boolean active, Pageable paging);
    Optional<Guest> findByIdAndActive(Long id, Boolean active);
    Optional<Guest> findByNameAndSurnameAndActive(String name, String surname, Boolean active);

}
