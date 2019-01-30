package ftc.shift.untilstepuha.repositories;

import ftc.shift.untilstepuha.models.db.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Qualifier("H2Repo")
public class H2RequestRepository implements IRequestRepository {
    private final RequestDAO DAO;

    @Autowired
    public H2RequestRepository(RequestDAO DAO){
        this.DAO = DAO;
    }

    @Override
    public Request provideRequest(String id) {
        return DAO.findById(id).orElse(null);
    }

    @Override
    public List<Request> provideRequests() {
        ArrayList<Request> out = new ArrayList<>();
        DAO.findAll().forEach(out::add);
        return out;
    }

    @Override
    public List<Request> provideRequestsByAuthorID(String authorID) {
        return DAO.findAllByAuthorID(authorID);
    }

    @Override
    public void addRequest(Request request) {
        DAO.save(request);
    }

    @Override
    public void deleteRequest(Request request) {
        DAO.delete(request);
    }

    @Override
    public void updateRequest(Request request) {
        DAO.save(request);
    }
}
