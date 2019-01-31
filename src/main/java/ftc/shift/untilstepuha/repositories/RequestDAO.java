package ftc.shift.untilstepuha.repositories;

import ftc.shift.untilstepuha.models.db.Request;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestDAO extends CrudRepository<Request, String> {
    List<Request> findAllByAuthorID(String authorID);
}
