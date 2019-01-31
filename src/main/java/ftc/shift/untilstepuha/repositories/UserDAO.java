package ftc.shift.untilstepuha.repositories;

import ftc.shift.untilstepuha.models.db.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends CrudRepository<User, String> {
    Optional<User> findFirstByToken(String token);

    Optional<User> findFirstByName(String name);
}
