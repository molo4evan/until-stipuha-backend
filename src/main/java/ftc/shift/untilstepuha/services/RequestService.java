package ftc.shift.untilstepuha.services;

import ftc.shift.untilstepuha.models.db.Request;
import ftc.shift.untilstepuha.repositories.IRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RequestService implements IRequestService {

    private IRequestRepository requestRepository;

    @Autowired
    public RequestService(IRequestRepository requestRepository){
        this.requestRepository = requestRepository;
    }


    @Override
    public Request provideRequest(String id) {
        return requestRepository.provideRequest(id);
    }

    @Override
    public List<Request> provideRequests() {
        return requestRepository.provideRequests();
    }

    @Override
    public List<Request> provideRequestsByUserID(String userID) {
        return requestRepository.provideRequestsByAuthorID(userID);
    }

    @Override
    public void createRequest(Request request) {
        requestRepository.addRequest(request);
    }

    @Override
    public Double donate(String requestID, double payment) {
        Request request = requestRepository.provideRequest(requestID);
        if(request == null){
            return null;
        }
        double delta = request.getAim() - request.getBalance();
        if(request.getAim() == request.getBalance()){
            return 0.0;
        } else {
            if(delta < payment){
                request.setBalance(request.getAim());
                return payment - delta;
            } else {
                request.setBalance(request.getBalance() + payment);
                return payment;
            }
        }
    }

    @Override
    public Double deleteRequest(String requestID) {
        Request request = requestRepository.provideRequest(requestID);
        if(request == null){
            return null;
        }
        requestRepository.deleteRequest(request);
        if (request.getBalance() > 0){
            return request.getBalance();
        }
        return 0.0;
    }
}
