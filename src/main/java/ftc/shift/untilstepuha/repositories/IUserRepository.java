package ftc.shift.untilstepuha.repositories;

import ftc.shift.untilstepuha.models.db.User;

import java.util.List;

public interface IUserRepository {
    User fetchUserByID(String id);

    User fetchUserByToken(String token);

    User fetchUserByName(String name);

    void addUser(User user);

    List<User> fetchUsers();

    void updateUser(User user);
}
