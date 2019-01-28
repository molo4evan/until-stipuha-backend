package ftc.shift.untilstepuha;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
public class SpringBootApp {
    public static void main(String[] args) {
        try {
            initFirebase();
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        SpringApplication.run(SpringBootApp.class, args);
    }

    private static void initFirebase() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("firebase-adminsdk.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://untilstepuha.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);
    }
}
