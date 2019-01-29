package ftc.shift.untilstepuha.repositories;

import ftc.shift.untilstepuha.models.db.Request;

import java.util.List;

public interface IRepository {
    Request provideRequset(String id);

    List<Request> provideRequests();

    List<Request> provideRequestsByAuthorID(String authorID);

    void addREquest(Request request);

    void deleteRequest(Request request);

    void updateRequest(Request request);
}
