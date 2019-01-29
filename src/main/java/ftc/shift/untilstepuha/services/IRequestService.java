package ftc.shift.untilstepuha.services;

import ftc.shift.untilstepuha.models.db.Request;

import java.util.List;

public interface IRequestService extends IService {
    Request provideRequest(String id);

    List<Request> provideRequests();

    List<Request> provideRequestsByUserID(String userID);

    void createRequest(Request request);

    Double donate(String requestID, double payment);    //null if no request

    Double deleteRequest(String requestID);
}
