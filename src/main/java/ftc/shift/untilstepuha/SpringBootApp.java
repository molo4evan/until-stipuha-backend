package ftc.shift.untilstepuha;
import ftc.shift.untilstepuha.models.db.Request;
import ftc.shift.untilstepuha.repositories.InMemoryRequestRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class SpringBootApp {
    public static void main(String[] args) {
        //SpringApplication.run(SpringBootApp.class, args);
        InMemoryRequestRepository rep = new InMemoryRequestRepository();
        rep.addRequest(new Request(UUID.randomUUID().toString(), "request", "it's request", "0", 3000, 228.32));
    }
}
