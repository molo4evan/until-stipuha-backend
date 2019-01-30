package ftc.shift.untilstepuha.services;

import ftc.shift.untilstepuha.exceptions.UnacceptableDeltaException;
import ftc.shift.untilstepuha.models.db.Request;
import ftc.shift.untilstepuha.repositories.IRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RequestService implements IRequestService {

    private IRequestRepository requestRepository;

    @Autowired
    public RequestService(@Qualifier("H2Repo") IRequestRepository requestRepository){
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
    public Request createRequest(Request request) {
        Request realRequest = new Request(
                UUID.randomUUID().toString(),
                request.getName(),
                request.getDescription(),
                request.getAuthorID(),
                request.getAim(),
                0
        );
        requestRepository.addRequest(realRequest);
        return realRequest;
    }

    @Override
    public Double donate(String requestID, double payment) throws UnacceptableDeltaException {
        if (payment < 0) {
            throw new UnacceptableDeltaException();
        }

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
                requestRepository.updateRequest(request);
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
