package ftc.shift.untilstepuha.repositories;

import ftc.shift.untilstepuha.models.db.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Qualifier("H2Repo")
public class H2UserRepository implements IUserRepository {
    private final UserDAO DAO;

    @Autowired
    public H2UserRepository(UserDAO DAO){
        this.DAO = DAO;
    }

    @Override
    public User fetchUserByID(String id) {
        return DAO.findById(id).orElse(null);
    }

    @Override
    public User fetchUserByToken(String token) {
        return DAO.findFirstByToken(token).orElse(null);
    }

    @Override
    public User fetchUserByName(String name) {
        return DAO.findFirstByName(name).orElse(null);
    }

    @Override
    public void addUser(User user) {
        DAO.save(user);
    }

    @Override
    public List<User> fetchUsers() {
        ArrayList<User> out = new ArrayList<>();
        DAO.findAll().forEach(out::add);
        return out;
    }

    @Override
    public void updateUser(User user) {
        DAO.save(user);
    }
}
