package ftc.shift.untilstepuha.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import ftc.shift.untilstepuha.models.db.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.*;

@Repository
@Qualifier("InMemoryRepo")
public class InMemoryUserRepository implements IUserRepository {
    private Map<String, User> users = new HashMap<>();

    public InMemoryUserRepository() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            User[] usersArray = mapper.readValue(new File("users"), User[].class);
            for (User user: usersArray) {
                users.put(user.getId(), user);
            }
        } catch (Exception ignored) {}
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        try {
            File file = new File("users");
            if (file.exists()){
                file.delete();
            }
            file.createNewFile();

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file, users.values());
        } catch (Exception ex) {
            try {
                new File("users").delete();
            } catch (Exception ignored) {}
        }
    }

    @Override
    public User fetchUserByID(String id) {
        return users.get(id);
    }

    @Override
    public User fetchUserByToken(String token) {
        for (User user: users.values()) {
            if (user.getToken().equals(token)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User fetchUserByName(String name) {
        for (User user: users.values()) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void addUser(User user) {
        if (!users.containsKey(user.getId())){
            users.put(user.getId(), user);
        }
    }

    @Override
    public List<User> fetchUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void updateUser(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        }
    }
}
