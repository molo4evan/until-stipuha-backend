package ftc.shift.untilstepuha.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import ftc.shift.untilstepuha.models.db.Request;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.*;

@Repository
public class InMemoryRequestRepository implements IRequestRepository {
    private Map<String, Request> requests = new HashMap<>();

    public InMemoryRequestRepository(){
        try {
            File file = new File("requests");
            if (!file.exists()){
                return;
            }
            ObjectMapper mapper = new ObjectMapper();
            Request[] requestsArray = mapper.readValue(file, Request[].class);
            for (Request request: requestsArray) {
                requests.put(request.getId(), request);
            }
        } catch (Exception ignored) {}
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        try {
            File file = new File("requests");
            if (file.exists()){
                file.delete();
            }
            file.createNewFile();

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file, requests.values());
        } catch (Exception ex) {
            try {
                new File("requests").delete();
            } catch (Exception ignored) {}
        }
    }

    @Override
    public Request provideRequest(String id) {
        return requests.get(id);
    }

    @Override
    public List<Request> provideRequests() {
        return new ArrayList<>(requests.values());
    }

    @Override
    public List<Request> provideRequestsByAuthorID(String authorID) {
        ArrayList<Request> out = new ArrayList<>();
        for (Request req: requests.values()){
            if (req.getAuthorID().equals(authorID)){
                out.add(req);
            }
        }
        return out;
    }

    @Override
    public void addRequest(Request request) {
        if (!requests.containsKey(request.getId())){
            requests.put(request.getId(), request);
        }
    }

    @Override
    public void deleteRequest(Request request) {
        requests.remove(request.getId());
    }

    @Override
    public void updateRequest(Request request) {
        if (requests.containsKey(request.getId())){
            requests.put(request.getId(), request);
        }
    }
}
